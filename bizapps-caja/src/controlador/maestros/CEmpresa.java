package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.TabEmpresa;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CEmpresa extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox txtNombre;
	@Wire
	private Textbox txtDireccion;
	@Wire
	private Textbox txtTelefono;
	@Wire
	private Textbox txtFax;
	@Wire
	private Textbox txtRif;
	@Wire
	private Textbox txtNit;
	@Wire
	private Div divVEmpresa;
	@Wire
	private Div botoneraEmpresa;
	@Wire
	private Div catalogoEmpresa;
	@Wire
	private Groupbox gpxDatos;
	@Wire
	private Groupbox gpxRegistro;

	Botonera botonera;
	Catalogo<TabEmpresa> catalogo;
	protected List<TabEmpresa> listaGeneral = new ArrayList<TabEmpresa>();
	Integer clave = null;

	@Override
	public void inicializar() throws IOException {
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions
				.getCurrent().getAttribute("mapaGeneral");
		if (map != null) {
			if (map.get("tabsGenerales") != null) {
				tabs = (List<Tab>) map.get("tabsGenerales");
				cerrar = (String) map.get("titulo");
				map.clear();
				map = null;
			}
		}
		txtNombre.setFocus(true);
		mostrarCatalogo();

		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				if (validarSeleccion()) {
					if (catalogo.obtenerSeleccionados().size() == 1) {
						mostrarBotones(false);
						abrirRegistro();
						TabEmpresa f04 = catalogo
								.objetoSeleccionadoDelCatalogo();
						clave = f04.getIDEmp();
						txtDireccion.setValue(f04.getDirEmp());
						txtFax.setValue(f04.getFaxEmp());
						txtNit.setValue(f04.getNitEmp());
						txtNombre.setValue(f04.getNomEmp());
						txtRif.setValue(f04.getRifEmp());
						txtTelefono.setValue(f04.getTelEmp());
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}
			}

			@Override
			public void salir() {
				cerrarVentana(divVEmpresa, cerrar, tabs);

			}

			@Override
			public void reporte() {
			}

			@Override
			public void limpiar() {
				mostrarBotones(false);
				limpiarCampos();
				clave = null;
			}

			@Override
			public void guardar() {
				if (validar()) {
					TabEmpresa fooo4 = new TabEmpresa();
					if (clave == null)
						clave = servicioEmpresa.buscarUltimo() + 1;
					fooo4.setIDEmp(clave);
					fooo4.setNomEmp(txtNombre.getValue());
					fooo4.setDirEmp(txtDireccion.getValue());
					fooo4.setFaxEmp(txtFax.getValue());
					fooo4.setNitEmp(txtNit.getValue());
					fooo4.setRifEmp(txtRif.getValue());
					fooo4.setTelEmp(txtTelefono.getValue());
					servicioEmpresa.guardar(fooo4);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					listaGeneral = servicioEmpresa.buscarTodosOrdenados();
					catalogo.actualizarLista(listaGeneral, true);
				}

			}

			@Override
			public void eliminar() {
				if (gpxDatos.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<TabEmpresa> eliminarLista = catalogo
								.obtenerSeleccionados();
						Messagebox
								.show("¿Desea Eliminar los "
										+ eliminarLista.size() + " Registros?",
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													servicioEmpresa
															.eliminarVarios(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioEmpresa
															.buscarTodosOrdenados();
													catalogo.actualizarLista(
															listaGeneral, true);
												}
											}
										});
					}
				} else {
					/* Elimina un solo registro */
					if (clave != null) {
						Messagebox
								.show(Mensaje.deseaEliminar,
										"Alerta",
										Messagebox.OK | Messagebox.CANCEL,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event evt)
													throws InterruptedException {
												if (evt.getName()
														.equals("onOK")) {
													servicioEmpresa
															.eliminarUno(clave);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioEmpresa
															.buscarTodosOrdenados();
													catalogo.actualizarLista(
															listaGeneral, true);
												}
											}
										});
					} else
						msj.mensajeAlerta(Mensaje.noSeleccionoRegistro);
				}

			}

			@Override
			public void buscar() {

				abrirCatalogo();
			}

			@Override
			public void ayuda() {

			}

			@Override
			public void annadir() {
				abrirRegistro();
				mostrarBotones(false);
			}
		};
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(5).setVisible(false);
		botoneraEmpresa.appendChild(botonera);

	}

	public void mostrarBotones(boolean bol) {

		botonera.getChildren().get(1).setVisible(!bol);
		botonera.getChildren().get(2).setVisible(bol);
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botonera.getChildren().get(0).setVisible(bol);
		botonera.getChildren().get(3).setVisible(!bol);
		botonera.getChildren().get(5).setVisible(!bol);
	}

	public void limpiarCampos() {
		clave = null;
		txtDireccion.setValue("");
		txtFax.setValue("");
		txtNit.setValue("");
		txtNombre.setValue("");
		txtRif.setValue("");
		txtTelefono.setValue("");
	}

	public boolean validarSeleccion() {
		List<TabEmpresa> seleccionados = catalogo.obtenerSeleccionados();
		if (seleccionados == null) {
			msj.mensajeAlerta(Mensaje.noHayRegistros);
			return false;
		} else {
			if (seleccionados.isEmpty()) {
				msj.mensajeAlerta(Mensaje.noSeleccionoItem);
				return false;
			} else {
				return true;
			}
		}
	}

	protected boolean validar() {
		if (!camposLLenos()) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	public boolean camposLLenos() {
		if (txtDireccion.getText().compareTo("") == 0
				|| txtFax.getText().compareTo("") == 0
				|| txtNombre.getText().compareTo("") == 0
				|| txtRif.getText().compareTo("") == 0
				|| txtTelefono.getText().compareTo("") == 0) {
			return false;
		} else
			return true;
	}

	public boolean camposEditando() {
		if (txtDireccion.getText().compareTo("") != 0
				|| txtFax.getText().compareTo("") != 0
				|| txtNombre.getText().compareTo("") != 0
				|| txtRif.getText().compareTo("") != 0
				|| txtTelefono.getText().compareTo("") != 0
				|| txtNit.getText().compareTo("") != 0) {
			return true;
		} else
			return false;
	}

	@Listen("onClick = #gpxRegistro")
	public void abrirRegistro() {
		gpxDatos.setOpen(false);
		gpxRegistro.setOpen(true);
		mostrarBotones(false);
	}

	@Listen("onOpen = #gpxDatos")
	public void abrirCatalogo() {
		gpxDatos.setOpen(false);
		if (camposEditando()) {
			Messagebox.show(Mensaje.estaEditando, "Alerta", Messagebox.YES
					| Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onYes")) {
								gpxDatos.setOpen(false);
								gpxRegistro.setOpen(true);
							} else {
								if (evt.getName().equals("onNo")) {
									gpxDatos.setOpen(true);
									gpxRegistro.setOpen(false);
									limpiarCampos();
									mostrarBotones(true);
								}
							}
						}
					});
		} else {
			gpxDatos.setOpen(true);
			limpiarCampos();
			gpxRegistro.setOpen(false);
			mostrarBotones(true);
		}
	}

	public void mostrarCatalogo() {
		listaGeneral = servicioEmpresa.buscarTodosOrdenados();
		catalogo = new Catalogo<TabEmpresa>(catalogoEmpresa, "Empresas",
				listaGeneral, false, false, false, "Nombre", "RIF",
				"Direccion", "Telefono") {

			@Override
			protected List<TabEmpresa> buscar(List<String> valores) {

				List<TabEmpresa> lista = new ArrayList<TabEmpresa>();

				for (TabEmpresa f0004 : listaGeneral) {
					if (f0004.getNomEmp().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& f0004.getRifEmp().toLowerCase()
									.contains(valores.get(1).toLowerCase())
							&& f0004.getDirEmp().toLowerCase()
									.contains(valores.get(2).toLowerCase())
							&& f0004.getTelEmp().toLowerCase()
									.contains(valores.get(3).toLowerCase())) {
						lista.add(f0004);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(TabEmpresa f0004) {
				String[] registros = new String[4];
				registros[0] = f0004.getNomEmp();
				registros[1] = f0004.getRifEmp();
				registros[2] = f0004.getDirEmp();
				registros[3] = f0004.getTelEmp();
				return registros;
			}
		};
		catalogo.setParent(catalogoEmpresa);
	}

}
