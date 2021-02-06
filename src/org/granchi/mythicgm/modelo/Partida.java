package org.granchi.mythicgm.modelo;

import java.util.ArrayList;
import java.util.List;

import org.granchi.mythicgm.client.recursos.CadenasModelo;

import com.google.gwt.core.client.GWT;

/**
 * Datos de la partida.
 * 
 * La lista de PJs, PNJs y objetivos está siempre actualizada, y es la que
 * permite cambios entre escenas.
 */
public class Partida extends TienePersonajesObjetivos {
	private static final CadenasModelo cadenas = GWT
			.create(CadenasModelo.class);

	public static final int CAOS_DEFECTO = 5;

	private String nombre = cadenas.partidaAnonima();

	private List<Escena> escenas = new ArrayList<Escena>();

	/**
	 * Constructor.
	 */
	public Partida() {
		// TODO Ya tendrá cosas
		objetivos.add("Encontrar al Buda");
		objetivos.add("Matar al Buda");

		PJ b = new PJ("El Bibliotecario");
		b.setUrl("http://www.google.com");
		b.getPnjs().add("Ponder Stibbons");
		b.getPnjs().add("Lord Vetinari");
		b.getObjetivos().add("Comer plátanos");
		pjs.add(b);

		pjs.add(new PJ("Rincewind"));
		// TODO habrá que quitar pnjs también
		pnjs.add("PNJ de prueba");
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la escena actual.
	 * 
	 * @return escena actual, null si no hay ninguna
	 */
	public Escena getEscenaActual() {
		return (escenas.isEmpty() ? null : escenas.get(escenas.size() - 1));
	}

	/**
	 * Crea una nueva Escena para la partida pero no la guarda todavía en ésta
	 * por si se quiere cancelar.
	 * 
	 * @return nueva escena
	 */
	public Escena crearEscena() {
		if (!escenas.isEmpty() && escenas.get(escenas.size() - 1).isAbierta()) {
			throw new IllegalStateException("Ya existe una escena abierta");
		}
		return new Escena(this);
	}

	/**
	 * Añade una nueva escena.
	 * 
	 * @param escena
	 *            escena
	 */
	public void addEscena(Escena escena) {
		if (escena == null) {
			throw new IllegalArgumentException("Escena nula");
		}

		escenas.add(escena);
	}

	/**
	 * Devuelve la lista de escenas hasta el momento.
	 * 
	 * @return
	 */
	public List<Escena> getEscenas() {
		return escenas;
	}

	/**
	 * Dice si la partida está entre escenas o no.
	 * 
	 * @return si la partida está entre escenas
	 */
	public boolean isEntreEscenas() {
		Escena e = getEscenaActual();
		return (e == null) || (!e.isAbierta());
	}
}
