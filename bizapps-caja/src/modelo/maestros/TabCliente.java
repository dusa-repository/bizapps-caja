package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tabCliente database table.
 * 
 */
@Entity
@Table(name="tabCliente")
@NamedQuery(name="TabCliente.findAll", query="SELECT t FROM TabCliente t")
public class TabCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int IDCli;

	@Column(name="NomCli")
	private String nomCli;

	public TabCliente() {
	}

	public int getIDCli() {
		return this.IDCli;
	}

	public void setIDCli(int IDCli) {
		this.IDCli = IDCli;
	}

	public String getNomCli() {
		return this.nomCli;
	}

	public void setNomCli(String nomCli) {
		this.nomCli = nomCli;
	}

}