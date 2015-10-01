package controlador.transacciones;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.maestros.F0005;
import modelo.maestros.TabBanco;
import modelo.maestros.TabCliente;
import modelo.maestros.TabConcepto;
import modelo.maestros.TabConfigur;
import modelo.maestros.TabEmpresa;
import modelo.maestros.TabProveedore;
import modelo.pk.TabConceptosPK;
import modelo.pk.TabGeneralPK;
import modelo.pk.TabNotasPK;
import modelo.transacciones.TabConceptos;
import modelo.transacciones.TabDetalles;
import modelo.transacciones.TabGeneral;
import modelo.transacciones.TabNotas;

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
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.BuscadorUDC;
import componente.Catalogo;
import componente.Convertidor;
import componente.Mensaje;
import controlador.maestros.CGenerico;

public class CRecibo extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Div botoneraRecibo;
	@Wire
	private Div divVRecibo;
	@Wire
	private Div catalogoRecibo;
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
	private Intbox txtCliente;
	@Wire
	private Label lblCliente;
	@Wire
	private Intbox txtBanco;
	@Wire
	private Label lblBanco;
	@Wire
	private Intbox txtConcepto;
	@Wire
	private Label lblConcepto;
	@Wire
	private Div divCatalogoCliente;
	@Wire
	private Div divCatalogoBanco;
	@Wire
	private Div divCatalogoConcepto;
	@Wire
	private Textbox txtObservacion;
	@Wire
	private Div divBuscadorPago;
	BuscadorUDC buscadorPago;
	@Wire
	private Div divBuscadorMoneda;
	BuscadorUDC buscadorMoneda;
	@Wire
	private Textbox txtDoc;
	@Wire
	private Listbox ltbPedidos;
	@Wire
	private Spinner spnCantidad;
	@Wire
	private Doublespinner spnImporte;
	@Wire
	private Doublebox txtTotal;
	Catalogo<TabCliente> catalogoCliente;
	Catalogo<TabBanco> catalogoBanco;
	Catalogo<TabConcepto> catalogoConcepto;
	Botonera botonera;
	Catalogo<TabGeneral> catalogo;
	protected List<TabGeneral> listaGeneral = new ArrayList<TabGeneral>();
	protected List<TabConceptos> listaDetalle = new ArrayList<TabConceptos>();
	private TabGeneralPK clave = null;
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
								TabGeneral not = catalogo
										.objetoSeleccionadoDelCatalogo();
								clave = not.getId();
								TabCliente proveedor = servicioCliente
										.buscar(not.getIDCli());
								txtCliente.setValue(proveedor.getIDCli());
								lblCliente.setValue(proveedor.getNomCli());
								TabBanco banco = servicioBanco.buscar(not
										.getIDBan());
								txtBanco.setValue(banco.getIDBan());
								lblBanco.setValue(banco.getNomBan());
								txtNumero.setValue(clave.getIDRec());
								txtTotal.setValue(not.getTotFac());
								buscadorMoneda.settearCampo(servicioF0005
										.buscar("00", "04", not.getTipMon()));
								buscadorPago.settearCampo(servicioF0005.buscar(
										"00", "03", not.getForPag()));
								txtDoc.setValue(not.getNumChe());
								txtObservacion.setValue(not.getObseRec());
								dtbFecha.setValue(not.getFecRec());
								idEmpresa = clave.getIDEmp();
								TabEmpresa empresa = servicioEmpresa
										.buscar(idEmpresa);
								lblNombre.setValue(empresa.getNomEmp());
								lblDireccion.setValue(empresa.getDirEmp());
								lblFax.setValue(empresa.getFaxEmp());
								lblNit.setValue(empresa.getNitEmp());
								lblRif.setValue(empresa.getRifEmp());
								lblTelefono.setValue(empresa.getTelEmp());

								listaDetalle = servicioDetalleRecibo
										.buscar(clave);
								ltbPedidos
										.setModel(new ListModelList<TabConceptos>(
												listaDetalle));
								ltbPedidos.renderAll();
							} else
								msj.mensajeAlerta(Mensaje.editarSoloUno);
						}
					}

					@Override
					public void salir() {
						cerrarVentana(divVRecibo, cerrar, tabs);
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
							TabGeneral fooo4 = new TabGeneral();
							if (clave == null) {
								clave = new TabGeneralPK();
								calendario.setTime(dtbFecha.getValue());
								clave.setAno((short) calendario
										.get(Calendar.YEAR));
								clave.setIDEmp(idEmpresa);
								clave.setIDRec(servicioRecibo.buscarUltimo(
										(short) calendario.get(Calendar.YEAR),
										idEmpresa) + 1);
							} else
								servicioDetalleRecibo.limpiar(clave);
							fooo4.setId(clave);
							fooo4.setCamBol((double) 0);
							fooo4.setEstRec("Activo");
							fooo4.setFecRec(new Timestamp(dtbFecha.getValue()
									.getTime()));
							fooo4.setForPag(buscadorPago.obtenerCaja());
							fooo4.setHorRec(new Timestamp(dtbFecha.getValue()
									.getTime()));
							fooo4.setIDBan(txtBanco.getValue());
							fooo4.setIDCli(txtCliente.getValue());
							fooo4.setNumChe(txtDoc.getValue());
							fooo4.setObseRec(txtObservacion.getValue());
							fooo4.setTipMon(buscadorMoneda.obtenerCaja());
							fooo4.setTotFac(txtTotal.getValue());
							servicioRecibo.guardar(fooo4);

							TabConceptosPK idDetalle = new TabConceptosPK();
							TabConceptos detalle = new TabConceptos();
							for (int i = 0; i < listaDetalle.size(); i++) {
								idDetalle = new TabConceptosPK();
								idDetalle.setAno(clave.getAno());
								idDetalle.setIDCon(listaDetalle.get(i).getId()
										.getIDCon());
								idDetalle.setIDEmp(clave.getIDEmp());
								idDetalle.setIDRec(clave.getIDRec());

								detalle = new TabConceptos();
								detalle.setCanCon(listaDetalle.get(i)
										.getCanCon());
								detalle.setId(idDetalle);
								detalle.setImpCon(listaDetalle.get(i)
										.getImpCon());
								detalle.setPreCon(listaDetalle.get(i)
										.getImpCon());
								detalle.setTotRec(listaDetalle.get(i)
										.getTotRec());
								servicioDetalleRecibo.guardar(detalle);
							}
							msj.mensajeInformacion(Mensaje.guardado);
							listaGeneral = servicioRecibo
									.buscarTodosOrdenados();
							catalogo.actualizarLista(listaGeneral, true);
							Clients.evalJavaScript("window.open('"
									+ damePath()
									+ "Generador?valor=2&valor2="
									+ clave.getAno()
									+ "&valor3="
									+ clave.getIDEmp()
									+ "&valor4="
									+ clave.getIDRec()
									+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");
						}

					}

					@Override
					public void eliminar() {
						if (gpxDatos.isOpen()) {
							/* Elimina Varios Registros */
							if (validarSeleccion()) {
								final List<TabGeneral> eliminarLista = catalogo
										.obtenerSeleccionados();
								boolean error = false;
								for (int i = 0; i < eliminarLista.size(); i++) {
									if (eliminarLista.get(i).getEstRec()
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
																	TabGeneral objeto = eliminarLista
																			.get(i);
																	objeto.setEstRec("Eliminado");
																	servicioRecibo
																			.guardar(objeto);
																}
																msj.mensajeInformacion(Mensaje.eliminado);
																listaGeneral = servicioRecibo
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
								TabGeneral objeto = servicioRecibo
										.buscar(clave);
								if (!objeto.getEstRec().equals("Eliminado"))
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
																objeto.setEstRec("Eliminado");
																servicioRecibo
																		.guardar(objeto);
																msj.mensajeInformacion(Mensaje.eliminado);
																limpiar();
																listaGeneral = servicioRecibo
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
				botoneraRecibo.appendChild(botonera);
			} else {
				msj.mensajeAlerta("No se pueden Realizar recibos sin previamente configurar los datos de la Empresa, contacte al Administrador");
				cerrarVentana(divVRecibo, cerrar, tabs);
			}
		} else {
			msj.mensajeAlerta("No se pueden Realizar recibos sin previamente configurar los datos de la Empresa, contacte al Administrador");
			cerrarVentana(divVRecibo, cerrar, tabs);
		}

	}

	private void cargarBuscadores() {
		List<F0005> listF0005 = servicioF0005
				.buscarParaUDCOrdenados("00", "03");
		buscadorPago = new BuscadorUDC("Forma de Pago", 100, listF0005, true,
				false, false, "00", "03", "23%", "23%", "7%", "23%") {
			private static final long serialVersionUID = 1L;

			@Override
			protected F0005 buscar() {
				return servicioF0005.buscar("00", "03",
						buscadorPago.obtenerCaja());
			}
		};
		divBuscadorPago.appendChild(buscadorPago);

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
		txtBanco.setValue(null);
		txtCliente.setValue(null);
		txtDoc.setValue("");
		txtObservacion.setValue("");
		clave = null;
		txtTotal.setValue(null);
		txtNumero.setValue(null);
		lblBanco.setValue("");
		lblCliente.setValue("");
		buscadorPago.settearCampo(null);
		buscadorMoneda.settearCampo(null);
		listaDetalle.clear();
		ltbPedidos.getItems().clear();
		limpiarCamposItem();
	}

	public boolean validarSeleccion() {
		List<TabGeneral> seleccionados = catalogo.obtenerSeleccionados();
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
		if (txtBanco.getText().compareTo("") == 0
				|| buscadorPago.obtenerCaja().compareTo("") == 0
				|| buscadorMoneda.obtenerCaja().compareTo("") == 0
				|| txtDoc.getText().compareTo("") == 0
				|| txtCliente.getText().compareTo("") == 0) {
			return false;
		} else
			return true;
	}

	public boolean camposEditando() {
		if (txtBanco.getText().compareTo("") != 0
				|| buscadorPago.obtenerCaja().compareTo("") != 0
				|| buscadorMoneda.obtenerCaja().compareTo("") != 0
				|| txtDoc.getText().compareTo("") != 0
				|| txtCliente.getText().compareTo("") != 0
				|| txtObservacion.getText().compareTo("") != 0) {
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
		listaGeneral = servicioRecibo.buscarTodosOrdenados();
		catalogo = new Catalogo<TabGeneral>(catalogoRecibo, "Recibos",
				listaGeneral, false, false, false, "Fecha", "Empresa",
				"Observacion", "Estado") {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			protected List<TabGeneral> buscar(List<String> valores) {

				List<TabGeneral> lista = new ArrayList<TabGeneral>();

				for (TabGeneral f0004 : listaGeneral) {
					if (Convertidor.formatoFecha.format(f0004.getFecRec())
							.toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& f0004.getObseRec().contains(
									valores.get(2).toLowerCase())
							&& f0004.getEstRec().toLowerCase()
									.contains(valores.get(3).toLowerCase())) {
						lista.add(f0004);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(TabGeneral f0004) {
				String empresa = "";
				if (f0004.getId().getIDEmp() != null) {
					TabEmpresa empresa1 = servicioEmpresa.buscar(f0004.getId()
							.getIDEmp());
					empresa = empresa1.getNomEmp();
				}
				String[] registros = new String[4];
				registros[0] = Convertidor.formatoFecha.format(f0004
						.getFecRec());
				registros[1] = empresa;
				registros[2] = f0004.getObseRec();
				registros[3] = f0004.getEstRec();
				return registros;
			}
		};
		catalogo.setParent(catalogoRecibo);
	}

	@Listen("onClick = #btnBuscarCliente")
	public void mostrarCatalogoCliente(Event evento) {
		final List<TabCliente> listF0004 = servicioCliente
				.buscarTodosOrdenados();
		catalogoCliente = new Catalogo<TabCliente>(divCatalogoCliente,
				"Catalogo de Clientes", listF0004, true, false, false,
				"Descripcion") {

			@Override
			protected List<TabCliente> buscar(List<String> valores) {

				List<TabCliente> lista = new ArrayList<TabCliente>();

				for (TabCliente f0004 : listF0004) {
					if (f0004.getNomCli().toLowerCase()
							.contains(valores.get(0).toLowerCase())) {
						lista.add(f0004);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(TabCliente f0004) {
				String[] registros = new String[1];
				registros[0] = f0004.getNomCli();
				return registros;
			}
		};
		catalogoCliente.setClosable(true);
		catalogoCliente.setWidth("80%");
		catalogoCliente.setParent(divCatalogoCliente);
		catalogoCliente.doModal();
	}

	@Listen("onSeleccion = #divCatalogoCliente")
	public void seleccionCliente() {
		TabCliente f0004 = catalogoCliente.objetoSeleccionadoDelCatalogo();
		txtCliente.setValue(f0004.getIDCli());
		lblCliente.setValue(f0004.getNomCli());
		catalogoCliente.setParent(null);
	}

	@Listen("onClick = #btnBuscarBanco")
	public void mostrarCatalogoBanco(Event evento) {
		final List<TabBanco> listF0004 = servicioBanco.buscarTodosOrdenados();
		catalogoBanco = new Catalogo<TabBanco>(divCatalogoBanco,
				"Catalogo de Bancos", listF0004, false, false, false,
				"Descripcion") {

			@Override
			protected List<TabBanco> buscar(List<String> valores) {

				List<TabBanco> lista = new ArrayList<TabBanco>();

				for (TabBanco f0004 : listF0004) {
					if (f0004.getNomBan().toLowerCase()
							.contains(valores.get(0).toLowerCase())) {
						lista.add(f0004);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(TabBanco f0004) {
				String[] registros = new String[1];
				registros[0] = f0004.getNomBan();
				return registros;
			}
		};
		catalogoBanco.setClosable(true);
		catalogoBanco.setWidth("80%");
		catalogoBanco.setParent(divCatalogoBanco);
		catalogoBanco.doModal();
	}

	@Listen("onSeleccion = #divCatalogoBanco")
	public void seleccionBanco() {
		TabBanco f0004 = catalogoBanco.objetoSeleccionadoDelCatalogo();
		txtBanco.setValue(f0004.getIDBan());
		lblBanco.setValue(f0004.getNomBan());
		catalogoBanco.setParent(null);
	}

	@Listen("onClick = #btnBuscarConcepto")
	public void mostrarCatalogoConcepto(Event evento) {
		final List<TabConcepto> listF0004 = servicioConcepto
				.buscarTodosOrdenados();
		catalogoConcepto = new Catalogo<TabConcepto>(divCatalogoConcepto,
				"Catalogo de Conceptos", listF0004, false, false, false,
				"Descripcion") {

			@Override
			protected List<TabConcepto> buscar(List<String> valores) {

				List<TabConcepto> lista = new ArrayList<TabConcepto>();

				for (TabConcepto f0004 : listF0004) {
					if (f0004.getDesCon().toLowerCase()
							.contains(valores.get(0).toLowerCase())) {
						lista.add(f0004);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(TabConcepto f0004) {
				String[] registros = new String[1];
				registros[0] = f0004.getDesCon();
				return registros;
			}
		};
		catalogoConcepto.setClosable(true);
		catalogoConcepto.setWidth("80%");
		catalogoConcepto.setParent(divCatalogoConcepto);
		catalogoConcepto.doModal();
	}

	@Listen("onSeleccion = #divCatalogoConcepto")
	public void seleccion() {
		TabConcepto f0004 = catalogoConcepto.objetoSeleccionadoDelCatalogo();
		txtConcepto.setValue(f0004.getIDCon());
		lblConcepto.setValue(f0004.getDesCon());
		catalogoConcepto.setParent(null);
	}

	private void limpiarCamposItem() {
		txtConcepto.setValue(null);
		lblConcepto.setValue("");
		spnCantidad.setValue(0);
		spnImporte.setValue((double) 0);
	}

	private boolean validarItems() {
		if (txtConcepto.getText().compareTo("") == 0
				|| spnCantidad.getValue() == 0 || spnImporte.getValue() == 0)
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
			txtTotal.setValue(cantidad + spnImporte.getValue()
					* spnCantidad.getValue());
			TabConceptosPK idObject = new TabConceptosPK();
			idObject.setIDCon(txtConcepto.getValue());
			TabConceptos object = new TabConceptos();
			object.setId(idObject);
			object.setCanCon(spnCantidad.getValue());
			object.setImpCon(spnImporte.getValue());
			object.setPreCon(spnImporte.getValue());
			object.setTotRec(spnImporte.getValue() * spnCantidad.getValue());
			boolean error = false;
			for (int i = 0; i < ltbPedidos.getItemCount(); i++) {
				Listitem listItem = ltbPedidos.getItemAtIndex(i);
				TabConceptos modeloLista = listItem.getValue();
				if (modeloLista.getId().getIDCon()
						.equals(txtConcepto.getValue())) {
					error = true;
				}
			}
			if (!error) {
				listaDetalle.add(object);
				ltbPedidos.setModel(new ListModelList<TabConceptos>(
						listaDetalle));
				ltbPedidos.renderAll();
				limpiarCamposItem();
			} else
				msj.mensajeError(Mensaje.itemRepetido);
		} else
			msj.mensajeError(Mensaje.camposVaciosItem);
	}

	@Listen("onClick = #btnVer")
	public void seleccionarItemLista() {
		if (ltbPedidos.getItemCount() != 0) {
			if (ltbPedidos.getSelectedItems().size() == 1) {
				Listitem listItem = ltbPedidos.getSelectedItem();
				TabConceptos modelo = listItem.getValue();
				spnImporte.setValue(modelo.getImpCon());
				spnCantidad.setValue(modelo.getCanCon());
				txtConcepto.setValue(modelo.getId().getIDCon());
				lblConcepto.setValue(modelo.getId().nombreConcepto());
				ltbPedidos.removeItemAt(listItem.getIndex());
				listaDetalle.remove(modelo);
				ltbPedidos.renderAll();
				Double cantidad = txtTotal.getValue();
				if (cantidad == null)
					cantidad = (double) 0;
				txtTotal.setValue(cantidad - modelo.getTotRec());
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
				TabConceptos modelo = listItem.getValue();
				int i = listItem.getIndex();
				ltbPedidos.removeItemAt(i);
				listaDetalle.remove(modelo);
				ltbPedidos.renderAll();
				Double cantidad = txtTotal.getValue();
				if (cantidad == null)
					cantidad = (double) 0;
				txtTotal.setValue(cantidad - modelo.getTotRec());
			} else
				msj.mensajeAlerta(Mensaje.editarSoloUno);
		} else
			msj.mensajeAlerta(Mensaje.noHayRegistros);
	}

	public byte[] mostrarReporte(String ano, String empresa, String recibo) {
		TabGeneralPK id = new TabGeneralPK();
		id.setAno((short) Integer.parseInt(ano));
		id.setIDEmp(Integer.parseInt(empresa));
		id.setIDRec(Integer.parseInt(recibo));
		TabGeneral not = getSRecibo().buscar(id);
		TabCliente clienteOb = getSCliente().buscar(not.getIDCli());
		TabBanco bancoOb = getSBanco().buscar(not.getIDBan());
		TabEmpresa empresaOb = getSEmpresa().buscar(not.getId().getIDEmp());
		List<TabConceptos> lista = getSDetalleRecibo().buscar(id);
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("nro", not.getId().getIDRec());
		p.put("fecha", not.getFecRec());
		p.put("elaborado", nombreUsuarioSesion());
		p.put("cantidad", "CANTIDAD");
		p.put("empresaNombre", empresaOb.getNomEmp());
		p.put("empresaTelefono", empresaOb.getTelEmp());
		p.put("empresaFax", empresaOb.getFaxEmp());
		p.put("empresaDireccion", empresaOb.getDirEmp());
		p.put("empresaRif", empresaOb.getRifEmp());
		p.put("empresaNit", empresaOb.getNitEmp());
		p.put("cliente", clienteOb.getNomCli());
		p.put("observacion", not.getObseRec());
		p.put("moneda", not.getTipMon());
		p.put("pago", not.getForPag());
		p.put("banco", bancoOb.getNomBan());
		p.put("valor", not.getCamBol());
		p.put("referencia", "0");
		return generarReporteGenerico(p, lista, "/reporte/RRecibo.jasper",
				"PDF");
	}

}
