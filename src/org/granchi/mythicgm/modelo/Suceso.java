package org.granchi.mythicgm.modelo;

/**
 * Clase base de todos los sucesos que se pueden añadir a una escena, lo que
 * excluye a la premisa, que va al principio y punto.
 */
public abstract class Suceso extends ComponenteEscena {
	/**
	 * Constructor.
	 * 
	 * @param escena escena
	 */
	public Suceso(Escena escena) {
		super(escena);
	}
}
