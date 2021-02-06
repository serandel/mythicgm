package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.PJ;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se borra un PJ entre escenas.
 */
public class PJBorradoEvent extends
		GwtEvent<PJBorradoHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<PJBorradoHandler> TYPE = new GwtEvent.Type<PJBorradoHandler>();

	private final PJ pj;

	/**
	 * Constructor.
	 * 
	 * @param pj pj
	 */
	public PJBorradoEvent(PJ pj) {
		this.pj = pj;
	}

	@Override
	protected void dispatch(PJBorradoHandler handler) {
		handler.onPJBorrado(pj);
	}

	@Override
	public GwtEvent.Type<PJBorradoHandler> getAssociatedType() {
		return TYPE;
	}
}
