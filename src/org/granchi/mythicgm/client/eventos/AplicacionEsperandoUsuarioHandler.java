package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando la aplicaci�n est� esperando interacci�n por parte del
 * usuario.
 */
public interface AplicacionEsperandoUsuarioHandler extends EventHandler {
	/**
	 * La aplicaci�n est� esperando a que el usuario haga algo.
	 */
	void onAplicacionEsperandoUsuario();
}
