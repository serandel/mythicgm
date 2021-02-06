package org.granchi.mythicgm.client.eventos;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando se borra una misi�n entre escenas.
 */
public interface MisionBorradaHandler extends EventHandler {
	/**
	 * Se ha borrado una misi�n entre escenas.
	 * 
	 * @param mision misi�n
	 */
	void onMisionBorrada(String mision);
}
