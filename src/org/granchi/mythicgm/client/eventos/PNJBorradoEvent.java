package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se borra un PNJ entre escenas.
 */
public class PNJBorradoEvent extends
		GwtEvent<PNJBorradoHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<PNJBorradoHandler> TYPE = new GwtEvent.Type<PNJBorradoHandler>();

	private final String pnj;

	/**
	 * Constructor.
	 * 
	 * @param pnj PNJ
	 */
	public PNJBorradoEvent(String pnj) {
		this.pnj = pnj;
	}

	@Override
	protected void dispatch(PNJBorradoHandler handler) {
		handler.onPNJBorrado(pnj);
	}

	@Override
	public GwtEvent.Type<PNJBorradoHandler> getAssociatedType() {
		return TYPE;
	}
}
