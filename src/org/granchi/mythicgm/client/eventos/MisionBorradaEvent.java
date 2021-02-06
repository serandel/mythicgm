package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se borra una misi�n entre escenas.
 */
public class MisionBorradaEvent extends
		GwtEvent<MisionBorradaHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<MisionBorradaHandler> TYPE = new GwtEvent.Type<MisionBorradaHandler>();

	private final String mision;

	/**
	 * Constructor.
	 * 
	 * @param mision misi�n
	 */
	public MisionBorradaEvent(String mision) {
		this.mision = mision;
	}

	@Override
	protected void dispatch(MisionBorradaHandler handler) {
		handler.onMisionBorrada(mision);
	}

	@Override
	public GwtEvent.Type<MisionBorradaHandler> getAssociatedType() {
		return TYPE;
	}
}
