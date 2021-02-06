package org.granchi.mythicgm.client.comandos;

import org.granchi.mythicgm.client.MythicGM;
import org.granchi.mythicgm.client.PanelComando;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.client.util.IntroPulsaBotonKeyPressHandler;
import org.granchi.mythicgm.modelo.Escena;
import org.granchi.mythicgm.modelo.PreguntaSiNo;
import org.granchi.mythicgm.modelo.Suceso;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Focusable;

/**
 * Un panel que genera una pregunta de sí o no.
 * 
 * @author serandel
 */
public class PreguntaSiNoComando extends PanelComando {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	private DatosPreguntaSiNoComposite datos;

	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 * @param escena
	 *            escena a la que se refiere la pregunta
	 */
	public PreguntaSiNoComando(MythicGM mythicGM, Escena escena) {
		super(mythicGM, escena, cadenas.preguntar());

		addStyleName("preguntaSiNo");
	}

	@Override
	protected void addComponentes() {
		datos = new DatosPreguntaSiNoComposite();
		add(datos);

		// Esto es un poco hack pero sospecho que no hay propiedad default
		// accesible mediante el elemento del botón
		KeyPressHandler handler = new IntroPulsaBotonKeyPressHandler(pnlBotones
				.getBtnAceptar());
		datos.addKeyPressHandler(handler);
	}

	@Override
	protected Focusable getComponentePorDefecto() {
		return datos;
	}

	@Override
	protected Resultado onValido() {
		String pregunta = datos.getPregunta();
		if (pregunta.isEmpty()) {
			pregunta = cadenas.preguntaDesconocida();
		}

		PreguntaSiNo p = new PreguntaSiNo(escena, pregunta, datos
				.getIndProbabilidad());

		return new Resultado(
				null,
				new Suceso[] { p },
				(p.getGeneraEventoAleatorio() ? new PanelComando[] { new EventoAleatorioComando(
						mythicGM) }
						: null), null);
	}
}
