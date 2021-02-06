package org.granchi.mythicgm.modelo;

import org.granchi.mythicgm.client.recursos.CadenasModelo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;

/**
 * El inicio de una escena.
 * 
 * Puede tener la premisa aleatoria o dada por los jugadores, en cuyo caso se
 * puede alterar (y se permite una pregunta si/no de aclaración) o interrumpir
 * por un evento aleatorio.
 * 
 * No extiende de Suceso porque no se puede añadir en mitad de una escena.
 */
public class PremisaEscena extends ComponenteEscena {
	private static final CadenasModelo cadenas = GWT
			.create(CadenasModelo.class);

	public enum CambioPremisa {
		ALTERADA, INTERRUMPIDA
	}

	private static final String[] EXPLICACION_ALTERADA = {
			"<i><b>" + cadenas.premisaAlterada() + "</b></i>",
			"<i>" + cadenas.explPremisaAlterada() + "</i>" };

	private static final String[] EXPLICACION_INTERRUMPIDA = {
			"<i><b>" + cadenas.premisaInterrumpida() + "</b></i>",
			"<i>" + cadenas.explPremisaInterrumpida() + "</i>" };

	private String premisa, premisaModificada;
	private int tiradaCaos;
	private CambioPremisa cambioPremisa;
	private PreguntaSiNo aclaracionAlteracion;
	private EventoAleatorio semillaPremisa, causaInterrupcion;

	/**
	 * Constructor.
	 * 
	 * @param escena
	 *            escena
	 */
	PremisaEscena(Escena escena) {
		super(escena);
	}

	/**
	 * Genera un evento aleatorio como semilla de la premisa.
	 * 
	 * Se permite repetir hasta que se decida establecer una premisa.
	 */
	public void generarSemillaPremisa() {
		if (premisa != null) {
			throw new IllegalStateException("La escena ya contiene una premisa");
		}

		if (!escena.isPrimeraEscena()) {
			throw new IllegalStateException(
					"Sólo se puede generar una semilla para la premisa en la primera escena");
		}

		semillaPremisa = new EventoAleatorio(escena);
	}

	public String getPremisa() {
		return premisa;
	}

	/**
	 * Escribe la premisa y pone en marcha el mecanismo aleatorio de
	 * interrupción.
	 * 
	 * @param premisa
	 *            premisa
	 */
	public void setPremisa(String premisa) {
		if (premisa == null || premisa.isEmpty()) {
			throw new IllegalArgumentException("Premisa nula o vacía");
		}
		this.premisa = premisa;

		// Considero que las escenas generadas aleatoriamente ya son bastante
		// aleatorias
		if (semillaPremisa == null) {
			tiradaCaos = Random.nextInt(10) + 1;

			if (tiradaCaos > escena.getCaos()) {
				if (tiradaCaos % 2 == 0) {
					// Interrumpida
					cambioPremisa = CambioPremisa.INTERRUMPIDA;
					causaInterrupcion = new EventoAleatorio(escena);
				} else {
					// Alterada
					cambioPremisa = CambioPremisa.ALTERADA;
				}
			}
		}
	}

	public String getPremisaModificada() {
		return premisaModificada;
	}

	public void setPremisaModificada(String premisaModificada) {
		if (premisaModificada == null || premisaModificada.isEmpty()) {
			throw new IllegalArgumentException(
					"Premisa modificada nula o vacía");
		}

		this.premisaModificada = premisaModificada;
	}

	public int getTiradaCaos() {
		return tiradaCaos;
	}

	public CambioPremisa getCambioPremisa() {
		return cambioPremisa;
	}

	public PreguntaSiNo getAclaracionAlteracion() {
		return aclaracionAlteracion;
	}

	/**
	 * Hace una pregunta de sí o no que aclare cómo se ha podido alterar la
	 * premisa.
	 * 
	 * @param pregunta
	 *            pregunta
	 * @param probabilidad
	 *            índice de la probabilidad dentro de PreguntaSiNo.PROBABILIDAD
	 * 
	 * @return pregunta resuelta
	 */
	public PreguntaSiNo preguntarAclaracionAlteracion(String pregunta,
			int probabilidad) {
		if (cambioPremisa != CambioPremisa.ALTERADA) {
			throw new IllegalStateException("La premisa no ha sido alterada");
		}

		if (aclaracionAlteracion != null) {
			throw new IllegalStateException(
					"Ya se ha pedido una aclaración de la alteración");
		}

		aclaracionAlteracion = new PreguntaSiNo(escena, pregunta, probabilidad);

		return aclaracionAlteracion;
	}

	public EventoAleatorio getSemillaPremisa() {
		return semillaPremisa;
	}

	public EventoAleatorio getCausaInterrupcion() {
		return causaInterrupcion;
	}

	/**
	 * Devuelve la explicación de lo que ha cambiado la premisa, para mostrarla
	 * al usuario.
	 * 
	 * @return lista de cadenas, formateadas con HTML, null si no ha habido
	 *         cambios
	 */
	public String[] getExplicacion() {
		String res[] = null;

		if (cambioPremisa == CambioPremisa.ALTERADA) {
			res = EXPLICACION_ALTERADA;
		} else if (cambioPremisa == CambioPremisa.INTERRUMPIDA) {
			res = EXPLICACION_INTERRUMPIDA;
		}

		return res;
	}
}
