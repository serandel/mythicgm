package org.granchi.mythicgm.client.sucesos;

import org.granchi.mythicgm.client.ComponenteEscenaView;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.modelo.EventoAleatorio;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;

/**
 * Panel que muestra un evento aleatorio.
 */
public class EventoAleatorioView extends ComponenteEscenaView<EventoAleatorio> {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	/**
	 * Constructor.
	 * 
	 * @param evento evento aleatorio
	 */
	public EventoAleatorioView(EventoAleatorio evento) {
		super(evento);
	}

	@Override
	protected void addComponentes() {
		addStyleName("eventoAleatorio");
		
		StringBuffer html = new StringBuffer();

		html.append("<b><i>");
		html.append(cadenas.eventoAleatorio());
		html.append("</i></b><br/><b>");
		html.append(cadenas.foco());
		html.append("</b><br/>");
		html.append("<i>(");
		html.append(componente.getTiradaFoco());
		html.append(")</i> ");
		html.append(EventoAleatorio.DESC_FOCO[componente.getFoco().ordinal()]);
		if (componente.getObjetoFoco() != null) {
			html.append(": ");
			html.append(componente.getObjetoFoco());
		}
		html.append("<br/><b>");
		html.append(cadenas.significado());
		html.append("</b><br/>");
		html.append("<i>(");
		html.append(componente.getTiradaAccion());
		html.append(")</i> ");
		html.append(componente.getAccion());
		html.append(" + <i>(");
		html.append(componente.getTiradaObjeto());
		html.append(")</i> ");
		html.append(componente.getObjeto());

		add(new HTML(html.toString()));
	}
}