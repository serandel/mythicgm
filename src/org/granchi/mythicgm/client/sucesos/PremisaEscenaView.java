package org.granchi.mythicgm.client.sucesos;

import org.granchi.mythicgm.client.ComponenteEscenaView;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.modelo.PremisaEscena;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;

/**
 * Panel que muestra la premisa de la escena.
 */
public class PremisaEscenaView extends ComponenteEscenaView<PremisaEscena> {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	/**
	 * Constructor.
	 * 
	 * @param componente
	 *            componente de la escena
	 */
	public PremisaEscenaView(PremisaEscena componente) {
		super(componente);
	}

	@Override
	protected void addComponentes() {
		if (componente.getSemillaPremisa() != null) {
			add(new HTML("<b>" + cadenas.semillaPremisa() + "</b>"));
			EventoAleatorioView eav = new EventoAleatorioView(componente
					.getSemillaPremisa());
			eav.addStyleName("mythicSubPanel");
			add(eav);
		}

		HTML h = new HTML("<b>"
				+ (componente.getCambioPremisa() == null ? cadenas.premisa()
						: cadenas.premisaOriginal()) + "</b><br/>"
				+ componente.getPremisa());
		if (componente.getSemillaPremisa() != null) {
			h.addStyleName("cabecera");
		}
		add(h);

		if (componente.getCambioPremisa() != null) {
			HTML htmlCaos = new HTML("<b>"
					+ cadenas.tiradaCaos()
					+ "</b> <i>("
					+ componente.getTiradaCaos()
					+ ", "
					+ (componente.getTiradaCaos() % 2 == 0 ? cadenas.par()
							: cadenas.impar())
					+ ")</i> <b><= Caos de la escena</b> <i>("
					+ componente.getEscena().getCaos() + ")</i>");
			htmlCaos.addStyleName("cabecera");
			add(htmlCaos);

			for (String s : componente.getExplicacion()) {
				add(new HTML(s, true));
			}

			switch (componente.getCambioPremisa()) {
			case ALTERADA:
				if (componente.getAclaracionAlteracion() != null) {
					PreguntaSiNoView pregunta = new PreguntaSiNoView(componente
							.getAclaracionAlteracion());
					pregunta.addStyleName("mythicSubPanel");
					add(pregunta);
				}
				break;
			case INTERRUMPIDA:
				EventoAleatorioView eav = new EventoAleatorioView(componente
						.getCausaInterrupcion());
				eav.addStyleName("mythicSubPanel");
				add(eav);
			}

			HTML htmlPremisaModificada = new HTML("<b>" + cadenas.premisa()
					+ "</b><br/>" + componente.getPremisaModificada());
			htmlPremisaModificada.addStyleName("cabecera");
			add(htmlPremisaModificada);
		}
	}
}
