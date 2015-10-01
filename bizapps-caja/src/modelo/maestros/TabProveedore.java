package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tabProveedores database table.
 * 
 */
@Entity
@Table(name="tabProveedores")
@NamedQuery(name="TabProveedore.findAll", query="SELECT t FROM TabProveedore t")
public class TabProveedore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int IDPro;

	@Column(name="DirPro")
	private String dirPro;

	@Column(name="NitPro")
	private String nitPro;

	@Column(name="NomPro")
	private String nomPro;

	@Column(name="RifPro")
	private String rifPro;

	@Column(name="TelPro")
	private String telPro;

	public TabProveedore() {
	}

	public int getIDPro() {
		return this.IDPro;
	}

	public void setIDPro(int IDPro) {
		this.IDPro = IDPro;
	}

	public String getDirPro() {
		return this.dirPro;
	}

	public void setDirPro(String dirPro) {
		this.dirPro = dirPro;
	}

	public String getNitPro() {
		return this.nitPro;
	}

	public void setNitPro(String nitPro) {
		this.nitPro = nitPro;
	}

	public String getNomPro() {
		return this.nomPro;
	}

	public void setNomPro(String nomPro) {
		this.nomPro = nomPro;
	}

	public String getRifPro() {
		return this.rifPro;
	}

	public void setRifPro(String rifPro) {
		this.rifPro = rifPro;
	}

	public String getTelPro() {
		return this.telPro;
	}

	public void setTelPro(String telPro) {
		this.telPro = telPro;
	}

}