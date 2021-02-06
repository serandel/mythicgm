package org.granchi.mythicgm.client;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Panel con un evento aleatorio.
 */
// TODO caja de texto con notas y botón de aceptar y de rechazar
public class EventoAleatorio extends VerticalPanel {

	// 100 (he cambiado algunas por sinónimos en castellano o repeticiones en el
	// original)
	private static final String[] accion = { "Logro", "Comenzar", "Descuidar",
			"Luchar", "Reclutar", "Triunfo", "Profanar", "Oposici&oacute;n",
			"Malicia", "Comunicar", "Perseguir", "Incrementar", "Decrementar",
			"Abandonar", "Gratificar", "Indagar", "Enemistar", "Mover",
			"Desechar", "Tregua", "Liberar", "Hacer amigos", "Juzgar",
			"Desertar", "Dominar", "Procrastinar", "Alabar", "Separar",
			"Tomar", "Romper", "Sanar", "Retrasar", "Parar", "Mentir",
			"Retornar", "Imitar", "Forcejear", "Informar", "Conferir",
			"Posponer", "Exponer", "Regatear", "Aprisionar", "Degradar",
			"Celebrar", "Desarrollar", "Viajar", "Bloquear", "Da&ntilde;ar",
			"Corromper", "Consentir", "Levantar la sesi&oacute;n",
			"Adversidad", "Matar", "Disrumpir", "Usurpar", "Crear",
			"Traicionar", "Estar de acuerdo", "Abusar", "Oprimir",
			"Inspeccionar", "Emboscar", "Espiar", "Sujetar", "Llevar", "Abrir",
			"Falta de cuidado", "Arruinar", "Extravagancia", "Enga&ntilde;ar",
			"Llegar", "Proponer", "Dividir", "Rehusar", "Desconfiar",
			"Adjuntar", "Crueldad", "Intolerancia", "Confiar",
			"Excitaci&oacute;n", "Actividad", "Ayudar", "Cuidar",
			"Negligencia", "Pasi&oacute;n", "Trabajar duro", "Controlar",
			"Atraer", "Fallo", "Perseguir", "Venganza", "Procedimientos",
			"Disputar", "Castigar", "Guiar", "Transformar", "Derrocar",
			"Competir", "Cambiar" };

	private static final String[] sujeto = { "Metas", "Sue&ntilde;os",
			"Entorno", "Fuera", "Dentro", "Realidad", "Aliados", "Enemigos",
			"El Mal", "El Bien", "Emociones", "Oposici&oacute;n", "Guerra",
			"Paz", "Los inocentes", "Amor", "Lo espiritual", "Lo intelectual",
			"Nuevas ideas", "Alegr&iacute;a", "Mensajes", "Energ&iacute;a",
			"Equilibrio", "Tensi&oacute;n", "Amistad", "Lo f&iacute;sico",
			"Un proyecto", "Placeres", "Dolor", "Posesiones", "Beneficios",
			"Planes", "Mentiras", "Expectativas", "Asuntos legales",
			"Burocracia", "Negocios", "Un camino", "Noticias",
			"Factores externos", "Consejo", "Una conspiraci&oacute;n",
			"Competencia/competici&oacute;n", "Prisi&oacute;n", "Enfermedad",
			"Comida", "Atenci&oacute;n", "&Eacute;xito", "Fracaso", "Viaje",
			"Celos", "Disputa", "Hogar", "Inversi&oacute;n", "Sufrimiento",
			"Deseos", "T&aacute;cticas", "Punto muerto", "Azar", "Infortunio",
			"Muerte", "Disrupci&oacute;n", "Poder", "Una carga", "Intrigas",
			"Miedos", "Emboscada", "Rumor", "Heridas", "Extravagancia",
			"Un representante", "Adversidades", "Opulencia", "Libertad",
			"Ej&eacute;rcito", "Lo mundano", "Pruebas/juicios", "Masas",
			"Veh&iacute;culo", "Arte", "Victoria", "Disputa", "Riquezas",
			"Status quo", "Tecnolog&iacute;a", "Esperanza", "Magia",
			"Ilusiones", "Portales", "Peligro", "Armas", "Animales", "Clima",
			"Elementos", "Naturaleza", "El p&uacute;blico", "Liderazgo",
			"Fama", "Ira", "Informaci&oacute;n" };

	private enum Foco {
		EVENTO_REMOTO, ACCION_PNJ, INTRODUCIR_PNJ, AVANZAR_OBJETIVO, ALEJARSE_OBJETIVO, CERRAR_OBJETIVO, NEGATIVO_PJ, POSITIVO_PJ, EVENTO_AMBIGUO, NEGATIVO_PNJ, POSITIVO_PNJ
	};

	// Indexado por el índice del enum
	private static final String DESC_FOCO[] = { "Evento remoto",
			"Acci&oacute;n de un PNJ", "Introducir un nuevo PNJ",
			"Avanzar hacia un objetivo", "Alejarse de un objetivo",
			"Cerrar un objetivo", "Negativo para un PJ", "Positivo para un PJ",
			"Evento ambiguo", "Negativo para un PNJ", "Positivo para un PNJ" };

	// Distribución de probabilidad de los focos
	// Va del mayor número del rango al resultado
	private static final SortedMap<Integer, Foco> distribFocos;

	private Foco foco;
	// Objetivo, PNJ, etc.
	private String aux;
	// Si este evento va a hacer que se refresque la información de la partida
	private boolean refrescarInfo = false;

	/**
	 * Inicializamos las cosas
	 */
	static {
		distribFocos = new TreeMap<>();

		distribFocos.put(7, Foco.EVENTO_REMOTO);
		distribFocos.put(28, Foco.ACCION_PNJ);
		distribFocos.put(35, Foco.INTRODUCIR_PNJ);
		distribFocos.put(45, Foco.AVANZAR_OBJETIVO);
		distribFocos.put(52, Foco.ALEJARSE_OBJETIVO);
		distribFocos.put(55, Foco.CERRAR_OBJETIVO);
		distribFocos.put(67, Foco.NEGATIVO_PJ);
		distribFocos.put(75, Foco.POSITIVO_PJ);
		distribFocos.put(83, Foco.EVENTO_AMBIGUO);
		distribFocos.put(92, Foco.NEGATIVO_PNJ);
		distribFocos.put(100, Foco.POSITIVO_PNJ);

		GWT.log("EventoAleatorio: cargados " + accion.length + " acciones y "
				+ sujeto.length + " sujetos", null);
	}

	/**
	 * Constructor.
	 * 
	 * @param mythicGM
	 *            ventana principal
	 */
	public EventoAleatorio(final MythicGM mythicGM, final Partida partida) {
		getElement().setClassName("eventoAleatorio");
		StringBuffer html = new StringBuffer();

		add(new HTML("<b><i>Evento aleatorio</i></b><br/><b>Foco</b><br/>"));

		// Foco
		int tirada = 0;

		// Evitar que se hable de PNJS si todavía no hay, por ejemplo
		while (foco == null) {
			tirada = Random.nextInt(100) + 1;

			int cuenta = 0;

			for (Map.Entry<Integer, Foco> e : distribFocos.entrySet()) {
				cuenta = e.getKey();
				if (cuenta >= tirada) {
					foco = e.getValue();
					break;
				}
			}

			if (foco == Foco.NEGATIVO_PJ || foco == Foco.POSITIVO_PJ) {
				// Elegir PJ existente

				Map<String, PJ> pjs = partida.getPjs();
				aux = ((PJ) pjs.values().toArray()[Random
						.nextInt(pjs.size())]).getNombre();
			} else if (foco == Foco.ACCION_PNJ || foco == Foco.NEGATIVO_PNJ
					|| foco == Foco.POSITIVO_PNJ) {
				// Elegir PNJ existente

				SortedSet<String> pnjs = partida.getPnjs();
				if (pnjs.isEmpty()) {
					foco = null;
				} else {
					aux = (String) pnjs.toArray()[Random.nextInt(pnjs.size())];
				}
			} else if (foco == Foco.ALEJARSE_OBJETIVO
					|| foco == Foco.AVANZAR_OBJETIVO
					|| foco == Foco.CERRAR_OBJETIVO) {
				List<String> objetivos = partida.getObjetivos();
				if (objetivos.isEmpty()) {
					foco = null;
				} else {
					aux = objetivos.get(Random.nextInt(objetivos.size()));
				}

				refrescarInfo = (foco == Foco.CERRAR_OBJETIVO);
			}

			// TODO habrá siempre una misión y pjs?
		}

		html.append("<i>(");
		html.append(tirada);
		html.append(")</i> ");
		html.append(DESC_FOCO[foco.ordinal()]);
		if (aux != null) {
			html.append(": ");
			html.append(aux);
		}
		// Le pongo nombre para poder alterarlo luego
		final HTML htmlFoco = new HTML(html.toString());
		add(htmlFoco);

		final TextBox txtAux = new TextBox();
		if (foco == Foco.INTRODUCIR_PNJ) {
			refrescarInfo = true;

			add(txtAux);
		}

		// Significado
		html = new StringBuffer();
		html.append("<b>Significado</b><br/>");
		tirada = Random.nextInt(accion.length);
		html.append("<i>(");
		html.append(tirada + 1);
		html.append(")</i> ");
		html.append(accion[tirada]);

		tirada = Random.nextInt(accion.length);
		html.append(" + <i>(");
		html.append(tirada + 1);
		html.append(")</i> ");
		html.append(sujeto[tirada]);

		add(new HTML(html.toString()));

		final PanelBotones pnlBotones = new PanelBotones();
		add(pnlBotones);

		Anchor ancCancelar = new Anchor("Cancelar");
		ancCancelar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				removeFromParent();
				mythicGM.onHijoTermina(EventoAleatorio.this, false);
			}
		});
		pnlBotones.add(ancCancelar);

		final Button btnAceptar = new Button("Aceptar");
		btnAceptar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				boolean valido = true;

				// ¿Hacía falta introducir algo?
				if (txtAux.isAttached()) {
					aux = txtAux.getText().trim();
					if (valido = (!aux.isEmpty())
							&& (partida.getPnjs().add(aux))) {
						remove(txtAux);
						htmlFoco.setHTML(htmlFoco.getHTML() + ": " + aux);
					} else {
						txtAux.setFocus(true);
					}
				}

				// ¿Se cerró un objetivo?
				if (foco == Foco.CERRAR_OBJETIVO) {
					partida.getObjetivos().remove(aux);
				}

				if (valido) {
					remove(pnlBotones);
					mythicGM.onHijoTermina(EventoAleatorio.this, refrescarInfo);
				}
			}
		});
		pnlBotones.add(btnAceptar);

		// Sin el deferred no iría bien, tiene que añadirse antes
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				if (txtAux.isAttached()) {
					txtAux.setFocus(true);
					txtAux.addKeyPressHandler(new KeyPressHandler() {

						@Override
						public void onKeyPress(KeyPressEvent event) {
							int tecla = event.getCharCode();
							if (tecla == KeyCodes.KEY_ENTER) {
								btnAceptar.click();
							}

							// No puedo hacer lo mismo con el Anchor y un ESC,
							// porque Anchor
							// no tiene click

							// Si no, se lo lleva luego el botón al que le
							// hacemos el focus
							event.stopPropagation();

						}
					});
				} else {
					btnAceptar.setFocus(true);
				}
			}
		});
	}
}
