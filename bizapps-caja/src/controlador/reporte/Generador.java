package controlador.reporte;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import controlador.transacciones.CNota;
import controlador.transacciones.CRecibo;

/**
 * Servlet implementation class Reportero
 */
@WebServlet("/Generador")
public class Generador extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Generador() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		CRecibo recibos = new CRecibo();
		CNota notas = new CNota();
		ServletOutputStream out;
		String par1 = request.getParameter("valor");
		String par2 = request.getParameter("valor2");
		String par3 = request.getParameter("valor3");
		String par4 = request.getParameter("valor4");
		String par5 = request.getParameter("valor5");
		byte[] fichero = null;
			switch (par1) {
			case "1":
				fichero = notas.mostrarReporte(par2, par3, par4, par5);
				break;
			case "2":
				fichero = recibos.mostrarReporte(par2, par3, par4);
				break;
			default:
				break;
			}
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition",
				"inline; filename=Reporte.pdf");
		response.setHeader("Cache-Control", "max-age=30");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentLength(fichero.length);
		out = response.getOutputStream();
		out.write(fichero, 0, fichero.length);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
