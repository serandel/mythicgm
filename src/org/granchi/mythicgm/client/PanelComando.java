package org.granchi.mythicgm.client;

import org.granchi.mythicgm.client.eventos.ComandoCreadoEvent;
import org.granchi.mythicgm.client.eventos.ComandoTerminadoEvent;
import org.granchi.mythicgm.client.eventos.EscenaComponenteCreadoEvent;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.client.util.PanelBotones;
import org.granchi.mythicgm.modelo.Escena;
import org.granchi.mythicgm.modelo.FinEscena;
import org.granchi.mythicgm.modelo.PremisaEscena;
import org.granchi.mythicgm.modelo.Suceso;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Clase base de las ventanas que se añaden desde el panel de comandos para dar
 * lugar a sucesos de la partida, si no se cancelan. :)
 */
public abstract class PanelComando extends VerticalPanel {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	/**
	 * Clase con los resultados de aceptar un comando, que puede ser una
	 * premisa, varios sucesos y/o varios nuevos comandos.
	 */
	protected static class Resultado {
		private PremisaEscena premisa;
		private Suceso sucesos[];
		private PanelComando comandos[];
		private FinEscena fin;

		public Resultado(PremisaEscena premisa, Suceso[] sucesos,
				PanelComando[] comandos, FinEscena fin) {
			this.premisa = premisa;
			this.sucesos = (sucesos == null ? new Suceso[0] : sucesos);
			this.comandos = (comandos == null ? new PanelComando[0] : comandos);
			this.fin = fin;
		}
	}

	protected MythicGM mythicGM;
	protected Escena escena;

	protected PanelBotones pnlBotones;


	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 * @param escena
	 *            escena a la que se refiere el comando (puede ser la última de
	 *            la partida, una nueva que aún no se haya añadido o incluso
	 *            null si no aplica)
	 */
	public PanelComando(MythicGM mythicGM, Escena escena) {
		this(mythicGM, escena, cadenas.aceptar());
	}

	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 * @param escena
	 *            escena a la que se refiere el comando (puede ser la última de
	 *            la partida, una nueva que aún no se haya añadido o incluso
	 *            null si no aplica)
	 * @param verbo
	 *            verbo a usar en el botón de aceptar
	 */
	public PanelComando(MythicGM mythicGM, Escena escena, final String verbo) {
		addStyleName("mythicPanel");

		if (mythicGM == null) {
			throw new IllegalArgumentException("MythicGM nulo");
		}
		this.mythicGM = mythicGM;

		this.escena = escena;
		
		final HandlerManager eventBus = mythicGM.getEventBus(); 

		// Aunque lo añado luego, lo creo aquí para que el addComponentes
		// ya tenga el btnAceptar al cual poder engancharse...
		pnlBotones = new PanelBotones(verbo, new Command() {
			@Override
			public void execute() {
				if (isValido()) {
					Resultado res = onValido();

					if (res != null) {
						Escena e = PanelComando.this.mythicGM.getPartida()
								.getEscenaActual();

						if (res.premisa != null) {
							// La premisa ya va dentro de la escena, no hay que
							// añadirla
							eventBus.fireEvent(new EscenaComponenteCreadoEvent(res.premisa));
						}

						for (Suceso s : res.sucesos) {
							// Esto sí que se tiene que añadir a mano
							e.add(s);
							eventBus.fireEvent(new EscenaComponenteCreadoEvent(s));
						}

						for (PanelComando c : res.comandos) {
							eventBus.fireEvent(new ComandoCreadoEvent(c));
						}

						if (res.fin != null) {
							// Igualmente, esto se ha creado dentro de la escena
							// y no hace falta añadirlo
							eventBus.fireEvent(new EscenaComponenteCreadoEvent(res.fin));
						}
					}

					eventBus.fireEvent(new ComandoTerminadoEvent(PanelComando.this));
				} else {
					onNoValido();
				}
			}
		}, new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ComandoTerminadoEvent(PanelComando.this));
			}
		});

		addComponentes();

		// Y ahora sí que lo enchufo
		add(pnlBotones);

		// Sin el deferred no iría bien, tiene que añadirse antes
		final Focusable componentePorDefecto = getComponentePorDefecto();
		if (componentePorDefecto != null) {
			DeferredCommand.addCommand(new Command() {
				@Override
				public void execute() {
					componentePorDefecto.setFocus(true);
				}
			});
		}
	}

	/**
	 * Método para que cada suceso pueda añadir sus propias cosejas dentro.
	 */
	protected abstract void addComponentes();

	/**
	 * Indica si se puede considerar válida la información del suceso cuando se
	 * pulsa aceptar.
	 * 
	 * @return si el suceso es válido
	 */
	protected boolean isValido() {
		return true;
	}

	/**
	 * Se ejecuta cuando el comando se da por válido y se pulsa el botón de
	 * aceptar.
	 * 
	 * @return resultado que se obtiene del comando
	 */
	protected Resultado onValido() {
		return null;
	}

	/**
	 * Se ejecuta cuando se ha pulsado en el botón de aceptar pero el suceso no
	 */
	protected void onNoValido() {
		// Nada por defecto
	}

	/**
	 * Indica si el componente que debe recibir el foco cuando se muestra el
	 * suceso.
	 * 
	 * @return componente que debe recibir el foco, null para ninguno
	 */
	protected Focusable getComponentePorDefecto() {
		return pnlBotones.getBtnAceptar();
	}

	/**
	 * Indica si es necesario refrescar la información de partida cuando se
	 * acepta el suceso.
	 * 
	 * @return si es necesario refrescar la partida
	 */
	protected boolean isNecesarioRefrescarPartida() {
		return false;
	}
}
