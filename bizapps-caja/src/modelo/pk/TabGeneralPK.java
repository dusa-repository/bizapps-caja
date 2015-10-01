package modelo.pk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tabGeneral database table.
 * 
 */
@Embeddable
public class TabGeneralPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer IDRec;

	@Column(name="Ano")
	private short ano;

	private Integer IDEmp;

	public TabGeneralPK() {
	}
	public Integer getIDRec() {
		return this.IDRec;
	}
	public void setIDRec(Integer IDRec) {
		this.IDRec = IDRec;
	}
	public short getAno() {
		return this.ano;
	}
	public void setAno(short ano) {
		this.ano = ano;
	}
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
		if (!(other instanceof TabGeneralPK)) {
			return false;
		}
		TabGeneralPK castOther = (TabGeneralPK)other;
		return 
			(this.IDRec == castOther.IDRec)
			&& (this.ano == castOther.ano)
			&& (this.IDEmp == castOther.IDEmp);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.IDRec;
		hash = hash * prime + ((int) this.ano);
		hash = hash * prime + this.IDEmp;
		
		return hash;
	}
}