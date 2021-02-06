package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se cambia el nombre de la partida.
 * 
 * (Probablemente me lo podría ahorrar, pero por ir probando con uno simple...)
 */
public class MisionEditadaEvent extends
		GwtEvent<MisionEditadaHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<MisionEditadaHandler> TYPE = new GwtEvent.Type<MisionEditadaHandler>();

	private final String antiguaMision, mision;

	/**
	 * Constructor.
	 * 
	 * @param antiguaMision antigua misión
	 * @param mision misión
	 */
	public MisionEditadaEvent(String antiguaMision, String mision) {
		this.antiguaMision = antiguaMision;
		this.mision = mision;
	}

	@Override
	protected void dispatch(MisionEditadaHandler handler) {
		handler.onMisionEditada(antiguaMision, mision);
	}

	@Override
	public GwtEvent.Type<MisionEditadaHandler> getAssociatedType() {
		return TYPE;
	}
}
