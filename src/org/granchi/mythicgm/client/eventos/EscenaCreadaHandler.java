package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.Escena;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se crea una escena.
 */
public interface EscenaCreadaHandler extends EventHandler {
	/**
	 * Se ha creado una escena.
	 * 
	 * @param escena escena
	 */
	void onEscenaCreada(Escena escena);
}
