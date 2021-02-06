package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando la aplicación esté esperando interacción por parte del
 * usuario.
 */
public interface AplicacionEsperandoUsuarioHandler extends EventHandler {
	/**
	 * La aplicación está esperando a que el usuario haga algo.
	 */
	void onAplicacionEsperandoUsuario();
}
