package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se borra un PNJ entre escenas.
 */
public interface PNJBorradoHandler extends EventHandler {
	/**
	 * Se ha borrado un PNJ entre escenas.
	 * 
	 * @param pnj pnj
	 */
	void onPNJBorrado(String pnj);
}
