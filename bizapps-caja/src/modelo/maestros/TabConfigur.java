package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the tabConfigur database table.
 * 
 */
@Entity
@Table(name = "tabConfiguracion")
@NamedQuery(name = "TabConfigur.findAll", query = "SELECT t FROM TabConfigur t")
public class TabConfigur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "IDEmp")
	private Integer idEmp;

	@Column(name = "autoriza")
	private String autoriza;

	@Column(name = "revisa")
	private String revisa;

	public TabConfigur() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAutoriza() {
		return autoriza;
	}

	public void setAutoriza(String autoriza) {
		this.autoriza = autoriza;
	}

	public String getRevisa() {
		return revisa;
	}

	public void setRevisa(String revisa) {
		this.revisa = revisa;
	}

	public Integer getIdEmp() {
		return idEmp;
	}

	public void setIdEmp(Integer idEmp) {
		this.idEmp = idEmp;
	}

}