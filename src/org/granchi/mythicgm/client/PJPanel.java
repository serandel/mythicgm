package org.granchi.mythicgm.client;

import java.util.Set;

import org.granchi.mythicgm.modelo.PJ;

/**
 * Recubrimiento en un PanelComando de un PJView en modo edición.
 */
public class PJPanel extends PanelComando {
	private PJ pj;
	private PJView pjView;

	public PJPanel(MythicGM mythicGM, PJ pj) {
		super(mythicGM, null);
		this.pj = pj;
		// Creado por el addComponentes
		pjView.mostrar(pj, true);
	}

	@Override
	protected void addComponentes() {
		// No podemos darle el pj porque todavía estamos en el "super"
		pjView = new PJView();
		add(pjView);
	}

	@Override
	protected boolean isNecesarioRefrescarPartida() {
		// Claro que hace falta mostrar al pavo nuevo
		return true;
	}

	@Override
	protected Resultado onValido() {
		// Si era un PJ nuevo se añade, si se estaba editando uno, pues el add
		// del set ya lo ignora.
		Set<PJ> pjs = mythicGM.getPartida().getPjs();
		pjs.add(pj);
		
		return null;
	}
}
