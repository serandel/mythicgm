package org.granchi.mythicgm.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel para enganchar botones.
 * 
 * @author serandel
 */
public class PanelBotones extends FlowPanel {
	public PanelBotones() {
		setStyleName("panelBotones");
	}

	/**
	 * Habilita o deshabilita sus botones.
	 * 
	 * @param b
	 *            si ha de habilitar los botones
	 */
	public void setEnabled(boolean b) {
		for (int i = 0; i < getWidgetCount(); i++) {
			Widget w = getWidget(i);
			if (w instanceof FocusWidget) {
				((FocusWidget) w).setEnabled(b);
			}
		}
	}
}
