package org.granchi.mythicgm.client.comandos;

import org.granchi.mythicgm.client.MythicGM;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.client.sucesos.PreguntaSiNoView;
import org.granchi.mythicgm.modelo.Escena;
import org.granchi.mythicgm.modelo.EventoAleatorio;
import org.granchi.mythicgm.modelo.PremisaEscena;
import org.granchi.mythicgm.modelo.Suceso;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Panel para rellenar la alteración de la premisa de una escena.
 */
public class InicioEscenaAlteradaComando extends InicioEscenaConCambiosComando {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);
	
	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 * @param escena
	 *            escena que se está construyendo
	 */
	public InicioEscenaAlteradaComando(MythicGM mythicGM, Escena escena) {
		super(mythicGM, escena);
	}

	@Override
	protected void addComponentesEspecificos() {
		// Para poder borrarlo en cuanto se haga la pregunta
		final VerticalPanel panel = new VerticalPanel();
		panel.addStyleName("mythicSubPanel");
		// Para que coja el color del borde
		panel.addStyleName("preguntaSiNo");
		add(panel);

		// Por si quiere hacer una pregunta
		final DatosPreguntaSiNoComposite datos = new DatosPreguntaSiNoComposite(
				"<b>" +cadenas.preguntaAlteracion() + "</b>");
		panel.add(datos);

		FlowPanel pnlBotonPreguntar = new FlowPanel();
		pnlBotonPreguntar.addStyleName("panelBotones");
		panel.add(pnlBotonPreguntar);
		
		Button btnPreguntar = new Button(cadenas.preguntar());
		btnPreguntar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// Cambiar los datos de la pregunta por la respuesta
				PremisaEscena premisa = escena.getPremisa();
				premisa.preguntarAclaracionAlteracion(datos.getPregunta(),
						datos.getIndProbabilidad());
				panel.clear();
				panel.add(new PreguntaSiNoView(premisa
						.getAclaracionAlteracion()));

				// No olvidar que una pregunta puede traer un evento aleatorio
				if (premisa.getAclaracionAlteracion()
						.getGeneraEventoAleatorio()) {
					sucesos = new Suceso[] { new EventoAleatorio(escena) };
				}
			}
		});
		pnlBotonPreguntar.add(btnPreguntar);
	}
}
