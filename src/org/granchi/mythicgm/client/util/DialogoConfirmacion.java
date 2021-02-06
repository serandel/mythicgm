package org.granchi.mythicgm.client.util;

import org.granchi.mythicgm.client.recursos.Cadenas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Diálogo que pide confirmación para hacer una cosa.
 */
// TODO esto está pidiendo constante con parámetros, en vez de la cutrez que
// monto
public class DialogoConfirmacion extends DialogBox {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	/**
	 * Constructor.
	 * 
	 * @param pregunta
	 *            titulo
	 * @param onAceptar
	 *            comando a ejecutar si se acepta
	 */
	public DialogoConfirmacion(String pregunta, final Command onAceptar) {
		this(pregunta, cadenas.aceptar(), onAceptar);
	}

	/**
	 * Constructor.
	 * 
	 * @param pregunta
	 *            titulo
	 * @param textoAceptar
	 *            texto del botón de aceptar
	 * @param onAceptar
	 *            comando a ejecutar si se acepta
	 */
	public DialogoConfirmacion(String pregunta, String textoAceptar,
			final Command onAceptar) {
		// Modal sin autohide
		super(false, true);

		addStyleName("dialogoConfirmacion");

		if (onAceptar == null) {
			throw new IllegalArgumentException("onAceptar nulo");
		}

		setText(cadenas.seguro());

		if (pregunta == null || pregunta.isEmpty()) {
			pregunta = cadenas.seguro();
		}

		Panel vpnContenido = new VerticalPanel();
		add(vpnContenido);

		vpnContenido.add(new HTML(pregunta));

		final PanelBotones pnlBotones = new PanelBotones(new Command() {
			@Override
			public void execute() {
				onAceptar.execute();
				hide();
			}
		}, new Command() {
			@Override
			public void execute() {
				hide();
			}
		});
		vpnContenido.add(pnlBotones);

		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				pnlBotones.getBtnAceptar().setFocus(true);
			}
		});
	}
}
