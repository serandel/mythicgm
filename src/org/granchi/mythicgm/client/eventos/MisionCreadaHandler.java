package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se a�ade una misi�n entre escenas.
 */
public interface MisionCreadaHandler extends EventHandler {
	/**
	 * Se ha a�adido una misi�n entre escenas.
	 * 
	 * @param mision misi�n
	 */
	void onMisionCreada(String mision);
}
