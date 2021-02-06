package org.granchi.mythicgm.client;

import java.util.Collection;

import org.granchi.mythicgm.client.recursos.Cadenas;
import org.granchi.mythicgm.client.util.DialogoConfirmacion;
import org.granchi.mythicgm.client.util.DialogoInput;
import org.granchi.mythicgm.client.util.ParametrizedCommand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Una tabla de cadenas que puede mostrarlos tal cual o permitir añadir, borrar
 * y editar.
 */
public class TablaAnadirBorrar extends FlexTable {
	private static final Cadenas cadenas = GWT.create(Cadenas.class);

	private boolean editando;

	/**
	 * Rellena la tabla con el contenido de una colección dada en modo de
	 * lectura o de edición.
	 * 
	 * @param coleccion
	 *            coleccion a representar
	 * @param editar
	 *            si se permite editar
	 * @param etiqueta
	 *            etiqueta para los diálogos, si se está en modo edición
	 */
	public void rellenar(final Collection<String> coleccion, boolean editar,
			final String etiqueta) {
		this.editando = editar;

		removeAllRows();
		int i = 0;
		for (final String elem : coleccion) {
			if (editar) {
				// Un enlace con el valor y diálogo para cambiarlo y otro para
				// borrar

				// TODO encode html
				final Anchor ancCambiar = new Anchor(elem);
				ancCambiar.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						DialogoInput dlgInput = new DialogoInput(etiqueta, elem,
								new ParametrizedCommand<String>() {
									@Override
									public void execute(String pnj) {
										coleccion.remove(elem);
										coleccion.add(pnj);
										rellenar(coleccion, true, etiqueta);
									}
								});
						dlgInput.setPopupPosition(
								ancCambiar.getAbsoluteLeft() + 40, ancCambiar
										.getAbsoluteTop() + 10);
						dlgInput.show();
					}
				});
				setWidget(i, 0, ancCambiar);

				final Anchor ancBorrar = new Anchor(cadenas.borrar());
				ancBorrar.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						DialogoConfirmacion dlgConfirm = new DialogoConfirmacion(
								cadenas.borrarAlgo() + " '" + elem + "'...",
								new Command() {
									@Override
									public void execute() {
										coleccion.remove(elem);
										rellenar(coleccion, true, etiqueta);
									}
								});

						dlgConfirm.setPopupPosition(
								ancBorrar.getAbsoluteLeft() + 40, ancBorrar
										.getAbsoluteTop() + 10);
						dlgConfirm.show();
					}
				});
				setWidget(i, 1, ancBorrar);

			} else {
				// El texto y ya

				// TODO encode HTML
				setText(i, 0, elem);
			}

			i++;
		}

		if (editar) {
			// Enlace para añadir valores
			final Anchor ancAnadir = new Anchor(cadenas.anadir());
			ancAnadir.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					DialogoInput dlgInput = new DialogoInput(etiqueta, "",
							new ParametrizedCommand<String>() {
								@Override
								public void execute(String pnj) {
									coleccion.add(pnj);
									rellenar(coleccion, true, etiqueta);
								}
							});
					dlgInput.setPopupPosition(ancAnadir.getAbsoluteLeft() + 40,
							ancAnadir.getAbsoluteTop() + 10);
					dlgInput.show();
				}
			});
			setWidget(getRowCount(), 0, ancAnadir);
			// Queda más bonito así
			getFlexCellFormatter().setColSpan(getRowCount(), 0, 2);
		}
	}

	public boolean isEditando() {
		return editando;
	}
}
