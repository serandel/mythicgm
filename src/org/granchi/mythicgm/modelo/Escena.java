package org.granchi.mythicgm.modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.granchi.mythicgm.client.recursos.CadenasModelo;

import com.google.gwt.core.client.GWT;

/**
 * Una escena de la aventura.
 * 
 * Tiene la lista de PJs, PNJs y objetivos con la que funcion�.
 * 
 * Los objetivos que se cierren se pasan a la lista de objetivos cerrados, para
 * que si revisitamos la escena luego quede constancia de que estuvieron ah�
 * pero que en sucesivas tiradas no puedan seguir saliendo.
 */
public class Escena extends TienePersonajesObjetivos {
	private static final CadenasModelo cadenas = GWT
			.create(CadenasModelo.class);

	private Partida partida;

	private String nombre;
	private int caos;

	private PremisaEscena premisa;
	private List<Suceso> sucesos = new ArrayList<Suceso>();
	private FinEscena fin;

	private List<String> objetivosCerrados = new LinkedList<String>();

	/**
	 * Empieza una escena que ir� a continuaci�n de la �ltima ya existente en la
	 * partida.
	 * 
	 * Se llama desde Partida.crearEscena().
	 * 
	 * No se a�ade todav�a a la partida, eso se hace con partida.addEscena(e).
	 * 
	 * @param partida
	 *            partida
	 */
	Escena(Partida partida) {
		if (partida == null) {
			throw new IllegalArgumentException("Partida nula");
		}
		this.partida = partida;

		setNombre("");

		Escena ultimaEscena = partida.getEscenaActual();
		caos = (ultimaEscena == null ? Partida.CAOS_DEFECTO : ultimaEscena
				.getCaosFinal());

		pjs.addAll(partida.getPjs());
		pnjs.addAll(partida.getPnjs());
		objetivos.addAll(partida.getObjetivos());

		premisa = new PremisaEscena(this);
	}

	public Partida getPartida() {
		return partida;
	}

	public boolean isPrimeraEscena() {
		List<Escena> escenas = partida.getEscenas();

		// Nos hayan a�adido a�n o no
		return (escenas.size() == 0) || (escenas.get(0) == this);
	}

	public boolean isUltimaEscena() {
		// Nos hayan a�adido a�n o no
		return partida.getEscenaActual() == null
				|| partida.getEscenaActual() == this;
	}

	public PremisaEscena getPremisa() {
		return premisa;
	}

	/**
	 * A�ade un suceso a la partida.
	 * 
	 * @param suceso
	 *            suceso
	 */
	public void add(Suceso suceso) {
		sucesos.add(suceso);
	}

	public FinEscena getFin() {
		return fin;
	}

	public void createFin(int deltaCaos) {
		this.fin = new FinEscena(this, deltaCaos);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = ((nombre == null || nombre.isEmpty()) ? cadenas
				.escenaAnonima() : nombre);
	}

	public boolean isAbierta() {
		return fin == null;
	}

	public int getCaos() {
		return caos;
	}

	public int getCaosFinal() {
		if (fin == null) {
			throw new IllegalStateException("Escena abierta");
		}
		return caos + fin.getDeltaCaos();
	}

	/**
	 * Cierra un objetivo, pas�ndolo de la lista de objetivos a la de objetivos
	 * cerrados. Si no exist�a en la primera, no pasa nada.
	 * 
	 * @param objetivo
	 *            objetivo
	 */
	public void cerrarObjetivo(String objetivo) {
		if (objetivos.remove(objetivo)) {
			objetivosCerrados.add(objetivo);
		}
	}
}
