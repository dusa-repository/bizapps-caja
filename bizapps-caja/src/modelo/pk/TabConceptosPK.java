package modelo.pk;

import java.io.Serializable;

import javax.persistence.*;

import controlador.maestros.CGenerico;
import modelo.maestros.TabConcepto;

/**
 * The primary key class for the tabConceptos database table.
 * 
 */
@Embeddable
public class TabConceptosPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer IDRec;
	private short ano;
	private Integer IDCon;
	private Integer IDEmp;

	public TabConceptosPK() {
	}

	@Column(insertable = false, updatable = false)
	public Integer getIDRec() {
		return this.IDRec;
	}

	public void setIDRec(Integer IDRec) {
		this.IDRec = IDRec;
	}

	@Column(name = "Ano", insertable = false, updatable = false)
	public short getAno() {
		return this.ano;
	}

	public void setAno(short ano) {
		this.ano = ano;
	}

	@Column(insertable = false, updatable = false)
	public Integer getIDCon() {
		return this.IDCon;
	}

	public void setIDCon(Integer IDCon) {
		this.IDCon = IDCon;
	}

	@Column(insertable = false, updatable = false)
	public Integer getIDEmp() {
		return this.IDEmp;
	}

	public void setIDEmp(Integer IDEmp) {
		this.IDEmp = IDEmp;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TabConceptosPK)) {
			return false;
		}
		TabConceptosPK castOther = (TabConceptosPK) other;
		return (this.IDRec == castOther.IDRec) && (this.ano == castOther.ano)
				&& (this.IDCon == castOther.IDCon)
				&& (this.IDEmp == castOther.IDEmp);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.IDRec;
		hash = hash * prime + ((int) this.ano);
		hash = hash * prime + this.IDCon;
		hash = hash * prime + this.IDEmp;

		return hash;
	}

	public String nombreConcepto() {
		TabConcepto concept = CGenerico.getSConcepto().buscar(this.IDCon);
		if (concept != null)
			return concept.getDesCon();
		else
			return "N/A";
	}
}