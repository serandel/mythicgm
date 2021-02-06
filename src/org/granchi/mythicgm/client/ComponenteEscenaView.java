package org.granchi.mythicgm.client;

import org.granchi.mythicgm.client.sucesos.EventoAleatorioView;
import org.granchi.mythicgm.client.sucesos.FinEscenaView;
import org.granchi.mythicgm.client.sucesos.NotasView;
import org.granchi.mythicgm.client.sucesos.PreguntaSiNoView;
import org.granchi.mythicgm.client.sucesos.PremisaEscenaView;
import org.granchi.mythicgm.modelo.ComponenteEscena;
import org.granchi.mythicgm.modelo.EventoAleatorio;
import org.granchi.mythicgm.modelo.FinEscena;
import org.granchi.mythicgm.modelo.Notas;
import org.granchi.mythicgm.modelo.PreguntaSiNo;
import org.granchi.mythicgm.modelo.PremisaEscena;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Clase base de las vistas que reflejan, en modo de sólo lectura, un componente
 * de una escena de la partida.
 */
public abstract class ComponenteEscenaView<T extends ComponenteEscena> extends
		VerticalPanel {
	protected T componente;

	/**
	 * Constructor.
	 * 
	 * @param componente
	 *            suceso a mostrar
	 */
	protected ComponenteEscenaView(T componente) {
		if (componente == null) {
			throw new IllegalArgumentException("Componente de la escena nulo");
		}
		this.componente = componente;

		addComponentes();
	}

	/**
	 * Método para que cada suceso pueda añadir sus propias cosejas dentro.
	 */
	protected abstract void addComponentes();

	/**
	 * Método "factoría" para sacar una vista para un componente determinado de
	 * la escena.
	 * 
	 * @param componente
	 *            componente
	 * @return vista que muestra el componente
	 */
	public static ComponenteEscenaView<? extends ComponenteEscena> getComponenteEscenaView(
			ComponenteEscena componente) {
		ComponenteEscenaView<? extends ComponenteEscena> res = null;

		// TODO No se me ocurre forma mejor que no sean moscas a cañonazos
		if (componente instanceof Notas) {
			res = new NotasView((Notas) componente);
		} else if (componente instanceof EventoAleatorio) {
			res = new EventoAleatorioView((EventoAleatorio) componente);
		} else if (componente instanceof PreguntaSiNo) {
			res = new PreguntaSiNoView((PreguntaSiNo) componente);
		} else if (componente instanceof PremisaEscena) {
			res = new PremisaEscenaView((PremisaEscena) componente);
		} else if (componente instanceof FinEscena) {
			res = new FinEscenaView((FinEscena) componente);
		}

		return res;
	}
}
