package org.granchi.mythicgm.client.comandos;

import org.granchi.mythicgm.client.MythicGM;
import org.granchi.mythicgm.client.PanelComando;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.modelo.Notas;
import org.granchi.mythicgm.modelo.Suceso;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Suceso que permite insertar notas en mitad de la partida.
 */
public class NotasComando extends PanelComando {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);
	
	protected TextArea txtNotas;

	public NotasComando(MythicGM mythicGM) {
		super(mythicGM, mythicGM.getPartida().getEscenaActual(), cadenas.anotar());
	}

	@Override
	protected void addComponentes() {
		addStyleName("notas");
		add(new HTML("<b>" + cadenas.notas() + "</b>"));

		txtNotas = new TextArea();
		txtNotas.getElement().setClassName("txtNotas");
		add(txtNotas);
	}

	@Override
	protected Focusable getComponentePorDefecto() {
		return txtNotas;
	}

	/**
	 * Las notas no son válidas si están vacías.
	 */
	@Override
	protected boolean isValido() {
		return !txtNotas.getText().trim().isEmpty();
	}

	@Override
	protected Resultado onValido() {
		return new Resultado(null, new Suceso[] { new Notas(mythicGM
				.getPartida().getEscenaActual(), txtNotas.getText().trim()) },
				null, null);
	}

	@Override
	protected void onNoValido() {
		// Cuando sólo hay intros se quedaba raro
		txtNotas.setText("");
		txtNotas.setFocus(true);
	}
}
