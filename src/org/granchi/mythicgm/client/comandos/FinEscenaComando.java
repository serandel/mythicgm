package org.granchi.mythicgm.client.comandos;

import org.granchi.mythicgm.client.MythicGM;
import org.granchi.mythicgm.client.PanelComando;
import org.granchi.mythicgm.client.recursos.Cadenas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RadioButton;

public class FinEscenaComando extends PanelComando {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	private RadioButton pjsControl[];

	private static final String GRUPO_RADIOBUTTONS = "control";

	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 */
	public FinEscenaComando(MythicGM mythicGM) {
		super(mythicGM, mythicGM.getPartida().getEscenaActual());

		if (escena == null) {
			throw new IllegalStateException("Escena nula");
		}
		if (!escena.isAbierta()) {
			throw new IllegalStateException("Escena ya cerrada");
		}
	}

	@Override
	protected void addComponentes() {
		add(new HTML("<b>" + cadenas.finEscena() + "</b>"));

		add(new HTML(cadenas.pjsControlEscena()));

		pjsControl = new RadioButton[2];
		pjsControl[0] = new RadioButton(GRUPO_RADIOBUTTONS, cadenas.si()
				+ " <i>(" + cadenas.caosSiguienteEscena()
				+ (escena.getCaos() == 1 ? "1" : escena.getCaos() - 1)
				+ ")</i>", true);
		pjsControl[1] = new RadioButton(GRUPO_RADIOBUTTONS, cadenas.no()
				+ " <i>(" + cadenas.caosSiguienteEscena()
				+ (escena.getCaos() == 9 ? "9" : escena.getCaos() + 1)
				+ ")</i>", true);

		// Éste seleccionado por defecto :D
		pjsControl[1].setValue(true);

		for (RadioButton r : pjsControl) {
			// TODO no parece funcionar una mierda
//			r.addKeyPressHandler(new IntroPulsaBotonKeyPressHandler(pnlBotones.getBtnAceptar()));
			add(r);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.granchi.mythicgm.client.PanelComando#getComponentePorDefecto()
	 */
	@Override
	protected Focusable getComponentePorDefecto() {
		return pnlBotones.getBtnAceptar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.granchi.mythicgm.client.PanelComando#isNecesarioRefrescarPartida()
	 */
	@Override
	protected boolean isNecesarioRefrescarPartida() {
		// Para que se vea la escena cerrada y tal
		// TODO comprobarlo
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.granchi.mythicgm.client.PanelComando#onValido()
	 */
	@Override
	protected Resultado onValido() {
		escena.createFin(pjsControl[0].getValue() ? -1 : 1);
		return new Resultado(null, null, null, escena.getFin());
	}
}
