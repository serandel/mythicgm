package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.PJ;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se edita un PJ, que no tiene que ser entre escenas.
 */
public interface PJEditadoHandler extends EventHandler {
	/**
	 * Se ha editado un PJ entre escenas.
	 * 
	 * @param pj pj
	 */
	void onPJEditado(PJ pj);
}
