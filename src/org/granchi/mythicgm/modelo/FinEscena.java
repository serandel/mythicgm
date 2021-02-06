package org.granchi.mythicgm.modelo;

/**
 * El fin de una escena.
 * 
 * Simplemente dice la variación de caos que sucede al final de la escena.
 * 
 * No extiende de Suceso porque no se puede añadir en mitad de una escena.
 */
public class FinEscena extends ComponenteEscena {
	private int deltaCaos;

	/**
	 * Constructor.
	 * 
	 * @param escena
	 *            escena
	 * @param deltaCaos
	 *            diferencia en el caos respecto al que tenía la escena en el
	 *            principio
	 */
	FinEscena(Escena escena, int deltaCaos) {
		super(escena);

		int caosFinal = escena.getCaos() + deltaCaos;
		// Ajustar el delta para que no se salga el caos de 1-9
		if (caosFinal < 1) {
			deltaCaos = escena.getCaos() - 1;
		} else if (caosFinal > 9) {
			deltaCaos = 9 - escena.getCaos();
		}

		this.deltaCaos = deltaCaos;
	}

	public int getDeltaCaos() {
		return deltaCaos;
	}

}
