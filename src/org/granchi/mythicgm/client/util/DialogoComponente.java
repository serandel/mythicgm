package org.granchi.mythicgm.client.util;

import org.granchi.mythicgm.client.recursos.Cadenas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Diálogo que muestra un componente y una caja con cancelar y aceptar.
 */
public class DialogoComponente<T extends Widget> extends DialogBox {
	private T componente;
	
	private static final Cadenas cadenas = GWT.create(Cadenas.class);
	
	/**
	 * Constructor.
	 * 
	 * @param titulo
	 *            titulo
	 * @param componente
	 *            componente
	 * @param onAceptar
	 *            comando a ejecutar si se acepta, null para ninguno
	 */
	public DialogoComponente(String titulo, final T componente,
			final Command onAceptar) {
		// Modal sin autohide
		super(false, true);

		addStyleName("dialogoComponente");

		if (componente == null) {
			throw new IllegalArgumentException("Componente nulo");
		}
		this.componente = componente;

		if (titulo == null || titulo.isEmpty()) {
			titulo = cadenas.mythicGM();
		}
		setText(titulo);

		VerticalPanel vpnContenido = new VerticalPanel();
		add(vpnContenido);
		
		vpnContenido.add(componente);

		PanelBotones pnlBotones = new PanelBotones(new Command() {
			@Override
			public void execute() {
				if (onAceptar != null) {
					onAceptar.execute();
				}
				hide();
			}
		}, null);
		pnlBotones.setSoloAceptar();
		vpnContenido.add(pnlBotones);

		// TODO intro acepta y foco por defecto
	}
	
	/**
	 * Devuelve el componente mostrado.
	 * 
	 * @return componente
	 */
	public T getComponente() {
		return componente;
	}
}
