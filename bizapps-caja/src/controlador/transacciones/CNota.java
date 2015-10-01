package controlador.transacciones;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.maestros.F0005;
import modelo.maestros.TabConfigur;
import modelo.maestros.TabEmpresa;
import modelo.maestros.TabProveedore;
import modelo.pk.TabDetallesPK;
import modelo.pk.TabNotasPK;
import modelo.transacciones.TabDetalles;
import modelo.transacciones.TabNotas;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import security.modelo.UsuarioSeguridad;
import componente.Botonera;
import componente.BuscadorUDC;
import componente.Catalogo;
import componente.Convertidor;
import componente.Mensaje;
import controlador.maestros.CGenerico;

public class CNota extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Div botoneraNota;
	@Wire
	private Div divVNota;
	@Wire
	private Div catalogoNota;
	@Wire
	private Groupbox gpxDatos;
	@Wire
	private Groupbox gpxRegistro;
	@Wire
	private Label lblNombre;
	@Wire
	private Label lblDireccion;
	@Wire
	private Label lblTelefono;
	@Wire
	private Label lblRif;
	@Wire
	private Label lblFax;
	@Wire
	private Label lblNit;
	@Wire
	private Intbox txtNumero;
	@Wire
	private Datebox dtbFecha;
	@Wire
	private Intbox txtProveedor;
	@Wire
	private Label lblProveedor;
	@Wire
	private Label lblElaborado;
	@Wire
	private Label lblRevisado;
	@Wire
	private Label lblAutorizado;
	@Wire
	private Div divCatalogoProveedor;
	@Wire
	private Div divBuscadorTipo;
	BuscadorUDC buscadorTipo;
	@Wire
	private Div divBuscadorClase;
	BuscadorUDC buscadorClase;
	@Wire
	private Div divBuscadorMoneda;
	BuscadorUDC buscadorMoneda;
	@Wire
	private Listbox ltbPedidos;
	@Wire
	private Textbox txtReferencia;
	@Wire
	private Textbox txtConcepto;
	@Wire
	private Doublebox txtTotal;
	@Wire
	private Doublespinner spnImporte;
	Catalogo<TabProveedore> catalogoProveedor;
	Botonera botonera;
	Catalogo<TabNotas> catalogo;
	protected List<TabNotas> listaGeneral = new ArrayList<TabNotas>();
	protected List<TabDetalles> listaDetalle = new ArrayList<TabDetalles>();
	private TabNotasPK clave = null;
	private Integer idEmpresa = 0;

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
		TabConfigur configuracion = servicioConfiguracion.buscar(1);
		if (configuracion != null) {
			TabEmpresa empresa = servicioEmpresa.buscar(configuracion
					.getIdEmp());
			if (empresa != null) {
				txtNumero.setValue(0);
				idEmpresa = empresa.getIDEmp();
				lblNombre.setValue(empresa.getNomEmp());
				lblDireccion.setValue(empresa.getDirEmp());
				lblFax.setValue(empresa.getFaxEmp());
				lblNit.setValue(empresa.getNitEmp());
				lblRif.setValue(empresa.getRifEmp());
				lblTelefono.setValue(empresa.getTelEmp());
				lblElaborado.setValue(nombreUsuarioSesion());
				lblAutorizado.setValue(configuracion.getAutoriza());
				lblRevisado.setValue(configuracion.getRevisa());
				mostrarCatalogo();
				cargarBuscadores();
				botonera = new Botonera() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void seleccionar() {
						if (validarSeleccion()) {
							if (catalogo.obtenerSeleccionados().size() == 1) {
								mostrarBotones(false);
								abrirRegistro();
								TabNotas not = catalogo
										.objetoSeleccionadoDelCatalogo();
								clave = not.getId();
								TabProveedore proveedor = servicioProveedor
										.buscar(not.getIDPro());
								txtProveedor.setValue(proveedor.getIDPro());
								lblProveedor.setValue(proveedor.getNomPro());
								txtNumero.setValue(clave.getIDNot());
								txtTotal.setValue(not.getTotNot());
								buscadorClase.settearCampo(servicioF0005
										.buscar("00", "02", clave.getCatNot()));
								buscadorMoneda.settearCampo(servicioF0005
										.buscar("00", "04", not.getTipMon()));
								buscadorTipo.settearCampo(servicioF0005.buscar(
										"00", "05", clave.getTipNot()));
								idEmpresa = clave.getIDEmp();
								TabEmpresa empresa = servicioEmpresa
										.buscar(idEmpresa);
								lblNombre.setValue(empresa.getNomEmp());
								lblDireccion.setValue(empresa.getDirEmp());
								lblFax.setValue(empresa.getFaxEmp());
								lblNit.setValue(empresa.getNitEmp());
								lblRif.setValue(empresa.getRifEmp());
								lblTelefono.setValue(empresa.getTelEmp());
								lblElaborado.setValue(not.getUsuNot());
								dtbFecha.setValue(not.getFecNot());

								listaDetalle = servicioDetalleNota
										.buscar(clave);
								if (!listaDetalle.isEmpty()) {
									lblAutorizado.setValue(listaDetalle.get(0)
											.getAutNot());
									lblRevisado.setValue(listaDetalle.get(0)
											.getRevNot());
								}
								ltbPedidos
										.setModel(new ListModelList<TabDetalles>(
												listaDetalle));
								ltbPedidos.renderAll();
								buscadorClase.inhabilitarCampo();
								buscadorMoneda.inhabilitarCampo();
								buscadorTipo.inhabilitarCampo();
							} else
								msj.mensajeAlerta(Mensaje.editarSoloUno);
						}
					}

					@Override
					public void salir() {
						cerrarVentana(divVNota, cerrar, tabs);
					}

					@Override
					public void reporte() {
					}

					@Override
					public void limpiar() {
						mostrarBotones(false);
						limpiarCampos();
						clave = null;
						habilitarTextClave();
					}

					@Override
					public void guardar() {
						if (validar()) {
							TabNotas fooo4 = new TabNotas();
							if (clave == null) {
								clave = new TabNotasPK();
								clave.setCatNot(buscadorClase.obtenerCaja());
								clave.setIDEmp(idEmpresa);
								clave.setIDNot(servicioNota.buscarUltimo(
										buscadorClase.obtenerCaja(), idEmpresa,
										buscadorTipo.obtenerCaja()) + 1);
								clave.setTipNot(buscadorTipo.obtenerCaja());
							} else {
								servicioDetalleNota.limpiar(clave);
								// servicioNota.limpiar(clave);
							}
							fooo4.setId(clave);
							fooo4.setAnoNot((short) calendario
									.get(Calendar.YEAR));
							fooo4.setEstNot("Activo");
							fooo4.setFecNot(new Timestamp(dtbFecha.getValue()
									.getTime()));
							fooo4.setHorNot(new Timestamp(dtbFecha.getValue()
									.getTime()));
							fooo4.setIDPro(txtProveedor.getValue());
							fooo4.setTipMon(buscadorMoneda.obtenerCaja());
							fooo4.setTotNot(txtTotal.getValue());
							String elaborado = lblElaborado.getValue();
							if (elaborado.length() > 10)
								elaborado = elaborado.substring(0, 9);
							fooo4.setUsuNot(elaborado);
							fooo4.setValMon((double) 0);
							servicioNota.guardar(fooo4);

							TabDetallesPK idDetalle = new TabDetallesPK();
							TabDetalles detalle = new TabDetalles();
							for (int i = 0; i < listaDetalle.size(); i++) {
								idDetalle = new TabDetallesPK();
								idDetalle.setCatNot(clave.getCatNot());
								idDetalle.setIDEmp(clave.getIDEmp());
								idDetalle.setIDNot(clave.getIDNot());
								idDetalle.setRow((short) (i + 1));
								idDetalle.setTipNot(clave.getTipNot());

								detalle = new TabDetalles();
								detalle.setAutNot(lblAutorizado.getValue());
								detalle.setConNot(listaDetalle.get(i)
										.getConNot());
								detalle.setHorNot(new Timestamp(dtbFecha
										.getValue().getTime()));
								detalle.setId(idDetalle);
								detalle.setImpNot(listaDetalle.get(i)
										.getImpNot());
								detalle.setMonNot(listaDetalle.get(i)
										.getImpNot());
								detalle.setRefNot(listaDetalle.get(i)
										.getRefNot());
								detalle.setRevNot(lblRevisado.getValue());
								servicioDetalleNota.guardar(detalle);
							}
							msj.mensajeInformacion(Mensaje.guardado);
							listaGeneral = servicioNota.buscarTodosOrdenados();
							catalogo.actualizarLista(listaGeneral, true);
							Clients.evalJavaScript("window.open('"
									+ damePath()
									+ "Generador?valor=1&valor2="
									+ clave.getCatNot()
									+ "&valor3="
									+ clave.getTipNot()
									+ "&valor4="
									+ clave.getIDEmp()
									+ "&valor5="
									+ clave.getIDNot()
									+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
						}

					}

					@Override
					public void eliminar() {
						if (gpxDatos.isOpen()) {
							/* Elimina Varios Registros */
							if (validarSeleccion()) {
								final List<TabNotas> eliminarLista = catalogo
										.obtenerSeleccionados();
								boolean error = false;
								for (int i = 0; i < eliminarLista.size(); i++) {
									if (eliminarLista.get(i).getEstNot()
											.equals("Eliminado")) {
										error = true;
										break;
									}
								}
								if (!error)
									Messagebox
											.show("¿Desea Eliminar los "
													+ eliminarLista.size()
													+ " Registros?",
													"Alerta",
													Messagebox.OK
															| Messagebox.CANCEL,
													Messagebox.QUESTION,
													new org.zkoss.zk.ui.event.EventListener<Event>() {
														public void onEvent(
																Event evt)
																throws InterruptedException {
															if (evt.getName()
																	.equals("onOK")) {
																for (int i = 0; i < eliminarLista
																		.size(); i++) {
																	TabNotas objeto = eliminarLista
																			.get(i);
																	objeto.setEstNot("Eliminado");
																	servicioNota
																			.guardar(objeto);
																}
																msj.mensajeInformacion(Mensaje.eliminado);
																listaGeneral = servicioNota
																		.buscarTodosOrdenados();
																catalogo.actualizarLista(
																		listaGeneral,
																		true);
															}
														}
													});
								else
									msj.mensajeAlerta("No se pueden eliminar registros ya eliminados");
							}
						} else {
							/* Elimina un solo registro */
							if (clave != null) {
								TabNotas objeto = servicioNota.buscar(clave);
								if (!objeto.getEstNot().equals("Eliminado"))
									Messagebox
											.show(Mensaje.deseaEliminar,
													"Alerta",
													Messagebox.OK
															| Messagebox.CANCEL,
													Messagebox.QUESTION,
													new org.zkoss.zk.ui.event.EventListener<Event>() {
														public void onEvent(
																Event evt)
																throws InterruptedException {
															if (evt.getName()
																	.equals("onOK")) {
																objeto.setEstNot("Eliminado");
																servicioNota
																		.guardar(objeto);
																msj.mensajeInformacion(Mensaje.eliminado);
																limpiar();
																listaGeneral = servicioNota
																		.buscarTodosOrdenados();
																catalogo.actualizarLista(
																		listaGeneral,
																		true);
															}
														}
													});
								else
									msj.mensajeAlerta("No se pueden eliminar registros ya eliminados");
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
				Button limpiar = (Button) botonera.getChildren().get(3);
				limpiar.setImage("/public/imagenes/botones/reporte.png");
				limpiar.setLabel("Imprimir");
				botoneraNota.appendChild(botonera);
			} else {
				msj.mensajeAlerta("No se pueden Realizar notas sin previamente configurar los datos de la Empresa, contacte al Administrador");
				cerrarVentana(divVNota, cerrar, tabs);
			}
		} else {
			msj.mensajeAlerta("No se pueden Realizar notas sin previamente configurar los datos de la Empresa, contacte al Administrador");
			cerrarVentana(divVNota, cerrar, tabs);
		}

	}

	private void cargarBuscadores() {
		List<F0005> listF0005 = servicioF0005
				.buscarParaUDCOrdenados("00", "02");
		buscadorClase = new BuscadorUDC("Clase", 100, listF0005, true, false,
				false, "00", "02", "23%", "23%", "7%", "23%") {
			private static final long serialVersionUID = 1L;

			@Override
			protected F0005 buscar() {
				return servicioF0005.buscar("00", "02",
						buscadorClase.obtenerCaja());
			}
		};
		divBuscadorClase.appendChild(buscadorClase);

		listF0005 = servicioF0005.buscarParaUDCOrdenados("00", "05");
		buscadorTipo = new BuscadorUDC("Tipo", 100, listF0005, true, false,
				false, "00", "05", "23%", "23%", "7%", "23%") {
			private static final long serialVersionUID = 1L;

			@Override
			protected F0005 buscar() {
				return servicioF0005.buscar("00", "05",
						buscadorTipo.obtenerCaja());
			}
		};
		divBuscadorTipo.appendChild(buscadorTipo);

		listF0005 = servicioF0005.buscarParaUDCOrdenados("00", "04");
		buscadorMoneda = new BuscadorUDC("Moneda", 100, listF0005, true, false,
				false, "00", "04", "23%", "23%", "7%", "23%") {
			private static final long serialVersionUID = 1L;

			@Override
			protected F0005 buscar() {
				return servicioF0005.buscar("00", "04",
						buscadorMoneda.obtenerCaja());
			}
		};
		divBuscadorMoneda.appendChild(buscadorMoneda);
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
		TabConfigur configuracion = servicioConfiguracion.buscar(1);
		TabEmpresa empresa = servicioEmpresa.buscar(configuracion.getIdEmp());
		txtNumero.setValue(0);
		idEmpresa = empresa.getIDEmp();
		lblNombre.setValue(empresa.getNomEmp());
		lblDireccion.setValue(empresa.getDirEmp());
		lblFax.setValue(empresa.getFaxEmp());
		lblNit.setValue(empresa.getNitEmp());
		lblRif.setValue(empresa.getRifEmp());
		lblTelefono.setValue(empresa.getTelEmp());
		lblElaborado.setValue(nombreUsuarioSesion());
		lblAutorizado.setValue(configuracion.getAutoriza());
		lblRevisado.setValue(configuracion.getRevisa());
		clave = null;
		txtTotal.setValue(null);
		txtNumero.setValue(null);
		txtProveedor.setValue(null);
		lblProveedor.setValue("");
		buscadorClase.settearCampo(null);
		buscadorMoneda.settearCampo(null);
		buscadorTipo.settearCampo(null);
		listaDetalle.clear();
		ltbPedidos.getItems().clear();
		limpiarCamposItem();
	}

	public boolean validarSeleccion() {
		List<TabNotas> seleccionados = catalogo.obtenerSeleccionados();
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
		} else {
			if (ltbPedidos.getItems().isEmpty()) {
				msj.mensajeError("Debe agregar al menos un item a los detalles");
				return false;
			}
			return true;
		}

	}

	public boolean camposLLenos() {
		if (txtProveedor.getText().compareTo("") == 0
				|| buscadorClase.obtenerCaja().compareTo("") == 0
				|| buscadorMoneda.obtenerCaja().compareTo("") == 0
				|| buscadorTipo.obtenerCaja().compareTo("") == 0) {
			return false;
		} else
			return true;
	}

	public boolean camposEditando() {
		if (txtProveedor.getText().compareTo("") != 0
				|| buscadorClase.obtenerCaja().compareTo("") != 0
				|| buscadorMoneda.obtenerCaja().compareTo("") != 0
				|| buscadorTipo.obtenerCaja().compareTo("") != 0) {
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
									habilitarTextClave();
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
		listaGeneral = servicioNota.buscarTodosOrdenados();
		catalogo = new Catalogo<TabNotas>(catalogoNota, "Notas", listaGeneral,
				false, false, false, "Fecha", "Empresa", "Tipo", "Estado") {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			protected List<TabNotas> buscar(List<String> valores) {

				List<TabNotas> lista = new ArrayList<TabNotas>();

				for (TabNotas f0004 : listaGeneral) {
					if (Convertidor.formatoFecha.format(f0004.getFecNot())
							.toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& f0004.getId().getTipNot()
									.contains(valores.get(2).toLowerCase())
							&& f0004.getEstNot().toLowerCase()
									.contains(valores.get(3).toLowerCase())) {
						lista.add(f0004);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(TabNotas f0004) {
				String empresa = "";
				if (f0004.getId().getIDEmp() != null) {
					TabEmpresa empresa1 = servicioEmpresa.buscar(f0004.getId()
							.getIDEmp());
					empresa = empresa1.getNomEmp();
				}
				String[] registros = new String[4];
				registros[0] = Convertidor.formatoFecha.format(f0004
						.getFecNot());
				registros[1] = empresa;
				registros[2] = f0004.getId().getTipNot();
				registros[3] = f0004.getEstNot();
				return registros;
			}
		};
		catalogo.setParent(catalogoNota);
	}

	public void habilitarTextClave() {
		buscadorClase.habilitarCampos();
		buscadorMoneda.habilitarCampos();
		buscadorTipo.habilitarCampos();
	}

	@Listen("onClick = #btnBuscarProveedor")
	public void mostrarCatalogoF0004(Event evento) {
		final List<TabProveedore> listF0004 = servicioProveedor
				.buscarTodosOrdenados();
		catalogoProveedor = new Catalogo<TabProveedore>(divCatalogoProveedor,
				"Catalogo de Proveedores", listF0004, true, false, false,
				"Nombre", "RIF", "Direccion", "Telefono") {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			protected List<TabProveedore> buscar(List<String> valores) {

				List<TabProveedore> lista = new ArrayList<TabProveedore>();

				for (TabProveedore f0004 : listF0004) {
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
		catalogoProveedor.setClosable(true);
		catalogoProveedor.setWidth("80%");
		catalogoProveedor.setParent(divCatalogoProveedor);
		catalogoProveedor.doModal();
	}

	@Listen("onSeleccion = #divCatalogoProveedor")
	public void seleccion() {
		TabProveedore f0004 = catalogoProveedor.objetoSeleccionadoDelCatalogo();
		txtProveedor.setValue(f0004.getIDPro());
		lblProveedor.setValue(f0004.getNomPro());
		catalogoProveedor.setParent(null);
	}

	private void limpiarCamposItem() {
		txtConcepto.setValue("");
		txtReferencia.setValue("");
		spnImporte.setValue((double) 0);
	}

	private boolean validarItems() {
		if (txtConcepto.getText().compareTo("") == 0
				|| txtReferencia.getText().compareTo("") == 0
				|| spnImporte.getValue() == 0)
			return false;
		else
			return true;
	}

	@Listen("onClick = #btnAgregar")
	public void annadirLista() {
		if (validarItems()) {
			// contador++;
			Double cantidad = txtTotal.getValue();
			if (cantidad == null)
				cantidad = (double) 0;
			txtTotal.setValue(cantidad + spnImporte.getValue());
			TabDetalles object = new TabDetalles();
			object.setConNot(txtConcepto.getValue());
			object.setRefNot(txtReferencia.getValue());
			object.setMonNot(spnImporte.getValue());
			object.setImpNot(spnImporte.getValue());
			listaDetalle.add(object);
			ltbPedidos.setModel(new ListModelList<TabDetalles>(listaDetalle));
			ltbPedidos.renderAll();
			limpiarCamposItem();
		} else
			msj.mensajeError(Mensaje.camposVaciosItem);
	}

	@Listen("onClick = #btnVer")
	public void seleccionarItemLista() {
		if (ltbPedidos.getItemCount() != 0) {
			if (ltbPedidos.getSelectedItems().size() == 1) {
				Listitem listItem = ltbPedidos.getSelectedItem();
				TabDetalles modelo = listItem.getValue();
				spnImporte.setValue(modelo.getImpNot());
				txtConcepto.setValue(modelo.getConNot());
				txtReferencia.setValue(modelo.getRefNot());
				ltbPedidos.removeItemAt(listItem.getIndex());
				listaDetalle.remove(modelo);
				ltbPedidos.renderAll();
				Double cantidad = txtTotal.getValue();
				if (cantidad == null)
					cantidad = (double) 0;
				txtTotal.setValue(cantidad - modelo.getImpNot());
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);
		} else
			msj.mensajeAlerta(Mensaje.noHayRegistros);
	}

	@Listen("onClick = #btnRemover")
	public void removerItem() {
		if (ltbPedidos.getItemCount() != 0) {
			if (ltbPedidos.getSelectedItems().size() == 1) {
				Listitem listItem = ltbPedidos.getSelectedItem();
				TabDetalles modelo = listItem.getValue();
				int i = listItem.getIndex();
				ltbPedidos.removeItemAt(i);
				listaDetalle.remove(modelo);
				ltbPedidos.renderAll();
				Double cantidad = txtTotal.getValue();
				if (cantidad == null)
					cantidad = (double) 0;
				txtTotal.setValue(cantidad - modelo.getImpNot());
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);
		} else
			msj.mensajeAlerta(Mensaje.noHayRegistros);
	}

	public byte[] mostrarReporte(String categoria, String tipo, String empresa,
			String nota) {
		String nombre = "Nota de Debito";
		if (tipo.startsWith("C"))
			nombre = "Nota de Credito";
		TabNotasPK id = new TabNotasPK();
		id.setCatNot(categoria);
		id.setIDEmp(Integer.parseInt(empresa));
		id.setIDNot(Integer.parseInt(nota));
		id.setTipNot(tipo);
		TabNotas not = getSNota().buscar(id);
		TabEmpresa empresaOb = getSEmpresa().buscar(not.getId().getIDEmp());
		TabProveedore pro = getSProvedor().buscar(not.getIDPro());
		List<TabDetalles> lista = getSDetalleNota().buscar(id);
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("nombre", nombre);
		p.put("nro", not.getId().getIDNot());
		p.put("fecha", not.getFecNot());
		p.put("categoria", categoria);
		p.put("elaborado", not.getUsuNot());
		String revisado = "N/A";
		String autorizado = "N/A";
		if (!lista.isEmpty()) {
			revisado = lista.get(0).getRevNot();
			autorizado = lista.get(0).getAutNot();
		}
		p.put("revisado", revisado);
		p.put("autorizado", autorizado);
		p.put("cantidad", "CANTIDAD");
		p.put("empresaTelefono", empresaOb.getTelEmp());
		p.put("empresaFax", empresaOb.getFaxEmp());
		p.put("empresaNombre", empresaOb.getNomEmp());
		p.put("empresaDireccion", empresaOb.getDirEmp());
		p.put("empresaRif", empresaOb.getRifEmp());
		p.put("empresaNit", empresaOb.getNitEmp());
		p.put("proveedorNombre", pro.getNomPro());
		p.put("proveedorDireccion", pro.getDirPro());
		p.put("proveedorRif", pro.getRifPro());
		p.put("proveedorNit", pro.getNitPro());
		p.put("dataCopia", new JRBeanCollectionDataSource(lista));
		return generarReporteGenerico(p, lista, "/reporte/RNota.jasper", "PDF");
	}
}
