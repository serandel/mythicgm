package org.granchi.mythicgm.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Información sobre la partida.
 */
public class InfoPartida extends VerticalPanel {

	private Partida partida;

	private HTML htmlCaos, htmlPjs, htmlPnjs, htmlObjetivos;
	private ListBox lstPjs, lstPnjs, lstObjetivos; 

	/**
	 * Constructor.
	 * 
	 * @param partida
	 *            partida
	 */
	public InfoPartida(Partida partida) {
		if (partida == null) {
			throw new IllegalArgumentException("Partida nula");
		}
		
		getElement().setId("infoPartida");
		
		this.partida = partida;

		htmlCaos = new HTML("");
		add(htmlCaos);

		htmlPjs = new HTML("<b>PJs</b>");
		add(htmlPjs);
		lstPjs = new ListBox(true);
		add(lstPjs);
		
		htmlPnjs = new HTML("<b>PNJs</b>");
		add(htmlPnjs);
		lstPnjs = new ListBox(true);
		add(lstPnjs);

		htmlObjetivos = new HTML("<b>Objetivos</b>");
		add(htmlObjetivos);
		lstObjetivos = new ListBox(true);
		add(lstObjetivos);
		
		refrescar();
	}

	/**
	 * Refresca los controles.
	 */
	public void refrescar() {
		htmlCaos.setHTML("<b>Caos: </b>" + partida.getCaos());

		lstPjs.clear();
		for (PJ p : partida.getPjs().values()) {
			lstPjs.addItem(p.getNombre());
		}
		htmlPjs.setVisible(!partida.getPjs().isEmpty());
		lstPjs.setVisible(!partida.getPjs().isEmpty());
		if (lstPjs.isVisible()) {
			lstPjs.setVisibleItemCount(lstPjs.getItemCount());
		}
		
		lstPnjs.clear();
		for (String p : partida.getPnjs()) {
			lstPnjs.addItem(p);
		}
		htmlPnjs.setVisible(!partida.getPnjs().isEmpty());
		lstPnjs.setVisible(!partida.getPnjs().isEmpty());
		if (lstPnjs.isVisible()) {
			lstPnjs.setVisibleItemCount(lstPnjs.getItemCount());
		}
		
		lstObjetivos.clear();
		for (String o: partida.getObjetivos()) {
			lstObjetivos.addItem(o);
		}
		htmlObjetivos.setVisible(!partida.getObjetivos().isEmpty());
		lstObjetivos.setVisible(!partida.getObjetivos().isEmpty());
		if (lstObjetivos.isVisible()) {
			lstObjetivos.setVisibleItemCount(lstObjetivos.getItemCount());
		}
	}

}
