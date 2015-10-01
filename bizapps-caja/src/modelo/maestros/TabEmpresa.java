package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tabEmpresa database table.
 * 
 */
@Entity
@Table(name="tabEmpresa")
@NamedQuery(name="TabEmpresa.findAll", query="SELECT t FROM TabEmpresa t")
public class TabEmpresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int IDEmp;

	@Column(name="DirEmp")
	private String dirEmp;

	@Column(name="FaxEmp")
	private String faxEmp;

	@Column(name="NitEmp")
	private String nitEmp;

	@Column(name="NomEmp")
	private String nomEmp;

	@Column(name="RifEmp")
	private String rifEmp;

	@Column(name="TelEmp")
	private String telEmp;

	public TabEmpresa() {
	}

	public int getIDEmp() {
		return this.IDEmp;
	}

	public void setIDEmp(int IDEmp) {
		this.IDEmp = IDEmp;
	}

	public String getDirEmp() {
		return this.dirEmp;
	}

	public void setDirEmp(String dirEmp) {
		this.dirEmp = dirEmp;
	}

	public String getFaxEmp() {
		return this.faxEmp;
	}

	public void setFaxEmp(String faxEmp) {
		this.faxEmp = faxEmp;
	}

	public String getNitEmp() {
		return this.nitEmp;
	}

	public void setNitEmp(String nitEmp) {
		this.nitEmp = nitEmp;
	}

	public String getNomEmp() {
		return this.nomEmp;
	}

	public void setNomEmp(String nomEmp) {
		this.nomEmp = nomEmp;
	}

	public String getRifEmp() {
		return this.rifEmp;
	}

	public void setRifEmp(String rifEmp) {
		this.rifEmp = rifEmp;
	}

	public String getTelEmp() {
		return this.telEmp;
	}

	public void setTelEmp(String telEmp) {
		this.telEmp = telEmp;
	}

}