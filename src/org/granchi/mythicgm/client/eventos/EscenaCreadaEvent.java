package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.Escena;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se crea una nueva escena.
 */
public class EscenaCreadaEvent extends
		GwtEvent<EscenaCreadaHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<EscenaCreadaHandler> TYPE = new GwtEvent.Type<EscenaCreadaHandler>();

	private final Escena escena;

	/**
	 * Constructor.
	 * 
	 * @param escena escena
	 */
	public EscenaCreadaEvent(Escena escena) {
		this.escena = escena;
	}

	@Override
	protected void dispatch(EscenaCreadaHandler handler) {
		handler.onEscenaCreada(escena);
	}

	@Override
	public GwtEvent.Type<EscenaCreadaHandler> getAssociatedType() {
		return TYPE;
	}
}
