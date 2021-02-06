package org.granchi.mythicgm.client.util;

import org.granchi.mythicgm.client.recursos.Cadenas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Diálogo que pide una cadena de texto.
 */
public class DialogoInput extends DialogBox {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);
	
	private TextBox txtInput;

	/**
	 * Constructor.
	 * 
	 * @param titulo
	 *            titulo
	 * @param onAceptar comando a ejecutar si se acepta
	 */
	public DialogoInput(String titulo, 
			final ParametrizedCommand<String> onAceptar) {
		this(titulo, "", onAceptar);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param titulo
	 *            titulo
	 * @param valorInicial valor inicial de la caja de texto
	 * @param onAceptar comando a ejecutar si se acepta            
	 */
	public DialogoInput(String titulo, String valorInicial,
			final ParametrizedCommand<String> onAceptar) {
		// Modal sin autohide
		super(false, true);
		
		addStyleName("dialogoInput");

		if (onAceptar == null) {
			throw new IllegalArgumentException("onAceptar nulo");
		}

		if (titulo == null || titulo.isEmpty()) {
			titulo = cadenas.nuevoValor();
		}
		setText(titulo);

		Panel vpnContenido = new VerticalPanel();
		add(vpnContenido);

		txtInput = new TextBox();
		txtInput.setText(valorInicial);
		vpnContenido.add(txtInput);

		PanelBotones pnlBotones = new PanelBotones(new Command() {
			@Override
			public void execute() {
				String texto = txtInput.getText().trim();
				if (texto.isEmpty()) {
					txtInput.setText("");
					txtInput.setFocus(true);
				} else {
					onAceptar.execute(texto);
					hide();
				}

			}
		}, new Command() {
			@Override
			public void execute() {
				hide();
			}
		});
		vpnContenido.add(pnlBotones);

		txtInput.addKeyPressHandler(new IntroPulsaBotonKeyPressHandler(
				pnlBotones.getBtnAceptar()));

		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				txtInput.setFocus(true);
			}
		});
	}
}
