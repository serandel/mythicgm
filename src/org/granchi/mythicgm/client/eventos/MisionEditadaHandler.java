package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se edita una misión entre escenas.
 */
public interface MisionEditadaHandler extends EventHandler {
	/**
	 * Se ha editado una misión entre escenas.
	 * 
	 * @param antiguaMision antigua misión
	 * @param mision misión
	 */
	void onMisionEditada(String antiguaMision, String mision);
}
