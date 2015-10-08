package controlador.reporte;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.maestros.F0005;
import modelo.maestros.TabEmpresa;
import modelo.maestros.TabProveedore;
import modelo.transacciones.TabDetalles;
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

public class CReporteNota extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Div botoneraReporteNota;
	@Wire
	private Div divVReporteNota;
	// @Wire
	// private Intbox txtEmpresa;
	// @Wire
	// private Label lblEmpresa;
	@Wire
	private Div divCatalogoEmpresa;
	@Wire
	private Intbox txtProveedor;
	@Wire
	private Label lblProveedor;
	@Wire
	private Div divCatalogoProveedor;
	@Wire
	private Textbox txtUsuario;
	@Wire
	private Label lblUsuario;
	@Wire
	private Div divCatalogoUsuario;
	@Wire
	private Div divBuscadorTipo;
	@Wire
	private Div divBuscadorClase;
	@Wire
	private Datebox dtbDesde;
	@Wire
	private Datebox dtbHasta;
	Catalogo<TabEmpresa> catalogo;
	Catalogo<UsuarioSeguridad> catalogoUsuario;
	Catalogo<TabProveedore> catalogoProveedor;
	BuscadorUDC buscadorTipo;
	BuscadorUDC buscadorClase;
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
		cargarBuscadores();
		botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void salir() {
				cerrarVentana(divVReporteNota, cerrar, tabs);
			}

			@Override
			public void reporte() {
				Timestamp desde = new Timestamp(dtbDesde.getValue().getTime());
				Timestamp hasta = new Timestamp(agregarDia(dtbHasta.getValue())
						.getTime());
				String empresa = "%";
				// if (txtEmpresa.getValue() != 0)
				// empresa = String.valueOf(txtEmpresa.getValue());
				String proveedor = "%";
				if (txtProveedor.getValue() != 0)
					proveedor = String.valueOf(txtProveedor.getValue());
				String usuario = "%";
				if (!txtUsuario.getValue().equals("0"))
					usuario = String.valueOf(txtUsuario.getValue());
				String clase = "%";
				if (buscadorClase.obtenerCaja().compareTo("") != 0)
					clase = buscadorClase.obtenerCaja();
				String tipo = "%";
				if (buscadorTipo.obtenerCaja().compareTo("") != 0)
					tipo = buscadorTipo.obtenerCaja();
				List<Object[]> lista = getSDetalleNota().findByParameters(
						desde, hasta, empresa, proveedor, usuario, clase, tipo);
				if (!lista.isEmpty()) {
					String fecha11 = Convertidor.formatoFecha.format(desde);
					String fecha22 = Convertidor.formatoFecha.format(hasta);
					if (empresa.equals("%"))
						empresa = "0";
					if (proveedor.equals("%"))
						proveedor = "0";
					if (usuario.equals("%"))
						usuario = "0";
					if (clase.equals("%"))
						clase = "0";
					if (tipo.equals("%"))
						tipo = "0";
					Clients.evalJavaScript("window.open('"
							+ damePath()
							+ "Generador?valor=3"
							+ "&valor2="
							+ fecha11
							+ "&valor3="
							+ fecha22
							+ "&valor4="
							+ empresa
							+ "&valor5="
							+ proveedor
							+ "&valor6="
							+ usuario
							+ "&valor7="
							+ clase
							+ "&valor8="
							+ tipo
							+ "','','top=100,left=200,height=600,width=800,scrollbars=1,resizable=1')");

				} else
					msj.mensajeAlerta(Mensaje.noHayRegistros);
			}

			@Override
			public void limpiar() {
				// txtEmpresa.setValue(0);
				txtProveedor.setValue(0);
				txtUsuario.setValue("0");
				dtbHasta.setValue(fecha);
				dtbDesde.setValue(fecha);
				lblProveedor.setValue("TODOS");
				// lblEmpresa.setValue("TODOS");
				lblUsuario.setValue("TODOS");
				buscadorClase.settearCampo(null);
				buscadorTipo.settearCampo(null);
			}

			@Override
			public void guardar() {
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
		botoneraReporteNota.appendChild(botonera);
	}

	private void cargarBuscadores() {
		List<F0005> listF0005 = servicioF0005
				.buscarParaUDCOrdenados("00", "02");
		buscadorClase = new BuscadorUDC("Clase", 100, listF0005, true, false,
				false, "00", "02", "24%", "24%", "7%", "34%") {
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
				false, "00", "05", "24%", "24%", "7%", "34%") {
			private static final long serialVersionUID = 1L;

			@Override
			protected F0005 buscar() {
				return servicioF0005.buscar("00", "05",
						buscadorTipo.obtenerCaja());
			}
		};
		divBuscadorTipo.appendChild(buscadorTipo);
	}

	// @Listen("onClick = #btnBuscarEmpresa")
	// public void mostrarCatalogoF0004(Event evento) {
	// final List<TabEmpresa> listF0004 = servicioEmpresa
	// .buscarTodosOrdenados();
	// catalogo = new Catalogo<TabEmpresa>(divCatalogoEmpresa,
	// "Catalogo de Empresas", listF0004, true, false, false,
	// "Nombre", "RIF", "Direccion", "Telefono") {
	//
	// @Override
	// protected List<TabEmpresa> buscar(List<String> valores) {
	//
	// List<TabEmpresa> lista = new ArrayList<TabEmpresa>();
	//
	// for (TabEmpresa f0004 : listF0004) {
	// if (f0004.getNomEmp().toLowerCase()
	// .contains(valores.get(0).toLowerCase())
	// && f0004.getRifEmp().toLowerCase()
	// .contains(valores.get(1).toLowerCase())
	// && f0004.getDirEmp().toLowerCase()
	// .contains(valores.get(2).toLowerCase())
	// && f0004.getTelEmp().toLowerCase()
	// .contains(valores.get(3).toLowerCase())) {
	// lista.add(f0004);
	// }
	// }
	// return lista;
	// }
	//
	// @Override
	// protected String[] crearRegistros(TabEmpresa f0004) {
	// String[] registros = new String[4];
	// registros[0] = f0004.getNomEmp();
	// registros[1] = f0004.getRifEmp();
	// registros[2] = f0004.getDirEmp();
	// registros[3] = f0004.getTelEmp();
	// return registros;
	// }
	// };
	// catalogo.setClosable(true);
	// catalogo.setWidth("80%");
	// catalogo.setParent(divCatalogoEmpresa);
	// catalogo.doModal();
	// }
	//
	// @Listen("onSeleccion = #divCatalogoEmpresa")
	// public void seleccion() {
	// TabEmpresa f0004 = catalogo.objetoSeleccionadoDelCatalogo();
	// txtEmpresa.setValue(f0004.getIDEmp());
	// lblEmpresa.setValue(f0004.getNomEmp());
	// catalogo.setParent(null);
	// }

	@Listen("onClick = #btnBuscarProveedor")
	public void mostrarCatalogoProveedor(Event evento) {
		List<TabProveedore> listF0004 = servicioProveedor
				.buscarTodosOrdenados();
		TabProveedore concepto = new TabProveedore();
		concepto.setIDPro(0);
		concepto.setNomPro("TODOS");
		concepto.setRifPro("TODOS");
		concepto.setDirPro("TODOS");
		concepto.setTelPro("TODOS");
		listF0004.add(0, concepto);
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
	public void seleccionProveedor() {
		TabProveedore f0004 = catalogoProveedor.objetoSeleccionadoDelCatalogo();
		txtProveedor.setValue(f0004.getIDPro());
		lblProveedor.setValue(f0004.getNomPro());
		catalogoProveedor.setParent(null);
	}

	@Listen("onClick = #btnBuscarUsuario")
	public void mostrarCatalogoUsuario(Event evento) {
		List<UsuarioSeguridad> listF0004 = servicioUsuarioSeguridad
				.buscarTodos();
		UsuarioSeguridad concepto = new UsuarioSeguridad();
		concepto.setLogin("0");
		concepto.setNombre("TODOS");
		concepto.setApellido("TODOS");
		listF0004.add(0, concepto);
		catalogoUsuario = new Catalogo<UsuarioSeguridad>(divCatalogoUsuario,
				"Catalogo de Proveedores", listF0004, true, false, false,
				"Codigo", "Nombre", "Apellido") {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			protected List<UsuarioSeguridad> buscar(List<String> valores) {

				List<UsuarioSeguridad> lista = new ArrayList<UsuarioSeguridad>();

				for (UsuarioSeguridad f0004 : listF0004) {
					String rif = "", dir = "";
					if (f0004.getNombre() != null)
						rif = f0004.getNombre();
					if (f0004.getApellido() != null)
						dir = f0004.getApellido();
					if (f0004.getLogin().toLowerCase()
							.contains(valores.get(0).toLowerCase())
							&& rif.toLowerCase().contains(
									valores.get(1).toLowerCase())
							&& dir.toLowerCase().contains(
									valores.get(2).toLowerCase())) {
						lista.add(f0004);
					}
				}
				return lista;
			}

			@Override
			protected String[] crearRegistros(UsuarioSeguridad f0004) {
				String[] registros = new String[3];
				registros[0] = f0004.getLogin();
				registros[1] = f0004.getNombre();
				registros[2] = f0004.getApellido();
				return registros;
			}
		};
		catalogoUsuario.setClosable(true);
		catalogoUsuario.setWidth("80%");
		catalogoUsuario.setParent(divCatalogoUsuario);
		catalogoUsuario.doModal();
	}

	@Listen("onSeleccion = #divCatalogoUsuario")
	public void seleccionUsuario() {
		UsuarioSeguridad f0004 = catalogoUsuario
				.objetoSeleccionadoDelCatalogo();
		txtUsuario.setValue(f0004.getLogin());
		lblUsuario.setValue(f0004.getNombre() + " " + f0004.getApellido());
		catalogoUsuario.setParent(null);
	}

	public byte[] mostrarReporte(String desde, String hasta, String empresa,
			String proveedor, String usuario, String clase, String tipo) {
		if (empresa.equals("0"))
			empresa = "%";
		if (proveedor.equals("0"))
			proveedor = "%";
		if (usuario.equals("0"))
			usuario = "%";
		if (clase.equals("0"))
			clase = "%";
		if (tipo.equals("0"))
			tipo = "%";
		List<Object[]> aux = new ArrayList<Object[]>();
		try {
			aux = getSDetalleNota().findByParameters(
					new Timestamp(Convertidor.formatoFecha.parse(desde)
							.getTime()),
					new Timestamp(Convertidor.formatoFecha.parse(hasta)
							.getTime()), empresa, proveedor, usuario, clase,
					tipo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (empresa.equals("%"))
			empresa = "TODAS";
		if (proveedor.equals("%"))
			proveedor = "TODAS";
		else
			proveedor = getSProvedor().buscar(Integer.parseInt(proveedor))
					.getNomPro();
		if (usuario.equals("%"))
			usuario = "TODOS";
		if (clase.equals("%"))
			clase = "TODAS";
		if (tipo.equals("%"))
			tipo = "TODAS";
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("desde", desde);
		p.put("hasta", hasta);
		p.put("empresa", empresa);
		p.put("proveedor", proveedor);
		p.put("usuario", usuario);
		p.put("categoria", clase);
		p.put("tipo", tipo);
		return generarReporteGenerico(p, aux, "/reporte/RReporteNota.jasper",
				"PDF");
	}
}
