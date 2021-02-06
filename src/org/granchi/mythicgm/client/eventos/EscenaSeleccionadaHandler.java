package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.Escena;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se selecciona una nueva escena.
 */
public interface EscenaSeleccionadaHandler extends EventHandler {
	/**
	 * Se ha seleccionado una nueva escena.
	 * 
	 * @param escena escena
	 */
	void onEscenaSeleccionada(Escena escena);
}
