package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

public interface PartidaEditadoNombreHandler extends EventHandler {
	/**
	 * Se ha editado el nombre de la partida.
	 * 
	 * @param nombre nuevo nombre
	 */
	void onNombrePartidaEditado(String nombre);
}
