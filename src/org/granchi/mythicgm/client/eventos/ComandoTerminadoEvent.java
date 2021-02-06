package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.client.PanelComando;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se cambia el nombre de la partida.
 * 
 * (Probablemente me lo podría ahorrar, pero por ir probando con uno simple...)
 */
public class ComandoTerminadoEvent extends GwtEvent<ComandoTerminadoHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<ComandoTerminadoHandler> TYPE = new GwtEvent.Type<ComandoTerminadoHandler>();

	private final PanelComando comando;

	/**
	 * Constructor.
	 * 
	 * @param comando
	 *            comando
	 */
	public ComandoTerminadoEvent(PanelComando comando) {
		this.comando = comando;
	}

	@Override
	protected void dispatch(ComandoTerminadoHandler handler) {
		handler.onComandoTerminado(comando);
	}

	@Override
	public GwtEvent.Type<ComandoTerminadoHandler> getAssociatedType() {
		return TYPE;
	}
}
