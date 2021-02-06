package org.granchi.mythicgm.client.util;

import org.granchi.mythicgm.client.recursos.Cadenas;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Panel para enganchar botones.
 * 
 * @author serandel
 */
public class PanelBotones extends FlowPanel {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);
	
	private Anchor ancCancelar;
	private Button btnAceptar;

	/**
	 * Constructor.
	 * 
	 * @param onAceptar
	 *            comando a ejecutar si se pulsa aceptar
	 * @param onSalir
	 *            comando a ejecutar si se pulsa cancelar
	 */
	public PanelBotones(final Command onAceptar, final Command onCancelar) {
		this(null, onAceptar, onCancelar);
	}

	/**
	 * Constructor.
	 * 
	 * @param verbo
	 *            verbo para el botón aceptar
	 * @param onAceptar
	 *            comando a ejecutar si se pulsa aceptar, null para ninguno
	 * @param onCancelar
	 *            comando a ejecutar si se pulsa cancelar, null para ninguno
	 */
	public PanelBotones(String verbo, final Command onAceptar,
			final Command onCancelar) {
		setStyleName("panelBotones");

		if (verbo == null || verbo.isEmpty()) {
			verbo = cadenas.aceptar();
		}

		ancCancelar = new Anchor(cadenas.cancelar());
		ancCancelar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (onCancelar != null) {
					onCancelar.execute();
				}
			}
		});
		add(ancCancelar);

		btnAceptar = new Button(verbo);
		btnAceptar.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (onAceptar != null) {
					onAceptar.execute();
				}
			}
		});
		add(btnAceptar);
	}

	// TODO si el click estuviera en un interfaz, montaría esto para que el
	// click estuviese aquí y delegase al botón
	// TODO qué mierda he querido decir aquí? xD
	public Button getBtnAceptar() {
		return btnAceptar;
	}

	/**
	 * Pone el panel en modo "acepta o nada".
	 */
	public void setSoloAceptar() {
		btnAceptar.setFocus(true);
		ancCancelar.setVisible(false);
	}

	// TODO al final no lo estoy usando
	// /**
	// * Habilita o deshabilita sus botones.
	// *
	// * @param b
	// * si ha de habilitar los botones
	// */
	// public void setEnabled(boolean b) {
	// for (int i = 0; i < getWidgetCount(); i++) {
	// Widget w = getWidget(i);
	// if (w instanceof FocusWidget) {
	// ((FocusWidget) w).setEnabled(b);
	// }
	// }
	// }
}
