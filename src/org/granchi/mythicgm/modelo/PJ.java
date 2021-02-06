package org.granchi.mythicgm.modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Personaje Jugador
 */
public class PJ {
	private String nombre;

	// URL a una ficha que pudiera tener
	private String url;

	private SortedSet<String> pnjs = new TreeSet<String>();
	private List<String> objetivos = new LinkedList<String>();

	/**
	 * Constructor.
	 * 
	 * @param nombre
	 *            nombre del personaje
	 */
	public PJ(String nombre) {
		setNombre(nombre.trim());
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre == null) {
			throw new IllegalArgumentException("Nombre nulo");
		} else if (nombre.isEmpty()) {
			throw new IllegalArgumentException("Nombre vacío");
		}

		this.nombre = nombre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SortedSet<String> getPnjs() {
		return pnjs;
	}

	public List<String> getObjetivos() {
		return objetivos;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
