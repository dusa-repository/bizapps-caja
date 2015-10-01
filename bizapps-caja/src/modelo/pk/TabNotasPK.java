package modelo.pk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tabNotas database table.
 * 
 */
@Embeddable
public class TabNotasPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private Integer IDNot;
	
	private String tipNot;
	
	private String catNot;
	
	private Integer IDEmp;

	public TabNotasPK() {
	}

	public Integer getIDNot() {
		return this.IDNot;
	}
	public void setIDNot(Integer IDNot) {
		this.IDNot = IDNot;
	}

	@Column(name="TipNot")
	public String getTipNot() {
		return this.tipNot;
	}
	public void setTipNot(String tipNot) {
		this.tipNot = tipNot;
	}

	@Column(name="CatNot")
	public String getCatNot() {
		return this.catNot;
	}
	public void setCatNot(String catNot) {
		this.catNot = catNot;
	}

	@Column(insertable=false, updatable=false)
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
		if (!(other instanceof TabNotasPK)) {
			return false;
		}
		TabNotasPK castOther = (TabNotasPK)other;
		return 
			(this.IDNot == castOther.IDNot)
			&& this.tipNot.equals(castOther.tipNot)
			&& this.catNot.equals(castOther.catNot)
			&& (this.IDEmp == castOther.IDEmp);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.IDNot;
		hash = hash * prime + this.tipNot.hashCode();
		hash = hash * prime + this.catNot.hashCode();
		hash = hash * prime + this.IDEmp;
		
		return hash;
	}
}