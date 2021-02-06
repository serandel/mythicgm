package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.modelo.PJ;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se a�ade un PJ entre escenas.
 */
public interface PJCreadoHandler extends EventHandler {
	/**
	 * Se ha a�adido un PJ entre escenas.
	 * 
	 * @param pj pj
	 */
	void onPJCreado(PJ pj);
}
