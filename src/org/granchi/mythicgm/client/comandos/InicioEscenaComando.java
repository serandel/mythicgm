package org.granchi.mythicgm.client.comandos;

import org.granchi.mythicgm.client.MythicGM;
import org.granchi.mythicgm.client.PanelComando;
import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.client.sucesos.EventoAleatorioView;
import org.granchi.mythicgm.modelo.PremisaEscena;
import org.granchi.mythicgm.modelo.PremisaEscena.CambioPremisa;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Inicio de una escena: escoger premisa.
 * 
 * Dependiendo del azar, valdrá con esto o habrá que alterarla.
 */
public class InicioEscenaComando extends PanelComando {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	private TextArea txtPremisa;

	public InicioEscenaComando(MythicGM mythicGM) {
		// Pasamos la escena que vamos a componer
		super(mythicGM, mythicGM.getPartida().crearEscena());
	}

	@Override
	protected void addComponentes() {
		// Para agregar el EventoAleatorioView si es necesario
		final VerticalPanel vpnSemilla = new VerticalPanel();

		final Anchor ancSemilla = new Anchor(cadenas.generarSemillaPremisa());
		ancSemilla.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				escena.getPremisa().generarSemillaPremisa();
				EventoAleatorioView eav = new EventoAleatorioView(escena
						.getPremisa().getSemillaPremisa());
				eav.addStyleName("mythicSubPanel");
				vpnSemilla.add(eav);
				vpnSemilla.setVisible(true);
				// No añadir dos veces
				ancSemilla.setVisible(false);
				// Vuelta el foco a la premisa
				txtPremisa.setFocus(true);
			}
		});
		add(ancSemilla);

		// Para que coja el tamaño del resto del componente
		vpnSemilla.setWidth("100%");
		vpnSemilla.setVisible(false);
		add(vpnSemilla);

		vpnSemilla.add(new HTML("<b>" + cadenas.semillaPremisa() + "</b>"));

		HTML h = new HTML("<b>" + cadenas.premisa() + "</b>");
		h.addStyleName("cabecera");
		add(h);

		txtPremisa = new TextArea();
		txtPremisa.getElement().setClassName("txtNotas");
		add(txtPremisa);
	}

	@Override
	protected boolean isValido() {
		return !txtPremisa.getText().trim().isEmpty();
	}

	@Override
	protected Resultado onValido() {
		PremisaEscena pe = escena.getPremisa();
		// Si vamos a necesitar más pasos o no
		PanelComando siguientePaso[] = null;

		// Esto hace que se tire si va a salir así o hay que alterarla
		pe.setPremisa(txtPremisa.getText().trim().replace("\n", "<br/>"));

		PremisaEscena.CambioPremisa cambio = pe.getCambioPremisa();

		if (cambio == null) {
			// Tal cual empezamos
			mythicGM.getPartida().addEscena(escena);
		} else if (cambio == CambioPremisa.ALTERADA) {
			siguientePaso = new PanelComando[] { new InicioEscenaAlteradaComando(
					mythicGM, escena) };
		} else if (cambio == CambioPremisa.INTERRUMPIDA) {
			siguientePaso = new PanelComando[] { new InicioEscenaInterrumpidaComando(
					mythicGM, escena) };
		}

		// Pedimos que se pase al paso dos
		return new Resultado((siguientePaso == null ? pe : null), null,
				siguientePaso, null);
	}

	@Override
	protected void onNoValido() {
		// No se ha metido premisa, obligarle
		txtPremisa.setText("");
		txtPremisa.setFocus(true);
	}

	@Override
	protected boolean isNecesarioRefrescarPartida() {
		// Refrescamos si no hay más pasos
		return escena.getPremisa().getCambioPremisa() == null;
	}

	@Override
	protected Focusable getComponentePorDefecto() {
		return txtPremisa;
	}
}
