package org.granchi.mythicgm.client.sucesos;

import org.granchi.mythicgm.client.ComponenteEscenaView;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.modelo.FinEscena;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;

public class FinEscenaView extends ComponenteEscenaView<FinEscena> {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	public FinEscenaView(FinEscena fin) {
		super(fin);
	}

	@Override
	protected void addComponentes() {
		add(new HTML("<b>" + cadenas.finEscena() + "</b>"));
		add(new HTML(componente.getDeltaCaos() > 0 ? cadenas
				.pjsNoControlEscena() : cadenas.pjsSiControlEscena() + 
				" <i>(" 
				+ cadenas.caosSiguienteEscena()
				+ componente.getEscena().getCaosFinal() + ")</i>"
		));
	}
}
