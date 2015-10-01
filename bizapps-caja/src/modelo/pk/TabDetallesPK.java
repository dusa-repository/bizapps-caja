package modelo.pk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tabDetalles database table.
 * 
 */
@Embeddable
public class TabDetallesPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int IDNot;

	@Column(name="TipNot", insertable=false, updatable=false)
	private String tipNot;

	@Column(name="CatNot", insertable=false, updatable=false)
	private String catNot;

	@Column(insertable=false, updatable=false)
	private int IDEmp;

	@Column(name="Row")
	private short row;

	public TabDetallesPK() {
	}
	public int getIDNot() {
		return this.IDNot;
	}
	public void setIDNot(int IDNot) {
		this.IDNot = IDNot;
	}
	public String getTipNot() {
		return this.tipNot;
	}
	public void setTipNot(String tipNot) {
		this.tipNot = tipNot;
	}
	public String getCatNot() {
		return this.catNot;
	}
	public void setCatNot(String catNot) {
		this.catNot = catNot;
	}
	public int getIDEmp() {
		return this.IDEmp;
	}
	public void setIDEmp(int IDEmp) {
		this.IDEmp = IDEmp;
	}
	public short getRow() {
		return this.row;
	}
	public void setRow(short row) {
		this.row = row;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TabDetallesPK)) {
			return false;
		}
		TabDetallesPK castOther = (TabDetallesPK)other;
		return 
			(this.IDNot == castOther.IDNot)
			&& this.tipNot.equals(castOther.tipNot)
			&& this.catNot.equals(castOther.catNot)
			&& (this.IDEmp == castOther.IDEmp)
			&& (this.row == castOther.row);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.IDNot;
		hash = hash * prime + this.tipNot.hashCode();
		hash = hash * prime + this.catNot.hashCode();
		hash = hash * prime + this.IDEmp;
		hash = hash * prime + ((int) this.row);
		
		return hash;
	}
}