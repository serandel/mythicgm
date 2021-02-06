package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se edita un PNJ entre escenas.
 */
public interface PNJEditadoHandler extends EventHandler {
	/**
	 * Se ha editado un PNJ entre escenas.
	 * 
	 * @param antiguoPnj antiguo nombre del pnj
	 * @param pnj pnj
	 */
	void onPNJEditado(String antiguoPnj, String pnj);
}
