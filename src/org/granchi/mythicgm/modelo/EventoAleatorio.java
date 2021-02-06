package org.granchi.mythicgm.modelo;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.granchi.mythicgm.client.recursos.CadenasModelo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;

/**
 * Evento aleatorio.
 */
public class EventoAleatorio extends Suceso {
	private static final CadenasModelo cadenas = GWT
			.create(CadenasModelo.class);

	// Hay 100 de cada una
	public static final String ACCION[] = cadenas.accion();
	public static final String OBJETO[] = cadenas.objeto();

	public enum Foco {
		EVENTO_REMOTO, ACCION_PNJ, INTRODUCIR_PNJ, AVANZAR_OBJETIVO, ALEJARSE_OBJETIVO, CERRAR_OBJETIVO, NEGATIVO_PJ, POSITIVO_PJ, EVENTO_AMBIGUO, NEGATIVO_PNJ, POSITIVO_PNJ
	};

	// Indexado por el índice del enum
	public static final String DESC_FOCO[] = cadenas.descFoco();

	// Distribución de probabilidad de los focos
	// Va del mayor número del rango al resultado
	private static final SortedMap<Integer, Foco> distribFocos;

	private Foco foco;
	// Objeto del foco es un objetivo, PNJ, etc.
	private String objetoFoco, accion, objeto;
	private int tiradaFoco, tiradaAccion, tiradaObjeto;

	/**
	 * Inicializamos las cosas
	 */
	static {
		distribFocos = new TreeMap<Integer, Foco>();

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

		GWT.log("EventoAleatorio: cargados " + ACCION.length + " acciones y "
				+ OBJETO.length + " sujetos", null);
	}

	/**
	 * Constructor.
	 * 
	 * Resuelve el evento del tirón.
	 * 
	 * @param escena
	 *            escena
	 */
	public EventoAleatorio(Escena escena) {
		super(escena);

		// Foco
		int tirada = 0;
		// Esto es para evitar que se hable de PNJS si todavía no hay, por
		// ejemplo
		while (foco == null) {
			tiradaFoco = Random.nextInt(100) + 1;

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
				objetoFoco = getElementoAleatorio(escena.getPjs()).getNombre();
			} else if (foco == Foco.ACCION_PNJ || foco == Foco.NEGATIVO_PNJ
					|| foco == Foco.POSITIVO_PNJ) {
				// Elegir PNJ existente
				objetoFoco = getElementoAleatorio(escena.getPartida().getPnjs());

				// ¿No había pero es primera escena? Podemos pillar un PNJ de un
				// PJ
				if (objetoFoco == null && escena.isAbierta()) {
					// Si pillamos un PJ sin PNJs... mala suerte, nueva tirada
					// y a tomar viento
					PJ pj = getElementoAleatorio(escena.getPjs());
					objetoFoco = getElementoAleatorio(pj.getPnjs());
				}

				if (objetoFoco == null) {
					// Volvemos a tirar
					foco = null;
				}
			} else if (foco == Foco.ALEJARSE_OBJETIVO
					|| foco == Foco.AVANZAR_OBJETIVO
					|| foco == Foco.CERRAR_OBJETIVO) {
				objetoFoco = getElementoAleatorio(escena.getObjetivos());

				// ¿No había pero es primera escena? Podemos pillar un objetivo
				// de un PJ
				if (objetoFoco == null && escena.isAbierta()) {
					// Si pillamos un PJ sin PNJs... mala suerte, nueva tirada
					// y a tomar viento
					PJ pj = getElementoAleatorio(escena.getPjs());
					objetoFoco = getElementoAleatorio(pj.getObjetivos());
				}

				if (objetoFoco == null) {
					// Volvemos a tirar
					foco = null;
				}
			}
		}

		// Acción
		tiradaAccion = Random.nextInt(ACCION.length);
		accion = ACCION[tiradaAccion];
		tiradaAccion++;

		// Objeto
		tiradaObjeto = Random.nextInt(OBJETO.length);
		objeto = OBJETO[tiradaObjeto];
		tiradaObjeto++;
	}

	/**
	 * Devuelve una elemento al azar de la colección.
	 * 
	 * @param <T>
	 *            tipo de la colección
	 * @param conjunto
	 *            coleccion
	 * @return elemento al azar, null si la colección está vacía.
	 */
	private <T> T getElementoAleatorio(Collection<T> conjunto) {
		T res = null;

		if (!conjunto.isEmpty()) {
			int elem = Random.nextInt(conjunto.size());
			Iterator<T> it = conjunto.iterator();

			for (int i = 0; i < elem; i++) {
				res = it.next();
			}
		}

		return res;
	}

	public int getTiradaFoco() {
		return tiradaFoco;
	}

	public int getTiradaAccion() {
		return tiradaAccion;
	}

	public int getTiradaObjeto() {
		return tiradaObjeto;
	}

	public String getAccion() {
		return accion;
	}

	public String getObjeto() {
		return objeto;
	}

	public Foco getFoco() {
		return foco;
	}

	public String getObjetoFoco() {
		return objetoFoco;
	}
}
