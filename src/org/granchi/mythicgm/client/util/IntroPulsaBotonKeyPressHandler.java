package org.granchi.mythicgm.client.util;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;

/**
 * Un KeyPressHandler que cuando hay intro pulsa un boton.
 * 
 * Desgraciadamente, no se puede hacer para que el escape haga el aceptar.
 */
// TODO seguro que no se puede?
// TODO y se puede hacer click con un anchor?
public class IntroPulsaBotonKeyPressHandler implements KeyPressHandler {

	private Button boton;

	/**
	 * Constructor.
	 * 
	 * @param control
	 *            control que se debe pulsar
	 */
	public IntroPulsaBotonKeyPressHandler(Button boton) {
		if (boton == null) {
			throw new IllegalArgumentException("Boton nulo");
		}
		this.boton = boton;
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		int tecla = event.getCharCode();
		
		if (tecla == KeyCodes.KEY_ENTER) {
			boton.click();
		}

		// No puedo hacer lo mismo con el Anchor y un ESC, porque Anchor
		// no tiene click

		// Si no, se lo lleva luego el botón al que le hacemos el focus
		event.stopPropagation();
	}
}
