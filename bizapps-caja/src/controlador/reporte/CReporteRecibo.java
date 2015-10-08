package controlador.reporte;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.maestros.TabCliente;
import modelo.maestros.TabConcepto;
import modelo.maestros.TabEmpresa;
import modelo.maestros.TabProveedore;
import modelo.transacciones.TabNotas;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import security.modelo.UsuarioSeguridad;
import componente.Botonera;
import componente.BuscadorUDC;
import componente.Catalogo;
import componente.Convertidor;
import componente.Mensaje;
import controlador.maestros.CGenerico;

public class CReporteRecibo extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Div botoneraReporteRecibo;
	@Wire
	private Div divVReporteRecibo;
	@Wire
	private Intbox txtEmpresa;
	@Wire
	private Label lblEmpresa;
	@Wire
	private Div divCatalogoEmpresa;
	@Wire
	private Intbox txtCliente;
	@Wire
	private Label lblCliente;
	@Wire
	private Div divCatalogoCliente;
	@Wire
	private Intbox txtConcepto;
	@Wire
	private Label lblConcepto;
	@Wire
	private Div divCatalogoConcepto;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Datebox dtbHasta;
	Catalogo<TabEmpresa> catalogo;
	Catalogo<TabCliente> catalogoCliente;
	Catalogo<TabConcepto> catalogoConcepto;
	Botonera botonera;

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
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void salir() {
				cerrarVentana(divVReporteRecibo, cerrar, tabs);
			}

			@Override
			public void reporte() {
				Timestamp desde = new Timestamp(dtbDesde.getValue().getTime());
				Timestamp hasta = new Timestamp(agregarDia(dtbHasta.getValue())
						.getTime());
				String empresa = "%";
				if (txtEmpresa.getValue() != 0)
					empresa = String.valueOf(txtEmpresa.getValue());
				String cliente = "%";
				if (txtCliente.getValue() != 0)
					cliente = String.valueOf(txtCliente.getValue());
				String concepto = "%";
				if (txtConcepto.getValue() != 0)
					concepto = String.valueOf(txtConcepto.getValue());
				List<Object[]> lista = getSDetalleRecibo().buscarPorParametros(
						desde, hasta, empresa, cliente, concepto);
				if (!lista.isEmpty()) {
					String fecha11 = Convertidor.formatoFecha.format(desde);
					String fecha22 = Convertidor.formatoFecha.format(hasta);
					if (empresa.equals("%"))
						empresa = "0";
					if (cliente.equals("%"))
						cliente = "0";
					if (concepto.equals("%"))
						concepto = "0";
					Clients.evalJavaScript("window.open('"
							+ damePath()
							+ "Generador?valor=4"
							+ "&valor2="
							+ fecha11
							+ "&valor3="
							+ fecha22
							+ "&valor4="
							+ empresa
							+ "&valor5="
							+ cliente
							+ "&valor6="
							+ concepto
							+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");

				} else
					msj.mensajeAlerta(Mensaje.noHayRegistros);
			}

			@Override
			public void limpiar() {
				txtCliente.setValue(0);
				txtEmpresa.setValue(0);
				txtConcepto.setValue(0);
				lblEmpresa.setValue("TODOS");
				lblCliente.setValue("TODOS");
				lblConcepto.setValue("TODOS");
				dtbHasta.setValue(fecha);
				dtbDesde.setValue(fecha);
			}

			@Override
			public void guardar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void eliminar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void buscar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void ayuda() {
				// TODO Auto-generated method stub

			}

			@Override
			public void annadir() {
				// TODO Auto-generated method stub

			}
		};
		botonera.getChildren().get(0).setVisible(false);
		botonera.getChildren().get(1).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(3).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(6).setVisible(false);
		Button limpiar = (Button) botonera.getChildren().get(5);
		Button reporte = (Button) botonera.getChildren().get(8);
		Button salir = (Button) botonera.getChildren().get(7);
		botonera.getChildren().get(5).removeChild(limpiar);
		botonera.getChildren().get(6).removeChild(reporte);
		botonera.getChildren().get(7).removeChild(salir);
		botonera.appendChild(reporte);
		botonera.appendChild(limpiar);
		botonera.appendChild(salir);
		botoneraReporteRecibo.appendChild(botonera);

	}

	@Listen("onClick = #btnBuscarEmpresa")
	public void mostrarCatalogoF0004(Event evento) {
		List<TabEmpresa> listF0004 = servicioEmpresa.buscarTodosOrdenados();
		TabEmpresa concepto = new TabEmpresa();
		concepto.setIDEmp(0);
		concepto.setNomEmp("TODAS");
		concepto.setRifEmp("TODAS");
		concepto.setDirEmp("TODAS");
		concepto.setTelEmp("TODAS");
		listF0004.add(0, concepto);
		catalogo = new Catalogo<TabEmpresa>(divCatalogoEmpresa,
				"Catalogo de Empresas", listF0004, true, false, false,
				"Nombre", "RIF", "Direccion", "Telefono") {

			@Override
			protected List<TabEmpresa> buscar(List<String> valores) {

				List<TabEmpresa> lista = new ArrayList<TabEmpresa>();

				for (TabEmpresa f0004 : listF0004) {
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
		catalogo.setClosable(true);
		catalogo.setWidth("80%");
		catalogo.setParent(divCatalogoEmpresa);
		catalogo.doModal();
	}

	@Listen("onSeleccion = #divCatalogoEmpresa")
	public void seleccion() {
		TabEmpresa f0004 = catalogo.objetoSeleccionadoDelCatalogo();
		txtEmpresa.setValue(f0004.getIDEmp());
		lblEmpresa.setValue(f0004.getNomEmp());
		catalogo.setParent(null);
	}

	@Listen("onClick = #btnBuscarConcepto")
	public void mostrarCatalogoConcepto(Event evento) {
		final List<TabConcepto> listF0004 = servicioConcepto
				.buscarTodosOrdenados();
		TabConcepto concepto = new TabConcepto();
		concepto.setIDCon(0);
		concepto.setDesCon("TODOS");
		listF0004.add(0, concepto);
		catalogoConcepto = new Catalogo<TabConcepto>(divCatalogoConcepto,
				"Catalogo de Conceptos", listF0004, true, false, false,
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
	public void seleccionConcepto() {
		TabConcepto f0004 = catalogoConcepto.objetoSeleccionadoDelCatalogo();
		txtConcepto.setValue(f0004.getIDCon());
		lblConcepto.setValue(f0004.getDesCon());
		catalogoConcepto.setParent(null);
	}

	@Listen("onClick = #btnBuscarCliente")
	public void mostrarCatalogoCliente(Event evento) {
		TabCliente cliente = new TabCliente();
		cliente.setIDCli(0);
		cliente.setNomCli("TODOS");
		List<TabCliente> listF0004 = servicioCliente.buscarTodosOrdenados();
		listF0004.add(0, cliente);
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

	public byte[] mostrarReporte(String desde, String hasta, String empresa,
			String cliente, String concepto) {
		if (empresa.equals("0"))
			empresa = "%";
		if (cliente.equals("0"))
			cliente = "%";
		if (concepto.equals("0"))
			concepto = "%";
		List<Object[]> aux = new ArrayList<Object[]>();
		try {
			aux = getSDetalleRecibo().buscarPorParametros(
					new Timestamp(Convertidor.formatoFecha.parse(desde)
							.getTime()),
					new Timestamp(Convertidor.formatoFecha.parse(hasta)
							.getTime()), empresa, cliente, concepto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (empresa.equals("%"))
			empresa = "TODAS";
		else
			empresa = getSEmpresa().buscar(Integer.parseInt(empresa))
					.getNomEmp();
		if (cliente.equals("%"))
			cliente = "TODOS";
		else
			cliente = getSCliente().buscar(Integer.parseInt(cliente))
					.getNomCli();
		if (concepto.equals("%"))
			concepto = "TODOS";
		else
			concepto = getSConcepto().buscar(Integer.parseInt(concepto))
					.getDesCon();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", desde);
		p.put("hasta", hasta);
		p.put("empresa", empresa);
		p.put("cliente", cliente);
		p.put("concepto", concepto);
		return generarReporteGenerico(p, aux, "/reporte/RReporteRecibo.jasper",
				"PDF");
	}

}
