package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.PJ;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se borra un PJ entre escenas.
 */
public interface PJBorradoHandler extends EventHandler {
	/**
	 * Se ha borrado un PJ entre escenas.
	 * 
	 * @param pj pj
	 */
	void onPJBorrado(PJ pj);
}
