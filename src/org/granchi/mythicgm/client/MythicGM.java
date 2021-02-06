package org.granchi.mythicgm.client;

import java.util.LinkedList;
import java.util.Queue;

import org.granchi.mythicgm.client.eventos.AplicacionEsperandoUsuarioEvent;
import org.granchi.mythicgm.client.eventos.ComandoCreadoEvent;
import org.granchi.mythicgm.client.eventos.ComandoCreadoHandler;
import org.granchi.mythicgm.client.eventos.ComandoTerminadoEvent;
import org.granchi.mythicgm.client.eventos.ComandoTerminadoHandler;
import org.granchi.mythicgm.client.eventos.EscenaComponenteCreadoEvent;
import org.granchi.mythicgm.client.eventos.EscenaComponenteCreadoHandler;
import org.granchi.mythicgm.client.eventos.PartidaEditadoNombreEvent;
import org.granchi.mythicgm.modelo.ComponenteEscena;
import org.granchi.mythicgm.modelo.Partida;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Pantalla principal de la aplicación.
 * 
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MythicGM extends FlowPanel implements EntryPoint,
		ComandoCreadoHandler, ComandoTerminadoHandler,
		EscenaComponenteCreadoHandler {
	private Partida partida = new Partida();

	// Bus de eventos
	private HandlerManager eventBus;

	// Si hay un panel en marcha
	private boolean panelActivo = false;
	// Los que se generan y se irán mostrando según se acepten los anteriores
	private Queue<PanelComando> comandosEsperando = new LinkedList<PanelComando>();

	private PartidaView partidaView;
	// TODO cambiar por un componente concreto
	private VerticalPanel vpnComandosEscena;

	/**
	 * Constructor.
	 */
	public MythicGM() {
		getElement().setId("mythicGM");

		// No sé para qué sirve la fuente, pienso que así simboliza
		// "la aplicación"
		eventBus = new HandlerManager(this);

		// Dos paneles: partida y acciones versus comandos,sucesos y demás
		vpnComandosEscena = new VerticalPanel();
		add(vpnComandosEscena);

		// Partida después de que se haya creado el contenedor de paneles, por
		// si necesitamos enganchar algún componente en la creación
		partidaView = new PartidaView(this);
		add(partidaView);
		
		eventBus.addHandler(ComandoCreadoEvent.TYPE, this);
		eventBus.addHandler(ComandoTerminadoEvent.TYPE, this);
		eventBus.addHandler(EscenaComponenteCreadoEvent.TYPE, this);
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

	public Partida getPartida() {
		return partida;
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get().add(this);

		// TODO lanzar eventos de partida como un loco, para construir el
		// estado inicial
		// TODO opcionalmente, lanzar uno que sea partida cargada y que
		// se haga todo de un tirón
		eventBus.fireEvent(new PartidaEditadoNombreEvent(partida.getNombre()));

		eventBus.fireEvent(new AplicacionEsperandoUsuarioEvent());
	}

	@Override
	public void onComandoCreado(PanelComando comando) {
		if (panelActivo) {
			comandosEsperando.add(comando);
		} else {
			mostrarComando(comando);
			panelActivo = true;
		}
	}

	@Override
	public void onComandoTerminado(PanelComando comando) {
		vpnComandosEscena.remove(comando);

		PanelComando siguiente = comandosEsperando.poll();
		if (siguiente == null) {
			panelActivo = false;
			eventBus.fireEvent(new AplicacionEsperandoUsuarioEvent());
		} else {
			mostrarComando(siguiente);
		}
	}

	/**
	 * Muestra en la ventana un panel de comando.
	 * 
	 * @param comando
	 *            comando
	 */
	private void mostrarComando(PanelComando comando) {
		vpnComandosEscena.add(comando);
		comando.getElement().scrollIntoView();
	}

	@Override
	public void onComponenteEscenaCreado(ComponenteEscena componente) {
		ComponenteEscenaView<? extends ComponenteEscena> cev = ComponenteEscenaView
				.getComponenteEscenaView(componente);
		// No tengo ni idea de dónde le estaba poniendo esto antes, pero era
		// necesario y si lo pongo en el addComponentes de los views resulta
		// que la lío si uno se está incluyendo como un subpanel
		cev.addStyleName("mythicPanel");
		vpnComandosEscena.add(cev);
	}
}