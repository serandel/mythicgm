package org.granchi.mythicgm.client;

import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.client.util.DialogoInput;
import org.granchi.mythicgm.client.util.ParametrizedCommand;
import org.granchi.mythicgm.modelo.PJ;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Componente vista de un PJ.
 */
// TODO html
public class PJView extends Composite {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	private Anchor ancNombre, ancFicha, ancCambiarFicha;
	private HTML htmlSepFicha;
	private CaptionPanel capPnjs, capMisiones;
	private TablaAnadirBorrar tblPnjs, tblMisiones;

	// Para poder detectar si hemos cambiado el nombre del pj
	private boolean mismoNombre = true;

	/**
	 * Constructor sin personaje, que le será añadido más tarde.
	 */
	public PJView() {
		// Aquí es donde lo engancharemos todo
		VerticalPanel panel = new VerticalPanel();
		// Hay que inicializar el widget
		initWidget(panel);

		addStyleName("infoPj");

		panel.add(new HTML("<b>" + cadenas.nombre() + "</b>", true));

		ancNombre = new Anchor();
		// Para que no se pulse mientras no haya PJ
		ancNombre.setEnabled(false);
		panel.add(ancNombre);

		HTML cabFicha = new HTML("<b>" + cadenas.ficha() + "</b>");
		cabFicha.addStyleName("cabecera");
		panel.add(cabFicha);

		HorizontalPanel hpnFicha = new HorizontalPanel();
		hpnFicha.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		panel.add(hpnFicha);

		ancFicha = new Anchor();
		ancFicha.setTarget("_blank");
		hpnFicha.add(ancFicha);

		htmlSepFicha = new HTML("&nbsp;");
		hpnFicha.add(htmlSepFicha);

		ancCambiarFicha = new Anchor();
		// Para que no se pulse mientras no haya PJ
		ancCambiarFicha.setEnabled(false);
		hpnFicha.add(ancCambiarFicha);

		capPnjs = new CaptionPanel(cadenas.pnjs());
		capPnjs.addStyleName("cabecera");
		panel.add(capPnjs);

		tblPnjs = new TablaAnadirBorrar();
		capPnjs.add(tblPnjs);

		capMisiones = new CaptionPanel(cadenas.misiones());
		capMisiones.addStyleName("cabecera");
		panel.add(capMisiones);

		tblMisiones = new TablaAnadirBorrar();
		capMisiones.add(tblMisiones);
	}

	/**
	 * Constructor con personaje.
	 * 
	 * @param pj
	 *            PJ
	 */
	public PJView(PJ pj) {
		this();
		mostrar(pj, false);
	}

	/**
	 * Muestra al PJ.
	 * 
	 * @param pj
	 *            pj
	 * @param editar
	 *            si deja editar PNJs y misiones o sólo nombre y ficha
	 */
	public void mostrar(final PJ pj, final boolean editar) {
		if (pj == null) {
			throw new IllegalArgumentException("PJ nulo");
		}

		mismoNombre = true;

		ancNombre.setText(pj.getNombre());
		ancNombre.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DialogoInput dlgInput = new DialogoInput(cadenas.nombrePj(), pj
						.getNombre(), new ParametrizedCommand<String>() {
					@Override
					public void execute(String nombre) {
						nombre = nombre.trim();

						if (!pj.getNombre().equals(nombre)) {
							mismoNombre = false;
							pj.setNombre(nombre);
							ancNombre.setText(pj.getNombre());
						}
					}
				});

				dlgInput.setPopupPosition(ancNombre.getAbsoluteLeft() + 40,
						ancNombre.getAbsoluteTop() + 10);
				dlgInput.show();
			}
		});
		ancNombre.setEnabled(true);

		String url = pj.getUrl();
		if (url != null && !url.isEmpty()) {
			ancFicha.setVisible(true);
			ancFicha.setText(url);
			ancFicha.setHref(URL.encode(url));
			htmlSepFicha.setVisible(true);
			ancCambiarFicha.setText(cadenas.cambiar());
		} else {
			ancFicha.setVisible(false);
			htmlSepFicha.setVisible(false);
			ancCambiarFicha.setText(cadenas.ninguna());
		}
		ancCambiarFicha.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DialogoInput dlgInput = new DialogoInput(cadenas.urlFicha(), pj
						.getUrl(), new ParametrizedCommand<String>() {
					@Override
					public void execute(String url) {
						pj.setUrl(url);
						ancFicha.setText(url);
						ancFicha.setHref(URL.encode(url));
					}
				});
				dlgInput.setPopupPosition(ancFicha.getAbsoluteLeft() + 40,
						ancFicha.getAbsoluteTop() + 10);
				dlgInput.show();
			}
		});
		ancCambiarFicha.setEnabled(true);

		capPnjs.setVisible(editar || !pj.getPnjs().isEmpty());
		if (capPnjs.isVisible()) {
			tblPnjs.rellenar(pj.getPnjs(), editar, cadenas.pnj());
		}

		capMisiones.setVisible(editar || !pj.getObjetivos().isEmpty());
		if (capMisiones.isVisible()) {
			tblMisiones.rellenar(pj.getObjetivos(), editar, cadenas.pnj());
		}
	}

	/**
	 * Devuelve si se ha mantenido el nombre del PJ desde que se empezó a
	 * mostrar.
	 * 
	 * @return si el nombre es el mismo
	 */
	public boolean isMismoNombre() {
		return mismoNombre;
	}
}
