package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se añade un PNJ entre escenas.
 */
public class PNJCreadoEvent extends
		GwtEvent<PNJCreadoHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<PNJCreadoHandler> TYPE = new GwtEvent.Type<PNJCreadoHandler>();

	private final String pnj;

	/**
	 * Constructor.
	 * 
	 * @param pnj PNJ
	 */
	public PNJCreadoEvent(String pnj) {
		this.pnj = pnj;
	}

	@Override
	protected void dispatch(PNJCreadoHandler handler) {
		handler.onPNJCreado(pnj);
	}

	@Override
	public GwtEvent.Type<PNJCreadoHandler> getAssociatedType() {
		return TYPE;
	}
}
