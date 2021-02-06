package org.granchi.mythicgm.modelo;

import org.granchi.mythicgm.client.recursos.CadenasModelo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;

/**
 * Una pregunta de sí o no.
 */
public class PreguntaSiNo extends Suceso {
	private static final CadenasModelo cadenas = GWT
	.create(CadenasModelo.class);
	
	public static final String PROBABILIDAD[] = cadenas.descProbabilidad();

	/**
	 * Probabilidades de crítico, éxito y pifia indexadas por caos y
	 * probabilidad
	 */
	private static final int DESTINO[][][] = {
			{ { 0, -20, 77 }, { 0, 0, 81 }, { 1, 5, 82 }, { 1, 5, 82 },
					{ 2, 10, 83 }, { 4, 20, 85 }, { 5, 25, 86 }, { 9, 45, 90 },
					{ 10, 50, 91 }, { 11, 55, 92 }, { 16, 80, 97 } },
			{ { 0, 0, 81 }, { 1, 5, 82 }, { 1, 5, 82 }, { 2, 10, 83 },
					{ 3, 15, 84 }, { 5, 25, 86 }, { 7, 35, 88 },
					{ 10, 50, 91 }, { 11, 55, 92 }, { 13, 65, 94 },
					{ 16, 85, 97 } },
			{ { 0, 0, 81 }, { 1, 5, 82 }, { 2, 10, 83 }, { 3, 15, 84 },
					{ 5, 25, 86 }, { 9, 45, 90 }, { 10, 50, 91 },
					{ 13, 65, 94 }, { 15, 75, 96 }, { 16, 80, 97 },
					{ 18, 90, 99 } },
			{ { 1, 5, 82 }, { 2, 10, 83 }, { 3, 15, 84 }, { 4, 20, 85 },
					{ 7, 35, 88 }, { 10, 50, 91 }, { 11, 55, 92 },
					{ 15, 75, 96 }, { 16, 80, 97 }, { 16, 85, 97 },
					{ 19, 95, 100 } },
			{ { 1, 5, 82 }, { 3, 15, 84 }, { 5, 25, 86 }, { 7, 35, 88 },
					{ 10, 50, 91 }, { 13, 65, 94 }, { 15, 75, 96 },
					{ 16, 85, 97 }, { 18, 90, 99 }, { 18, 90, 99 },
					{ 19, 95, 100 } },
			{ { 2, 10, 83 }, { 5, 25, 86 }, { 9, 45, 90 }, { 10, 50, 91 },
					{ 13, 65, 94 }, { 16, 80, 97 }, { 16, 85, 97 },
					{ 18, 90, 99 }, { 19, 95, 100 }, { 19, 95, 100 },
					{ 20, 100, 0 } },
			{ { 3, 15, 84 }, { 7, 35, 88 }, { 10, 50, 91 }, { 11, 55, 92 },
					{ 15, 75, 96 }, { 16, 85, 97 }, { 18, 90, 99 },
					{ 19, 95, 100 }, { 19, 95, 100 }, { 19, 95, 100 },
					{ 29, 100, 0 } },
			{ { 5, 25, 86 }, { 10, 50, 91 }, { 13, 65, 94 }, { 15, 75, 96 },
					{ 16, 85, 97 }, { 18, 90, 99 }, { 19, 95, 100 },
					{ 19, 95, 100 }, { 20, 100, 0 }, { 22, 110, 0 },
					{ 26, 130, 0 } },
			{ { 10, 50, 91 }, { 15, 75, 96 }, { 16, 85, 97 }, { 18, 90, 99 },
					{ 19, 95, 100 }, { 19, 95, 100 }, { 20, 100, 0 },
					{ 21, 105, 0 }, { 23, 105, 0 }, { 25, 125, 0 },
					{ 26, 145, 0 } } };

	private String pregunta, respuesta;
	private int probabilidad, tirada;
	private boolean generaEventoAleatorio;

	/**
	 * Constructor.
	 * 
	 * Se resuelve del tirón
	 * 
	 * @param escena
	 *            escena
	 */
	public PreguntaSiNo(Escena escena, String pregunta, int probabilidad) {
		super(escena);

		int caos = escena.getCaos();
		// El caos empieza en 1
		if (caos <= 0 || caos > DESTINO.length) {
			throw new IllegalArgumentException("Caos inválido: " + caos);
		}

		this.pregunta = pregunta;

		this.probabilidad = probabilidad;
		if (probabilidad < 0 || probabilidad >= PROBABILIDAD.length) {
			throw new IllegalArgumentException("Probabilidad inválida: "
					+ probabilidad);
		}

		tirada = Random.nextInt(100) + 1;

		int probabilidades[] = DESTINO[caos - 1][probabilidad];
		if (tirada <= probabilidades[0]) {
			// Crítico
			respuesta = "S&iacute; excepcional";
		} else if (tirada <= probabilidades[1]) {
			// Sí
			respuesta = "S&iacute;";
		} else if (tirada < probabilidades[2]) {
			// No
			respuesta = "No";
		} else {
			// Pifia
			respuesta = "No excepcional";
		}

		// Posible evento aleatorio si hay dobles
		generaEventoAleatorio = ((tirada / 10 == tirada % 10) && (tirada % 10 <= caos));
	}

	public String getPregunta() {
		return pregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public int getTirada() {
		return tirada;
	}

	public boolean getGeneraEventoAleatorio() {
		return generaEventoAleatorio;
	}

	public int getProbabilidad() {
		return probabilidad;
	}
}
