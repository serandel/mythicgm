package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se cambia el nombre de la partida.
 * 
 * (Probablemente me lo podría ahorrar, pero por ir probando con uno simple...)
 */
public class PNJEditadoEvent extends
		GwtEvent<PNJEditadoHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<PNJEditadoHandler> TYPE = new GwtEvent.Type<PNJEditadoHandler>();

	private final String antiguoPnj, pnj;

	/**
	 * Constructor.
	 * 
	 * @param antiguoPnj antiguo nombre del PNJ
	 * @param pnj PNJ
	 */
	public PNJEditadoEvent(String antiguoPnj, String pnj) {
		this.antiguoPnj = antiguoPnj;
		this.pnj = pnj;
	}

	@Override
	protected void dispatch(PNJEditadoHandler handler) {
		handler.onPNJEditado(antiguoPnj, pnj);
	}

	@Override
	public GwtEvent.Type<PNJEditadoHandler> getAssociatedType() {
		return TYPE;
	}
}
