package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.client.PanelComando;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se crea un nuevo panel de comando.
 */
public class ComandoCreadoEvent extends GwtEvent<ComandoCreadoHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<ComandoCreadoHandler> TYPE = new GwtEvent.Type<ComandoCreadoHandler>();

	private final PanelComando comando;

	/**
	 * Constructor.
	 * 
	 * @param comando comando
	 */
	public ComandoCreadoEvent(PanelComando comando) {
		this.comando = comando;
	}

	@Override
	protected void dispatch(ComandoCreadoHandler handler) {
		handler.onComandoCreado(comando);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ComandoCreadoHandler> getAssociatedType() {
		return TYPE;
	}
}
