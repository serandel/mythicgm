package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se borra una misión entre escenas.
 */
public interface MisionBorradaHandler extends EventHandler {
	/**
	 * Se ha borrado una misión entre escenas.
	 * 
	 * @param mision misión
	 */
	void onMisionBorrada(String mision);
}
