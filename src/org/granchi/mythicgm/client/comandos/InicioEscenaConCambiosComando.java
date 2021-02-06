package org.granchi.mythicgm.client.comandos;

import org.granchi.mythicgm.client.MythicGM;
import org.granchi.mythicgm.client.PanelComando;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.client.sucesos.EventoAleatorioView;
import org.granchi.mythicgm.modelo.Escena;
import org.granchi.mythicgm.modelo.PremisaEscena;
import org.granchi.mythicgm.modelo.Suceso;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Clase abstracta que sirve de base para InicioEscenaAlterada e
 * InicioEscenaInterrumpida, con las características comunes de ambas.
 */
public abstract class InicioEscenaConCambiosComando extends PanelComando {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	protected TextArea txtPremisaModificada;

	// Por si una subclase necesita meter sucesos
	protected Suceso sucesos[] = null;

	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 * @param escena
	 *            escena que se está construyendo
	 */
	InicioEscenaConCambiosComando(MythicGM mythicGM, Escena escena) {
		super(mythicGM, escena);
	}

	@Override
	protected final void addComponentes() {
		PremisaEscena pe = escena.getPremisa();

		// Lo defino arriba por si le tengo que poner estilo
		HTML htmlPremisa = new HTML("<b>" + cadenas.premisaOriginal()
				+ "</b><br/>" + pe.getPremisa());

		if (pe.getSemillaPremisa() != null) {
			add(new HTML("<b>" + cadenas.semillaPremisaOriginal() + "</b>"));
			add(new EventoAleatorioView(pe.getSemillaPremisa()));
			htmlPremisa.addStyleName("cabecera");
		}

		add(htmlPremisa);

		// Explicación de lo que ha pasado
		HTML htmlCaos = new HTML("<b>"
				+ cadenas.tiradaCaos()
				+ "</b> <i>("
				+ pe.getTiradaCaos()
				+ ", "
				+ (pe.getTiradaCaos() % 2 == 0 ? cadenas.par() : cadenas
						.impar()) + ")</i> <b><= " + cadenas.caosEscena()
				+ "</b> <i>(" + escena.getCaos() + ")</i>");
		htmlCaos.addStyleName("cabecera");
		add(htmlCaos);

		for (String s : pe.getExplicacion()) {
			add(new HTML(s, true));
		}

		addComponentesEspecificos();

		HTML htmlPremisaModificada = new HTML("<b>" + cadenas.premisa()
				+ "</b>");
		htmlPremisaModificada.addStyleName("cabecera");
		add(htmlPremisaModificada);

		txtPremisaModificada = new TextArea();
		txtPremisaModificada.getElement().setClassName("txtNotas");
		add(txtPremisaModificada);
	}

	/**
	 * Añade los componentes específicos de cada caso.
	 */
	protected abstract void addComponentesEspecificos();

	/**
	 * Es necesario refrescar la partida tras empezarla, sí. :)
	 */
	@Override
	protected boolean isNecesarioRefrescarPartida() {
		return true;
	}

	@Override
	protected boolean isValido() {
		return !txtPremisaModificada.getText().trim().isEmpty();
	}

	@Override
	protected Resultado onValido() {
		PremisaEscena pe = escena.getPremisa();

		pe.setPremisaModificada(txtPremisaModificada.getText().trim().replace(
				"\n", "<br/>"));
		mythicGM.getPartida().addEscena(escena);

		return new Resultado(pe, sucesos, null, null);
	}

	@Override
	protected void onNoValido() {
		// No se ha metido premisa, obligarle
		txtPremisaModificada.setText("");
		txtPremisaModificada.setFocus(true);
	}

	@Override
	protected Focusable getComponentePorDefecto() {
		return txtPremisaModificada;
	}
}
