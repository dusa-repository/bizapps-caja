package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tabBancos database table.
 * 
 */
@Entity
@Table(name="tabBancos")
@NamedQuery(name="TabBanco.findAll", query="SELECT t FROM TabBanco t")
public class TabBanco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int IDBan;

	@Column(name="NomBan")
	private String nomBan;

	public TabBanco() {
	}

	public int getIDBan() {
		return this.IDBan;
	}

	public void setIDBan(int IDBan) {
		this.IDBan = IDBan;
	}

	public String getNomBan() {
		return this.nomBan;
	}

	public void setNomBan(String nomBan) {
		this.nomBan = nomBan;
	}

}