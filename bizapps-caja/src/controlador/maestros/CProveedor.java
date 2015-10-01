package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.TabBanco;
import modelo.maestros.TabCliente;
import modelo.maestros.TabEmpresa;
import modelo.maestros.TabProveedore;

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

public class CProveedor extends CGenerico {

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
	private Textbox txtRif;
	@Wire
	private Textbox txtNit;
	@Wire
	private Div divVProveedor;
	@Wire
	private Div botoneraProveedor;
	@Wire
	private Div catalogoProveedor;
	@Wire
	private Groupbox gpxDatos;
	@Wire
	private Groupbox gpxRegistro;

	Botonera botonera;
	Catalogo<TabProveedore> catalogo;
	protected List<TabProveedore> listaGeneral = new ArrayList<TabProveedore>();
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
						TabProveedore f04 = catalogo
								.objetoSeleccionadoDelCatalogo();
						clave = f04.getIDPro();
						txtDireccion.setValue(f04.getDirPro());
						txtNit.setValue(f04.getNitPro());
						txtNombre.setValue(f04.getNomPro());
						txtRif.setValue(f04.getRifPro());
						txtTelefono.setValue(f04.getTelPro());
					} else
						msj.mensajeAlerta(Mensaje.editarSoloUno);
				}
			}

			@Override
			public void salir() {
				cerrarVentana(divVProveedor, cerrar, tabs);

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
					TabProveedore fooo4 = new TabProveedore();
					if (clave == null)
						clave = servicioProveedor.buscarUltimo() + 1;
					fooo4.setIDPro(clave);
					fooo4.setNomPro(txtNombre.getValue());
					fooo4.setDirPro(txtDireccion.getValue());
					fooo4.setNitPro(txtNit.getValue());
					fooo4.setRifPro(txtRif.getValue());
					fooo4.setTelPro(txtTelefono.getValue());
					servicioProveedor.guardar(fooo4);
					msj.mensajeInformacion(Mensaje.guardado);
					limpiar();
					listaGeneral = servicioProveedor.buscarTodosOrdenados();
					catalogo.actualizarLista(listaGeneral, true);
				}

			}

			@Override
			public void eliminar() {
				if (gpxDatos.isOpen()) {
					/* Elimina Varios Registros */
					if (validarSeleccion()) {
						final List<TabProveedore> eliminarLista = catalogo
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
													servicioProveedor
															.eliminarVarios(eliminarLista);
													msj.mensajeInformacion(Mensaje.eliminado);
													listaGeneral = servicioProveedor
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
													servicioProveedor
															.eliminarUno(clave);
													msj.mensajeInformacion(Mensaje.eliminado);
													limpiar();
													listaGeneral = servicioProveedor
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
		botoneraProveedor.appendChild(botonera);

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
		txtNit.setValue("");
		txtNombre.setValue("");
		txtRif.setValue("");
		txtTelefono.setValue("");
	}

	public boolean validarSeleccion() {
		List<TabProveedore> seleccionados = catalogo.obtenerSeleccionados();
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
				|| txtNombre.getText().compareTo("") == 0
				|| txtRif.getText().compareTo("") == 0
				|| txtTelefono.getText().compareTo("") == 0) {
			return false;
		} else
			return true;
	}

	public boolean camposEditando() {
		if (txtDireccion.getText().compareTo("") != 0
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
		listaGeneral = servicioProveedor.buscarTodosOrdenados();
		catalogo = new Catalogo<TabProveedore>(catalogoProveedor,
				"Proveedores", listaGeneral, false, false, false, "Nombre",
				"RIF", "Direccion", "Telefono") {

			@Override
			protected List<TabProveedore> buscar(List<String> valores) {

				List<TabProveedore> lista = new ArrayList<TabProveedore>();

				for (TabProveedore f0004 : listaGeneral) {
					String rif = "", dir = "", tel = "";
					if (f0004.getRifPro() != null)
						rif = f0004.getRifPro();
					if (f0004.getTelPro() != null)
						tel = f0004.getTelPro();
					if (f0004.getDirPro() != null)
						dir = f0004.getDirPro();
					if (f0004.getNomPro().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& rif.toLowerCase().contains(
									valores.get(1).toLowerCase())
							&& dir.toLowerCase().contains(
									valores.get(2).toLowerCase())
							&& tel.toLowerCase().contains(
									valores.get(3).toLowerCase())) {
						lista.add(f0004);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(TabProveedore f0004) {
				String[] registros = new String[4];
				registros[0] = f0004.getNomPro();
				registros[1] = f0004.getRifPro();
				registros[2] = f0004.getDirPro();
				registros[3] = f0004.getTelPro();
				return registros;
			}
		};
		catalogo.setParent(catalogoProveedor);
	}
}
