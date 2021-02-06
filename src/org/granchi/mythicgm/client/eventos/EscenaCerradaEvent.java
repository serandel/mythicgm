package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.Escena;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se cierra una escena.
 */
public class EscenaCerradaEvent extends
		GwtEvent<EscenaCerradaHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<EscenaCerradaHandler> TYPE = new GwtEvent.Type<EscenaCerradaHandler>();

	private final Escena escena;

	/**
	 * Constructor.
	 * 
	 * @param escena escena
	 */
	public EscenaCerradaEvent(Escena escena) {
		this.escena = escena;
	}

	@Override
	protected void dispatch(EscenaCerradaHandler handler) {
		handler.onEscenaCerrada(escena);
	}

	@Override
	public GwtEvent.Type<EscenaCerradaHandler> getAssociatedType() {
		return TYPE;
	}
}
