package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.PJ;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se edita un PJ, que no tiene que ser entre escenas.
 */
public class PJEditadoEvent extends
		GwtEvent<PJEditadoHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<PJEditadoHandler> TYPE = new GwtEvent.Type<PJEditadoHandler>();

	private final PJ pj;

	/**
	 * Constructor.
	 * 
	 * @param pj pj
	 */
	public PJEditadoEvent(PJ pj) {
		this.pj = pj;
	}

	@Override
	protected void dispatch(PJEditadoHandler handler) {
		handler.onPJEditado(pj);
	}

	@Override
	public GwtEvent.Type<PJEditadoHandler> getAssociatedType() {
		return TYPE;
	}
}
