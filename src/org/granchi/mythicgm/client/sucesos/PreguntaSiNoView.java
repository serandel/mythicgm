package org.granchi.mythicgm.client.sucesos;

import org.granchi.mythicgm.client.ComponenteEscenaView;
import org.granchi.mythicgm.modelo.PreguntaSiNo;

import com.google.gwt.user.client.ui.HTML;

/**
 * Un panel que muestra una pregunta de sí o no.
 * 
 * @author serandel
 */
public class PreguntaSiNoView extends ComponenteEscenaView<PreguntaSiNo> {
	/**
	 * Constructor.
	 * 
	 * @param pregunta
	 *            pregunta
	 */
	public PreguntaSiNoView(PreguntaSiNo pregunta) {
		super(pregunta);
	}

	@Override
	protected void addComponentes() {
		addStyleName("preguntaSiNo");

		add(new HTML("<b><i>" + componente.getPregunta() + "</i> -- ("
				+ PreguntaSiNo.PROBABILIDAD[componente.getProbabilidad()]
				+ ")</b><br/><i>(" + componente.getTirada() + ")</i>&nbsp;"
				+ componente.getRespuesta(), true));
	}
}
