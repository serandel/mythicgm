package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se añade un PNJ entre escenas.
 */
public interface PNJCreadoHandler extends EventHandler {
	/**
	 * Se ha añadido un PNJ entre escenas.
	 * 
	 * @param pnj pnj
	 */
	void onPNJCreado(String pnj);
}
