package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.Escena;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se cierra una escena.
 */
public interface EscenaCerradaHandler extends EventHandler {
	/**
	 * Se ha cerrado una escena.
	 * 
	 * @param escena escena
	 */
	void onEscenaCerrada(Escena escena);
}
