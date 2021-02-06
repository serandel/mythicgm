package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.ComponenteEscena;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se a�ade un nuevo componente a una escena.
 */
public interface EscenaComponenteCreadoHandler extends EventHandler {
	/**
	 * Se ha a�adido un nuevo componente a una escena.
	 * 
	 * @param componente nuevo componente
	 */
	void onComponenteEscenaCreado(ComponenteEscena componente);
}
