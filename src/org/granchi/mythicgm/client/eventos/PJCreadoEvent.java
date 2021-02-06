package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.PJ;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se añade un PJ entre escenas.
 */
public class PJCreadoEvent extends
		GwtEvent<PJCreadoHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<PJCreadoHandler> TYPE = new GwtEvent.Type<PJCreadoHandler>();

	private final PJ pj;

	/**
	 * Constructor.
	 * 
	 * @param pj pj
	 */
	public PJCreadoEvent(PJ pj) {
		this.pj = pj;
	}

	@Override
	protected void dispatch(PJCreadoHandler handler) {
		handler.onPJCreado(pj);
	}

	@Override
	public GwtEvent.Type<PJCreadoHandler> getAssociatedType() {
		return TYPE;
	}
}
