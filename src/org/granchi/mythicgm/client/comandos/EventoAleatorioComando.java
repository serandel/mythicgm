package org.granchi.mythicgm.client.comandos;

import org.granchi.mythicgm.client.MythicGM;
import org.granchi.mythicgm.client.PanelComando;
import org.granchi.mythicgm.client.sucesos.EventoAleatorioView;
import org.granchi.mythicgm.modelo.EventoAleatorio;
import org.granchi.mythicgm.modelo.Suceso;
import org.granchi.mythicgm.modelo.EventoAleatorio.Foco;

/**
 * Panel que genera un evento aleatorio.
 */
public class EventoAleatorioComando extends PanelComando {
	private EventoAleatorio ea;

	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 * @param partida
	 *            partida
	 */
	public EventoAleatorioComando(MythicGM mythicGM) {
		super(mythicGM, mythicGM.getPartida().getEscenaActual());

		addStyleName("eventoAleatorio");
	}

	@Override
	protected void addComponentes() {
		// Si lo hacemos en el constructor no está a tiempo para este método
		ea = new EventoAleatorio(escena);

		add(new EventoAleatorioView(ea));
	}

	@Override
	protected boolean isNecesarioRefrescarPartida() {
		// Lo único que actualizamos del tirón es si se cierra un objetivo,
		// para que salga tachado
		return ea.getFoco() == Foco.CERRAR_OBJETIVO;
	}

	@Override
	protected Resultado onValido() {
		if (ea.getFoco() == Foco.CERRAR_OBJETIVO) {
			mythicGM.getPartida().getEscenaActual().cerrarObjetivo(
					ea.getObjetoFoco());
		}

		return new Resultado(null, new Suceso[] { ea }, null, null);
	}
}