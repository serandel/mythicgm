package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se añade una misión entre escenas.
 */
public interface MisionCreadaHandler extends EventHandler {
	/**
	 * Se ha añadido una misión entre escenas.
	 * 
	 * @param mision misión
	 */
	void onMisionCreada(String mision);
}
