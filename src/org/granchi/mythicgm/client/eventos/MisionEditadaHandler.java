package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se edita una misi�n entre escenas.
 */
public interface MisionEditadaHandler extends EventHandler {
	/**
	 * Se ha editado una misi�n entre escenas.
	 * 
	 * @param antiguaMision antigua misi�n
	 * @param mision misi�n
	 */
	void onMisionEditada(String antiguaMision, String mision);
}
