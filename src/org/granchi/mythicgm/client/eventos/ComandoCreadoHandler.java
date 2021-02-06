package org.granchi.mythicgm.client.eventos;

import org.granchi.mythicgm.client.PanelComando;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler para recibir comandos recién creados.
 */
public interface ComandoCreadoHandler extends EventHandler {
	/**
	 * Se ha creado un nuevo comando.
	 * 
	 * @param comando nuevo comando
	 */
	void onComandoCreado(PanelComando comando);
}
