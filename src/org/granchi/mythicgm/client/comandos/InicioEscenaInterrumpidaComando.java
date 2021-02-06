package org.granchi.mythicgm.client.comandos;

import org.granchi.mythicgm.client.MythicGM;
import org.granchi.mythicgm.client.sucesos.EventoAleatorioView;
import org.granchi.mythicgm.modelo.Escena;

/**
 * Panel para rellenar la interrupción de la premisa de una escena.
 */
public class InicioEscenaInterrumpidaComando extends
		InicioEscenaConCambiosComando {
	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 * @param escena
	 *            escena que se está construyendo
	 */
	public InicioEscenaInterrumpidaComando(MythicGM mythicGM, Escena escena) {
		super(mythicGM, escena);
	}

	@Override
	protected void addComponentesEspecificos() {
		EventoAleatorioView eav = new EventoAleatorioView(escena.getPremisa().getCausaInterrupcion());
		eav.addStyleName("mythicSubPanel");
		add(eav);
	}
}
