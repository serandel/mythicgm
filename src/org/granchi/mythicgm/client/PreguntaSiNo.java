package org.granchi.mythicgm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Un panel que implementa una pregunta de sí o no.
 * 
 * @author serandel
 */
public class PreguntaSiNo extends VerticalPanel {
	private static final String PROBABILIDAD[] = { "Imposible", "No fastidies",
			"Muy improbable", "Improbable", "50/50", "Algo probable",
			"Probable", "Muy probable", "Casi seguro", "Seguro", "Garantizado" };

	/**
	 * Probabilidades de crítico, éxito y pifia indexadas por caos y
	 * probabilidad
	 */
	private static final int DESTINO[][][] = {
			{ { 0, -20, 77 }, { 0, 0, 81 }, { 1, 5, 82 }, { 1, 5, 82 },
					{ 2, 10, 83 }, { 4, 20, 85 }, { 5, 25, 86 }, { 9, 45, 90 },
					{ 10, 50, 91 }, { 11, 55, 92 }, { 16, 80, 97 } },
			{ { 0, 0, 81 }, { 1, 5, 82 }, { 1, 5, 82 }, { 2, 10, 83 },
					{ 3, 15, 84 }, { 5, 25, 86 }, { 7, 35, 88 },
					{ 10, 50, 91 }, { 11, 55, 92 }, { 13, 65, 94 },
					{ 16, 85, 97 } },
			{ { 0, 0, 81 }, { 1, 5, 82 }, { 2, 10, 83 }, { 3, 15, 84 },
					{ 5, 25, 86 }, { 9, 45, 90 }, { 10, 50, 91 },
					{ 13, 65, 94 }, { 15, 75, 96 }, { 16, 80, 97 },
					{ 18, 90, 99 } },
			{ { 1, 5, 82 }, { 2, 10, 83 }, { 3, 15, 84 }, { 4, 20, 85 },
					{ 7, 35, 88 }, { 10, 50, 91 }, { 11, 55, 92 },
					{ 15, 75, 96 }, { 16, 80, 97 }, { 16, 85, 97 },
					{ 19, 95, 100 } },
			{ { 1, 5, 82 }, { 3, 15, 84 }, { 5, 25, 86 }, { 7, 35, 88 },
					{ 10, 50, 91 }, { 13, 65, 94 }, { 15, 75, 96 },
					{ 16, 85, 97 }, { 18, 90, 99 }, { 18, 90, 99 },
					{ 19, 95, 100 } },
			{ { 2, 10, 83 }, { 5, 25, 86 }, { 9, 45, 90 }, { 10, 50, 91 },
					{ 13, 65, 94 }, { 16, 80, 97 }, { 16, 85, 97 },
					{ 18, 90, 99 }, { 19, 95, 100 }, { 19, 95, 100 },
					{ 20, 100, 0 } },
			{ { 3, 15, 84 }, { 7, 35, 88 }, { 10, 50, 91 }, { 11, 55, 92 },
					{ 15, 75, 96 }, { 16, 85, 97 }, { 18, 90, 99 },
					{ 19, 95, 100 }, { 19, 95, 100 }, { 19, 95, 100 },
					{ 29, 100, 0 } },
			{ { 5, 25, 86 }, { 10, 50, 91 }, { 13, 65, 94 }, { 15, 75, 96 },
					{ 16, 85, 97 }, { 18, 90, 99 }, { 19, 95, 100 },
					{ 19, 95, 100 }, { 20, 100, 0 }, { 22, 110, 0 },
					{ 26, 130, 0 } },
			{ { 10, 50, 91 }, { 15, 75, 96 }, { 16, 85, 97 }, { 18, 90, 99 },
					{ 19, 95, 100 }, { 19, 95, 100 }, { 20, 100, 0 },
					{ 21, 105, 0 }, { 23, 105, 0 }, { 25, 125, 0 },
					{ 26, 145, 0 } } };

	private TextBox txtPregunta;
	private ListBox lstProbabilidad;
	private Button btnPreguntar;
	private Anchor ancCancelar;

	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 * @param caos
	 *            nivel de caos
	 */
	public PreguntaSiNo(final MythicGM mythicGM, final Partida partida) {
		getElement().setClassName("preguntaSiNo");

		add(new HTML("<b><i>Pregunta</i> (s&iacute;/no)</b>", true));

		txtPregunta = new TextBox();
		add(txtPregunta);

		add(new HTML("<br/><b>Probabilidad de un s&iacute;</b>", true));
		lstProbabilidad = new ListBox();
		for (String p : PROBABILIDAD) {
			lstProbabilidad.addItem(p);
		}
		lstProbabilidad.setVisibleItemCount(lstProbabilidad.getItemCount());
		// Seleccionar el 50/50
		lstProbabilidad.setItemSelected(4, true);
		add(lstProbabilidad);

		PanelBotones pnlBotones = new PanelBotones();
		add(pnlBotones);

		ancCancelar = new Anchor("Cancelar");
		ancCancelar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				removeFromParent();
				mythicGM.onHijoTermina(PreguntaSiNo.this, false);
			}
		});
		pnlBotones.add(ancCancelar);

		btnPreguntar = new Button("Preguntar");
		btnPreguntar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clear();
				String pregunta = txtPregunta.getText().trim();
				int indProbabilidad = lstProbabilidad.getSelectedIndex();

				add(new HTML("<b><i>"
						+ (pregunta.length() == 0 ? "Pregunta desconocida"
								: pregunta) + "</i> -- ("
						+ lstProbabilidad.getItemText(indProbabilidad)
						+ ")</b>"));

				int tirada = Random.nextInt(100) + 1;

				String strTirada = "<i>(" + tirada + ")</i>&nbsp;";
				int probabilidades[] = DESTINO[partida.getCaos() - 1][indProbabilidad];
				if (tirada <= probabilidades[0]) {
					// Crítico
					add(new HTML(strTirada + "S&iacute; excepcional"));
				} else if (tirada <= probabilidades[1]) {
					// Sí
					add(new HTML(strTirada + "S&iacute;"));
				} else if (tirada < probabilidades[2]) {
					// No
					add(new HTML(strTirada + "No"));
				} else {
					// Pifia
					add(new HTML(strTirada + "No excepcional"));
				}
				
				// Posible evento aleatorio si hay dobles
				if ((tirada / 10 == tirada % 10) && (tirada % 10 <= partida.getCaos())) {
					add(new EventoAleatorio(mythicGM, partida));
				} else {
					mythicGM.onHijoTermina(PreguntaSiNo.this, false);
				}
			}
		});
		pnlBotones.add(btnPreguntar);

		// Sin el deferred no iría bien, tiene que añadirse antes
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				txtPregunta.setFocus(true);
			}
		});
		// Esto es un poco hack pero sospecho que no hay propiedad default
		// accesible mediante el elemento del botón
		KeyPressHandler handler = new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				int tecla = event.getCharCode();
				if (tecla == KeyCodes.KEY_ENTER) {
					btnPreguntar.click();
				}
				
				// No puedo hacer lo mismo con el Anchor y un ESC, porque Anchor
				// no tiene click
				
				// Si no, se lo lleva luego el botón al que le hacemos el focus
				event.stopPropagation();
			}
		};
		txtPregunta.addKeyPressHandler(handler);
		lstProbabilidad.addKeyPressHandler(handler);
	}
}
