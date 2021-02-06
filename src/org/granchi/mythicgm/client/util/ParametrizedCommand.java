package org.granchi.mythicgm.client.util;

/**
 * Un Command pero admitiendo un par�metro gen�rico.
 */
public interface ParametrizedCommand<T> {
	/**
	 * Hace lo que tenga que hacer con su argumento.
	 * 
	 * @param arg argumento
	 */
	public void execute(T arg);
}
