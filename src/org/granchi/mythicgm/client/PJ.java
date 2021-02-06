package org.granchi.mythicgm.client;

/**
 * Personaje Jugador
 */
public class PJ {
	private String nombre;

	/**
	 * Constructor.
	 * 
	 * @param nombre nombre del personaje
	 */
	public PJ(String nombre) {
		setNombre(nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre == null) {
			throw new IllegalArgumentException("Nombre nulo");
		}
		
		this.nombre = nombre.trim();
		
		if (this.nombre.isEmpty()) {
			throw new IllegalArgumentException("Nombre vacío");
		}
	}
	
	
}
