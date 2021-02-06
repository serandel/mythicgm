package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se cambia el nombre de la partida.
 * 
 * (Probablemente me lo podría ahorrar, pero por ir probando con uno simple...)
 */
public class PartidaEditadoNombreEvent extends
		GwtEvent<PartidaEditadoNombreHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<PartidaEditadoNombreHandler> TYPE = new GwtEvent.Type<PartidaEditadoNombreHandler>();

	private final String nombre;

	/**
	 * Constructor.
	 * 
	 * @param nombre
	 *            nuevo nombre de la partida
	 */
	public PartidaEditadoNombreEvent(String nombre) {
		this.nombre = nombre;
	}

	@Override
	protected void dispatch(PartidaEditadoNombreHandler handler) {
		handler.onNombrePartidaEditado(nombre);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PartidaEditadoNombreHandler> getAssociatedType() {
		return TYPE;
	}
}
