package org.granchi.mythicgm.client.comandos;

import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.modelo.PreguntaSiNo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Componente con los datos necesarios para hacer una pregunta de sí o no.
 */
public class DatosPreguntaSiNoComposite extends Composite implements
		HasKeyPressHandlers, Focusable {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	private TextBox txtPregunta;
	private ListBox lstProbabilidad;

	/**
	 * Constructor.
	 */
	public DatosPreguntaSiNoComposite() {
		this(null);
	}

	/**
	 * Constructor.
	 * 
	 * @param titulo
	 *            titulo
	 */
	public DatosPreguntaSiNoComposite(String titulo) {
		VerticalPanel panel = new VerticalPanel();
		initWidget(panel);

		if (titulo == null || titulo.trim().isEmpty()) {
			titulo = "<b>" + cadenas.pregunta() + "</b>";
		}

		panel.add(new HTML(titulo, true));

		txtPregunta = new TextBox();
		// No pinta borde porque no es un mythic panel
		panel.addStyleName("preguntaSiNo");
		panel.add(txtPregunta);

		panel
				.add(new HTML("<br/><b>" + cadenas.probabilidadSi() + "</b>",
						true));
		lstProbabilidad = new ListBox();
		for (String p : PreguntaSiNo.PROBABILIDAD) {
			lstProbabilidad.addItem(p);
		}
		lstProbabilidad.setVisibleItemCount(lstProbabilidad.getItemCount());
		// Seleccionar el 50/50
		lstProbabilidad.setItemSelected(4, true);
		panel.add(lstProbabilidad);
	}

	/**
	 * Devuelve el texto de la pregunta.
	 * 
	 * @return texto de la pregunta, sin caracteres en blanco al principio ni al
	 *         final
	 */
	public String getPregunta() {
		return txtPregunta.getText().trim();
	}

	/**
	 * Devuelve el índice de la probabilidad seleccionada.
	 * 
	 * @return índice de la probabilidad seleccionada
	 */
	public int getIndProbabilidad() {
		return lstProbabilidad.getSelectedIndex();
	}

	// ///////////////////////////////////////////////////////////////

	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		final HandlerRegistration hr1 = txtPregunta.addKeyPressHandler(handler);
		final HandlerRegistration hr2 = lstProbabilidad
				.addKeyPressHandler(handler);

		// Sé que esto es virguería por mi parte, pero así está el remove
		// solucionado también :)
		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				hr1.removeHandler();
				hr2.removeHandler();
			}
		};
	}

	// Todo lo de focusable lo delegamos en txtPregunta

	@Override
	public int getTabIndex() {
		return txtPregunta.getTabIndex();
	}

	@Override
	public void setAccessKey(char key) {
		txtPregunta.setAccessKey(key);
	}

	@Override
	public void setFocus(boolean focused) {
		txtPregunta.setFocus(focused);
	}

	@Override
	public void setTabIndex(int index) {
		txtPregunta.setTabIndex(index);
	}

}
