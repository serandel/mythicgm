package org.granchi.mythicgm.client;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Datos de la partida.
 */
// TODO devolver iterators en vez de las colecciones
public class Partida {
	private String nombre;
	private int caos = 5;

	private SortedMap<String, PJ> pjs = new TreeMap<String, PJ>();
	private SortedSet<String> pnjs = new TreeSet<String>();
	private List<String>objetivos = new LinkedList<String>();

	/**
	 * Constructor.
	 */
	public Partida() {
		// TODO Ya tendrá cosas
		// TODO cómo se suben objetivos
		objetivos.add("Encontrar al Buda");
		objetivos.add("Matar al Buda");
		
		// TODO se pueden quitar objetivos sin avisar?
		
		// TODO como se suben y se quitan pjs
		PJ b = new PJ("El Bibliotecario");
		pjs.put(b.getNombre(), b);
		
		// TODO habrá que quitar pnjs también
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public int getCaos() {
		return caos;
	}

	public void setCaos(int caos) {
		if (caos < 1 || caos > 9) {
			throw new IllegalArgumentException("Nivel de caos inválido: "
					+ caos);
		}
		
		this.caos = caos;
	}

	public SortedMap<String, PJ> getPjs() {
		return pjs;
	}
	
	public SortedSet<String> getPnjs() {
		return pnjs;
	}


	public List<String> getObjetivos() {
		return objetivos;
	}
	
	
}
