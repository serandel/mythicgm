package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.Escena;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se cambia el nombre de una escena.
 * 
 * (Probablemente me lo podría ahorrar también, como el del nombre de la
 * partida.)
 * @param <escena>
 */
public class EscenaEditadoNombreEvent extends
		GwtEvent<EscenaEditadoNombreHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<EscenaEditadoNombreHandler> TYPE = new GwtEvent.Type<EscenaEditadoNombreHandler>();

	private final Escena escena;

	/**
	 * Constructor.
	 * 
	 * @param escena escena
	 */
	public EscenaEditadoNombreEvent(Escena escena) {
		this.escena = escena;
	}

	@Override
	protected void dispatch(EscenaEditadoNombreHandler handler) {
		handler.onNombreEscenaEditado(escena);
	}

	@Override
	public GwtEvent.Type<EscenaEditadoNombreHandler> getAssociatedType() {
		return TYPE;
	}
}
