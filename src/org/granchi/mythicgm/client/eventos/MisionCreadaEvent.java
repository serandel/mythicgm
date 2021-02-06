package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se a�ade una misi�n entre escenas.
 */
public class MisionCreadaEvent extends
		GwtEvent<MisionCreadaHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<MisionCreadaHandler> TYPE = new GwtEvent.Type<MisionCreadaHandler>();

	private final String mision;

	/**
	 * Constructor.
	 * 
	 * @param mision misi�n
	 */
	public MisionCreadaEvent(String mision) {
		this.mision = mision;
	}

	@Override
	protected void dispatch(MisionCreadaHandler handler) {
		handler.onMisionCreada(mision);
	}

	@Override
	public GwtEvent.Type<MisionCreadaHandler> getAssociatedType() {
		return TYPE;
	}
}
