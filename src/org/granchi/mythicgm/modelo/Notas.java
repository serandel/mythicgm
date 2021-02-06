package org.granchi.mythicgm.modelo;

/**
 * Suceso que permite insertar notas en mitad de la escena.
 */
public class Notas extends Suceso {
	protected String notas;
	
	public Notas(Escena escena, String notas) {
		super(escena);
		
		this.notas = notas;
	}
	
	public String getNotas() {
		return notas;
	}
}
