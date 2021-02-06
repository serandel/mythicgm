package org.granchi.mythicgm.client;

import org.granchi.mythicgm.client.comandos.FinEscenaComando;
import org.granchi.mythicgm.client.comandos.InicioEscenaComando;
import org.granchi.mythicgm.client.comandos.NotasComando;
import org.granchi.mythicgm.client.comandos.PreguntaSiNoComando;
import org.granchi.mythicgm.client.eventos.AplicacionEsperandoUsuarioEvent;
import org.granchi.mythicgm.client.eventos.AplicacionEsperandoUsuarioHandler;
import org.granchi.mythicgm.client.eventos.ComandoCreadoEvent;
import org.granchi.mythicgm.client.eventos.ComandoCreadoHandler;
import org.granchi.mythicgm.client.eventos.EscenaSeleccionadaEvent;
import org.granchi.mythicgm.client.eventos.EscenaSeleccionadaHandler;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.modelo.Escena;
import org.granchi.mythicgm.modelo.Partida;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel con los botones de los comandos de la partida.
 */
public class PanelBotonesComandos extends VerticalPanel implements
		AplicacionEsperandoUsuarioHandler, ComandoCreadoHandler,
		EscenaSeleccionadaHandler {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	private Partida partida;
	// Para apuntar qué escena se está viendo
	private Escena escena;

	private boolean aplicacionEsperandoUsuario, botonesActivos;

	// TODO en verdad los eventos no se podían poner a placer
	private Button btnEmpezarEscena, btnPreguntaSiNo, /* btnEvento, */btnNotas,
			btnTerminarEscena;

	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 */
	public PanelBotonesComandos(final MythicGM mythicGM) {
		this.partida = mythicGM.getPartida();
		final HandlerManager eventBus = mythicGM.getEventBus();

		btnEmpezarEscena = new Button(cadenas.empezarEscena());
		btnEmpezarEscena.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ComandoCreadoEvent(
						new InicioEscenaComando(mythicGM)));
			}
		});
		add(btnEmpezarEscena);

		btnPreguntaSiNo = new Button(cadenas.preguntaSiNo());
		btnPreguntaSiNo.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ComandoCreadoEvent(
						new PreguntaSiNoComando(mythicGM, mythicGM.getPartida()
								.getEscenaActual())));
			}
		});
		add(btnPreguntaSiNo);

		// btnEvento = new Button("Evento aleatorio");
		// btnEvento.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// addSuceso(new EventoAleatorio(MythicGM.this, partida));
		// }
		// });
		// pnlBotones.add(btnEvento);

		btnNotas = new Button(cadenas.notas());
		btnNotas.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ComandoCreadoEvent(new NotasComando(
						mythicGM)));
			}
		});
		add(btnNotas);

		btnTerminarEscena = new Button(cadenas.terminarEscena());
		btnTerminarEscena.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ComandoCreadoEvent(new FinEscenaComando(
						mythicGM)));
			}
		});
		add(btnTerminarEscena);

		aplicacionEsperandoUsuario = botonesActivos = false;

		eventBus.addHandler(AplicacionEsperandoUsuarioEvent.TYPE, this);
		eventBus.addHandler(ComandoCreadoEvent.TYPE, this);
		eventBus.addHandler(EscenaSeleccionadaEvent.TYPE, this);
	}

	@Override
	public void onAplicacionEsperandoUsuario() {
		aplicacionEsperandoUsuario = true;
		
		// Potencialmente la aplicación puede recibir comandos... pero hay que
		// comprobar que no estemos mostrando una escena del pasado
		if (escena.isUltimaEscena() && !botonesActivos) {
			refrescar(true);
		}
	}

	@Override
	public void onComandoCreado(PanelComando comando) {
		// Si se crea un comando no se permite interacción del usuario
		aplicacionEsperandoUsuario = false;

		if (botonesActivos) {
			refrescar(false);
		}
	}

	@Override
	public void onEscenaSeleccionada(Escena escena) {
		this.escena = escena;

		// Hay que ver si nos ponemos en la última escena, y si la aplicación
		// estaba esperando usuario
		boolean activar = aplicacionEsperandoUsuario && escena.isUltimaEscena();
		if (activar != botonesActivos) {
			refrescar(activar);
		}
	}

	/**
	 * Refresca los controles.
	 * 
	 * @param enabled
	 *            si queremos habilitar los controles
	 */
	private void refrescar(boolean enabled) {
		botonesActivos = enabled;

		boolean hayEscenas = !partida.getEscenas().isEmpty();
		boolean escenaAbierta = escena != null && escena.isAbierta();

		// Primero ver los que están visibles o no
		btnEmpezarEscena.setVisible(!hayEscenas && !partida.getPjs().isEmpty());
		btnPreguntaSiNo.setVisible(escenaAbierta);
		btnNotas.setVisible(escenaAbierta);
		btnTerminarEscena.setVisible(escenaAbierta);

		// Y ahora habilitar o deshabilitar, más darle el foco al primero
		boolean primerBoton = true;
		for (Widget w : getChildren()) {
			if (w instanceof Button) {
				((Button) w).setEnabled(enabled);

				if (enabled && primerBoton) {
					((Button) w).setFocus(true);
				}
				primerBoton = false;
			}
		}
	}
}
