package controlador.maestros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modelo.maestros.F0004;
import modelo.maestros.F0005;
import modelo.maestros.TabConfigur;
import modelo.maestros.TabEmpresa;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;

import componente.Botonera;
import componente.Catalogo;
import componente.Mensaje;

public class CConfiguracion extends CGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Div botoneraConfiguracion;
	@Wire
	private Div divConfiguracion;
	@Wire
	private Textbox txtAutoriza;
	@Wire
	private Textbox txtRevisa;
	@Wire
	private Intbox txtEmpresa;
	@Wire
	private Label lblEmpresa;
	@Wire
	private Div divCatalogoEmpresa;
	Catalogo<TabEmpresa> catalogo;

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
		actualizarEstado();
		Botonera botonera = new Botonera() {

			@Override
			public void seleccionar() {
				// TODO Auto-generated method stub

			}

			@Override
			public void salir() {
				cerrarVentana(divConfiguracion, cerrar, tabs);
			}

			@Override
			public void reporte() {
				// TODO Auto-generated method stub

			}

			@Override
			public void limpiar() {
				txtAutoriza.setValue("");
				txtRevisa.setValue("");
				txtEmpresa.setValue(null);
				lblEmpresa.setValue("");
			}

			@Override
			public void guardar() {
				if (validar()) {
					TabConfigur confi = new TabConfigur();
					confi.setId(1);
					confi.setAutoriza(txtAutoriza.getValue());
					confi.setRevisa(txtRevisa.getValue());
					confi.setIdEmp(txtEmpresa.getValue());
					servicioConfiguracion.guardar(confi);
					msj.mensajeInformacion(Mensaje.guardado);
					actualizarEstado();
				}
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
		botonera.getChildren().get(5).setVisible(false);
		botonera.getChildren().get(2).setVisible(false);
		botonera.getChildren().get(4).setVisible(false);
		botonera.getChildren().get(6).setVisible(false);
		botonera.getChildren().get(8).setVisible(false);
		botoneraConfiguracion.appendChild(botonera);
	}

	protected boolean validar() {
		if (!camposLLenos()) {
			msj.mensajeError(Mensaje.camposVacios);
			return false;
		} else
			return true;
	}

	private boolean camposLLenos() {
		if (txtAutoriza.getText().compareTo("") == 0
				|| txtEmpresa.getText().compareTo("") == 0
				|| txtRevisa.getText().compareTo("") == 0) {
			return false;
		} else
			return true;
	}

	protected void actualizarEstado() {
		TabConfigur actual = servicioConfiguracion.buscar(1);
		if (actual != null) {
			txtAutoriza.setValue(actual.getAutoriza());
			txtRevisa.setValue(actual.getRevisa());
			TabEmpresa empresa = servicioEmpresa.buscar(actual.getIdEmp());
			txtEmpresa.setValue(actual.getIdEmp());
			if (empresa != null)
				lblEmpresa.setValue(empresa.getNomEmp());
		}
	}

	@Listen("onClick = #btnBuscarEmpresa")
	public void mostrarCatalogoF0004(Event evento) {
		final List<TabEmpresa> listF0004 = servicioEmpresa
				.buscarTodosOrdenados();
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

}
