package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Evento lanzado cuando la aplicación se queda sin nada que hacer.
 */
public class AplicacionEsperandoUsuarioEvent extends GwtEvent<AplicacionEsperandoUsuarioHandler> {
	// Tag para el evento
	public static final GwtEvent.Type<AplicacionEsperandoUsuarioHandler> TYPE = new GwtEvent.Type<AplicacionEsperandoUsuarioHandler>();

	@Override
	protected void dispatch(AplicacionEsperandoUsuarioHandler handler) {
		handler.onAplicacionEsperandoUsuario();
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AplicacionEsperandoUsuarioHandler> getAssociatedType() {
		return TYPE;
	}
}
