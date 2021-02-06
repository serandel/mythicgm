package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.Escena;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se cambia el nombre de una escena.
 */
public interface EscenaEditadoNombreHandler extends EventHandler {
	/**
	 * Se ha editado el nombre de una escena.
	 * 
	 * @param escena escena
	 */
	void onNombreEscenaEditado(Escena escena);
}
