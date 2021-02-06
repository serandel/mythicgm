package org.granchi.mythicgm.modelo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Clase abstracta para que tanto Partida como Escena puedan tener su lista de
 * PJs, PNJs y objetivos.
 */
public abstract class TienePersonajesObjetivos {
	// No pongo TreeSet porque cuando se cambia un nombre al PJ se monta un lío
	// de narices... que lo ordene la pantalla que lo pinta y punto
	protected Set<PJ> pjs = new HashSet<PJ>();
	protected SortedSet<String> pnjs = new TreeSet<String>();
	protected List<String>objetivos = new LinkedList<String>();
	
	public Set<PJ> getPjs() {
		return pjs;
	}
	
	public SortedSet<String> getPnjs() {
		return pnjs;
	}


	public List<String> getObjetivos() {
		return objetivos;
	}
}
