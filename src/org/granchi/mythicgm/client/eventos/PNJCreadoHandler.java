package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se a�ade un PNJ entre escenas.
 */
public interface PNJCreadoHandler extends EventHandler {
	/**
	 * Se ha a�adido un PNJ entre escenas.
	 * 
	 * @param pnj pnj
	 */
	void onPNJCreado(String pnj);
}
