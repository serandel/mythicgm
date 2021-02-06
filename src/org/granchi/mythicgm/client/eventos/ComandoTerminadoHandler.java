package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.client.PanelComando;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para cuando un comando termina.
 */
public interface ComandoTerminadoHandler extends EventHandler {
	/**
	 * Un comando ha terminado.
	 * 
	 * @param comando comando
	 */
	void onComandoTerminado(PanelComando comando);
}
