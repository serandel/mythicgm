package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.ComponenteEscena;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando se a�ade un componente nuevo a una escena.
 * 
 * (El mismo componente ya sabe cu�l es la escena.)
 */
public class EscenaComponenteCreadoEvent extends
		GwtEvent<EscenaComponenteCreadoHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<EscenaComponenteCreadoHandler> TYPE = new GwtEvent.Type<EscenaComponenteCreadoHandler>();

	private final ComponenteEscena componente;

	/**
	 * Constructor.
	 * 
	 * @param nombre
	 *            nuevo nombre de la partida
	 */
	public EscenaComponenteCreadoEvent(ComponenteEscena componente) {
		this.componente = componente;
	}

	@Override
	protected void dispatch(EscenaComponenteCreadoHandler handler) {
		handler.onComponenteEscenaCreado(componente);
	}

	@Override
	public GwtEvent.Type<EscenaComponenteCreadoHandler> getAssociatedType() {
		return TYPE;
	}
}
