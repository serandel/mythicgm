package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.Escena;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se selecciona una nueva escena para visualizar.
 */
public class EscenaSeleccionadaEvent extends
		GwtEvent<EscenaSeleccionadaHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<EscenaSeleccionadaHandler> TYPE = new GwtEvent.Type<EscenaSeleccionadaHandler>();

	private final Escena escena;

	/**
	 * Constructor.
	 * 
	 * @param escena escena
	 */
	public EscenaSeleccionadaEvent(Escena escena) {
		this.escena = escena;
	}

	@Override
	protected void dispatch(EscenaSeleccionadaHandler handler) {
		handler.onEscenaSeleccionada(escena);
	}

	@Override
	public GwtEvent.Type<EscenaSeleccionadaHandler> getAssociatedType() {
		return TYPE;
	}
}
