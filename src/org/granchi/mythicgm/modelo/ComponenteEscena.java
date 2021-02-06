package org.granchi.mythicgm.modelo;

/**
 * Clase base de todos los objetos que componen una escena.
 */
public abstract class ComponenteEscena {
	protected Escena escena;

	/**
	 * Constructor.
	 * 
	 * @param escena escena
	 */
	public ComponenteEscena(Escena escena) {
		if (escena == null) {
			throw new IllegalArgumentException("Escena nula");
		}
		this.escena = escena;
	}

	public Escena getEscena() {
		return escena;
	}
}
