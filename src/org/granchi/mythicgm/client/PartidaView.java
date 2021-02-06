package org.granchi.mythicgm.client;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.granchi.mythicgm.client.eventos.ComandoCreadoEvent;
import org.granchi.mythicgm.client.eventos.EscenaCreadaEvent;
import org.granchi.mythicgm.client.eventos.EscenaCreadaHandler;
import org.granchi.mythicgm.client.eventos.EscenaEditadoNombreEvent;
import org.granchi.mythicgm.client.eventos.EscenaEditadoNombreHandler;
import org.granchi.mythicgm.client.eventos.EscenaSeleccionadaEvent;
import org.granchi.mythicgm.client.eventos.EscenaSeleccionadaHandler;
import org.granchi.mythicgm.client.eventos.PartidaEditadoNombreEvent;
import org.granchi.mythicgm.client.eventos.PartidaEditadoNombreHandler;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.client.util.DialogoComponente;
import org.granchi.mythicgm.client.util.DialogoConfirmacion;
import org.granchi.mythicgm.client.util.DialogoInput;
import org.granchi.mythicgm.client.util.ParametrizedCommand;
import org.granchi.mythicgm.modelo.Escena;
import org.granchi.mythicgm.modelo.PJ;
import org.granchi.mythicgm.modelo.Partida;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Panel con información sobre la partida, acciones, etc.
 */
// TODO Si las cadenas son muy largas se va a la derecha y se jode el layout
// TODO tachar objetivos cumplidos
public class PartidaView extends VerticalPanel implements
		PartidaEditadoNombreHandler, EscenaCreadaHandler,
		EscenaEditadoNombreHandler, EscenaSeleccionadaHandler {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	private static final String COLOR_CAOS[] = { "#00bbbb", "#009999",
			"#006666", "#003333", "#000000", "#440000", "#880000", "#cc0000",
			"#ff0000" };

	private MythicGM mythicGM;
	private final HandlerManager eventBus;

	private Partida partida;
	// No tiene que ser la actual de la partida, sino la que se esté viendo
	private Escena escena;

	boolean comandosActivos;

	private Anchor ancNombre, ancEscena;
	private ListBox lstEscenas;

	private HTML htmlCaos;
	private CaptionPanel capEscena, capPjs, capPnjs, capMisiones;
	private VerticalPanel pnlEscenas;
	// TODO esto bien podría ser TablaAnadirBorrar
	private FlexTable tblPjs;
	private TablaAnadirBorrar tblPnjs, tblMisiones;
	private PanelBotonesComandos pnlComandos;

	// Comparador para los PJs
	private final Comparator<PJ> compPJ = new Comparator<PJ>() {
		@Override
		public int compare(PJ o1, PJ o2) {
			return o1.getNombre().compareTo(o2.getNombre());
		}
	};

	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 */
	public PartidaView(final MythicGM mythicGM) {
		this.mythicGM = mythicGM;
		partida = mythicGM.getPartida();

		getElement().setId("infoPartida");

		ancNombre = new Anchor();
		ancNombre.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DialogoInput dlgInput = new DialogoInput(cadenas
						.nombrePartida(), partida.getNombre(),
						new ParametrizedCommand<String>() {
							@Override
							public void execute(String nombre) {
								partida.setNombre(nombre);
								eventBus
										.fireEvent(new PartidaEditadoNombreEvent(
												nombre));
							}
						});

				dlgInput.setPopupPosition(ancNombre.getAbsoluteLeft() + 40,
						ancNombre.getAbsoluteTop() + 10);
				dlgInput.show();
			}
		});
		add(ancNombre);

		capEscena = new CaptionPanel(cadenas.escena());
		capEscena.addStyleName("cabecera");
		add(capEscena);

		pnlEscenas = new VerticalPanel();
		pnlEscenas.setWidth("100%");
		capEscena.add(pnlEscenas);

		ancEscena = new Anchor();
		ancEscena.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Escena escena = partida.getEscenas().get(
						lstEscenas.getSelectedIndex());
				DialogoInput dlgInput = new DialogoInput(
						cadenas.nombreEscena(), escena.getNombre(),
						new ParametrizedCommand<String>() {
							@Override
							public void execute(String nombre) {
								escena.setNombre(nombre);
								eventBus
										.fireEvent(new EscenaEditadoNombreEvent(
												escena));
							}
						});

				dlgInput.setPopupPosition(ancNombre.getAbsoluteLeft() + 40,
						ancNombre.getAbsoluteTop() + 10);
				dlgInput.show();
			}
		});
		pnlEscenas.add(ancEscena);

		htmlCaos = new HTML("");
		pnlEscenas.add(htmlCaos);

		lstEscenas = new ListBox();
		// Para que se separe un poco
		lstEscenas.addStyleName("cabecera");
		lstEscenas.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				eventBus.fireEvent(new EscenaSeleccionadaEvent(escena));
			}
		});
		pnlEscenas.add(lstEscenas);

		capPjs = new CaptionPanel(cadenas.pjs());
		capPjs.addStyleName("cabecera");
		add(capPjs);

		tblPjs = new FlexTable();
		capPjs.add(tblPjs);

		capPnjs = new CaptionPanel(cadenas.pnjs());
		capPnjs.addStyleName("cabecera");
		add(capPnjs);

		tblPnjs = new TablaAnadirBorrar();
		capPnjs.add(tblPnjs);

		capMisiones = new CaptionPanel(cadenas.misiones());
		capMisiones.addStyleName("cabecera");
		add(capMisiones);

		tblMisiones = new TablaAnadirBorrar();
		capMisiones.add(tblMisiones);

		pnlComandos = new PanelBotonesComandos(mythicGM);
		pnlComandos.setWidth("100%");
		pnlComandos.addStyleName("cabecera");
		add(pnlComandos);

		// Eventos
		eventBus = mythicGM.getEventBus();
		registrarEventos();
	}

	/**
	 * Registramos todos los eventos de este componente en el bus.
	 */
	private void registrarEventos() {
		eventBus.addHandler(PartidaEditadoNombreEvent.TYPE, this);
		eventBus.addHandler(EscenaCreadaEvent.TYPE, this);
		eventBus.addHandler(EscenaEditadoNombreEvent.TYPE, this);
		eventBus.addHandler(EscenaSeleccionadaEvent.TYPE, this);
	}

	/**
	 * Refresca los controles con la información de la partida.
	 * 
	 * @param infoCambiada
	 *            si la información de la partida ha cambiado
	 * @param activarComandos
	 *            si se debe permitir al usuario introducir comandos o no
	 */
	public void refrescar(boolean infoCambiada, boolean activarComandos) {
		// Esto hubiera ido todo mucho mejor si tuviéramos eventos, lo que nos
		// daría mayor granularidad y no habría que ir rellenando todo una y
		// otra vez por un puñetero detalle que cambie...

		List<Escena> escenas = partida.getEscenas();
		boolean hayEscenas = !escenas.isEmpty();
		// No la actual de la partida, sino la que se muestra
		int indEscena = getIndEscenaVisible();
		// Si hubiéramos cambiado en la select de escenas, vendría infoCambiada
		// a true
		Escena nuevaEscena = (infoCambiada ? (hayEscenas ? escenas
				.get(indEscena) : null) : escena);

		if (infoCambiada) {
			// Rellenar nombre de la partida
			ancNombre.setHTML(partida.getNombre());

			capEscena.setVisible(hayEscenas);

			if (hayEscenas) {
				lstEscenas.clear();
				int i = 0;
				for (Escena e : escenas) {
					i++;
					// Esto es para sacar '01: Persecución chunga'
					lstEscenas.addItem((i < 10 ? "0" + i : String.valueOf(i))
							+ ": " + e.getNombre());
				}
				lstEscenas.setSelectedIndex(indEscena);

				ancEscena.setText(nuevaEscena.getNombre());

				StringBuffer caos = new StringBuffer();
				caos.append("<span style=\"color:");
				caos.append(COLOR_CAOS[nuevaEscena.getCaos() - 1]);
				caos.append(";font-weight:bold\">Caos ");
				caos.append(nuevaEscena.getCaos());
				caos.append("</span>");
				if (!nuevaEscena.isAbierta()) {
					caos.append(" a ");
					caos.append("<span style=\"color:");
					caos.append(COLOR_CAOS[nuevaEscena.getCaosFinal() - 1]);
					caos.append(";font-weight:bold\">caos ");
					caos.append(escena.getCaosFinal());
					caos.append("</span>");
				}
				htmlCaos.setHTML(caos.toString());
			}
		}

		// Hemos cambiado de escena
		if (escena != nuevaEscena) {

		}

		if (activarComandos != comandosActivos) {
			comandosActivos = activarComandos;
		}

		comandosActivos = activarComandos;
		escena = nuevaEscena;

		// De PJs, PNJs y misiones se encarga el updateComandos... ya, ya sé,
		// se me ha ido un poco

		refrescarPjs();

		capPnjs.setVisible(!partida.getPnjs().isEmpty());
		if (capPnjs.isVisible()) {
			tblPnjs.rellenar(partida.getPnjs(), false, null);
		}

		capMisiones.setVisible(!partida.getObjetivos().isEmpty());
		if (capMisiones.isVisible()) {
			tblMisiones.rellenar(partida.getObjetivos(), false, null);
		}
	}

	// TODO esto va a morir y se va a convertir en una Tabla sí o sí
	/**
	 * Refresca la lista de PJs.
	 */
	private void refrescarPjs() {
		final Set<PJ> pjs = partida.getPjs();
		final PJ pjsOrdenados[] = pjs.toArray(new PJ[pjs.size()]);
		Arrays.sort(pjsOrdenados, compPJ);

		boolean partidaNoIniciada = partida.getEscenas().isEmpty();

		tblPjs.removeAllRows();
		int i = 0;
		for (final PJ pj : pjsOrdenados) {
			final Anchor ancPj = new Anchor(pj.getNombre());
			tblPjs.setWidget(i, 0, ancPj);

			// TODO estamos trabajando en ellou
			// if (partidaNoIniciada && !mythicGM.isMostrandoComando()) {
			if (partidaNoIniciada) {
				// Panel de edición si la partida está preparándose aún
				ancPj.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						PJPanel pjPanel = new PJPanel(mythicGM, pj);
						// mythicGM.addComando(pjPanel);
					}
				});

				// Pero, al mismo tiempo, también dejamos borrar
				final Anchor ancBorrar = new Anchor(cadenas.borrar());
				ancBorrar.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						DialogoConfirmacion dlgConfirm = new DialogoConfirmacion(
								cadenas.borrarAlgo() + " '" + pj.getNombre()
										+ "'...", new Command() {
									@Override
									public void execute() {
										pjs.remove(pj);
										// NO sólo refrescarPjs, porque si
										// borramos el último no
										// se puede empezar la partida
										PartidaView.this.refrescar(true,
												comandosActivos);
									}
								});

						dlgConfirm.setPopupPosition(
								ancBorrar.getAbsoluteLeft() + 40, ancBorrar
										.getAbsoluteTop() + 10);
						dlgConfirm.show();
					}
				});
				tblPjs.setWidget(i, 1, ancBorrar);
			} else {
				// Simplemente un popup si la partida ya está empezada o tenemos
				// un diálogo abierto
				ancPj.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						final DialogoComponente<PJView> dlgComponente = new DialogoComponente<PJView>(
								cadenas.pj(), new PJView(pj), null);

						dlgComponente.setPopupPosition(
								ancPj.getAbsoluteLeft() + 40, ancPj
										.getAbsoluteTop() + 10);
						dlgComponente.show();
						// Si le cambiamos el nombre a un tío, a refrescarse
						dlgComponente
								.addCloseHandler(new CloseHandler<PopupPanel>() {
									@Override
									public void onClose(
											CloseEvent<PopupPanel> event) {
										if (!dlgComponente.getComponente()
												.isMismoNombre()) {
											// No hace falta el refrescar entero
											PartidaView.this.refrescarPjs();
										}
									}
								});
					}
				});
			}

			i++;
		}

		// Añadir el enlace de añadir
		if (partidaNoIniciada) {
			final Anchor ancAnadir = new Anchor(cadenas.anadir());
			ancAnadir.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					eventBus.fireEvent(new ComandoCreadoEvent(new PJPanel(
							mythicGM, new PJ(cadenas.pjAnonimo()))));
				}
			});
			tblPjs.setWidget(tblPjs.getRowCount(), 0, ancAnadir);
			// Queda más bonito así
			tblPjs.getFlexCellFormatter()
					.setColSpan(tblPjs.getRowCount(), 0, 2);
		}
	}

	/**
	 * Habilita o deshabilita los comandos, cambiando los botones disponibles si
	 * es necesario según el estado de la partida.
	 * 
	 * @param enable
	 *            si se quiere habilitar o deshabilitar los botones
	 */
	public void updateComandos(boolean enable) {
		pnlComandos.refrescar(enable);

		// Condiciones
		boolean editarPjs = entreEscenas() && enable;
		// No antes de la primera escena
		boolean editarPnjsMisiones = (editarPjs && partida.getEscenaActual() != null);

		// TODO los pjs también

		// Las dos listas deberían de ir a la par
		if (tblPnjs.isEditando() != editarPnjsMisiones) {
			capPnjs.setVisible(editarPnjsMisiones
					&& !partida.getPnjs().isEmpty());
			if (capPnjs.isVisible()) {
				tblPnjs.rellenar(partida.getPnjs(), editarPnjsMisiones, cadenas
						.pnj());
			}

			capMisiones.setVisible(editarPnjsMisiones
					&& !partida.getObjetivos().isEmpty());
			if (capMisiones.isVisible()) {
				tblMisiones.rellenar(partida.getObjetivos(),
						editarPnjsMisiones, cadenas.mision());
			}
		}
	}

	@Override
	public void onNombrePartidaEditado(String nombre) {
		ancNombre.setHTML(nombre);
	}

	@Override
	public void onEscenaCreada(Escena escena) {
		lstEscenas.addItem(textoParaSelect(lstEscenas.getItemCount(), escena));
	}

	@Override
	public void onNombreEscenaEditado(Escena escena) {
		// El enlace sólo si es la que mostramos
		if (this.escena == escena) {
			ancEscena.setHTML(escena.getNombre());
		}

		// Pero en la select siempre
		int i = partida.getEscenas().indexOf(escena);
		lstEscenas.setItemText(i, textoParaSelect(i, escena));
	}

	@Override
	public void onEscenaSeleccionada(Escena escena) {
		if (escena != this.escena) {
			this.escena = escena;

			// TODO recargar cosas, supongo
		}
	}

	/**
	 * Devuelve el texto para una escena en la select de escenas.
	 * 
	 * @param i
	 *            índice en la select
	 * @param escena
	 *            escena
	 * @return texto a mostrar
	 */
	private String textoParaSelect(int i, Escena escena) {
		return String.format("%2d: %s", i, escena.getNombre());
	}

}
