package org.granchi.mythicgm.client.sucesos;

import org.granchi.mythicgm.client.ComponenteEscenaView;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.modelo.Notas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;

/**
 * Suceso que muestra una nota insertada en mitad de la partida.
 */
public class NotasView extends ComponenteEscenaView<Notas> {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	public NotasView(Notas notas) {
		super(notas);
	}
	
	@Override
	protected void addComponentes() {
		addStyleName("notas");
		
		add(new HTML("<b>" + cadenas.notas() + "</b>"));
		add(new HTML(componente.getNotas()));
	}
}
