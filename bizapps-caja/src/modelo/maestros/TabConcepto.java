package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tabConcepto database table.
 * 
 */
@Entity
@Table(name="tabConcepto")
@NamedQuery(name="TabConcepto.findAll", query="SELECT t FROM TabConcepto t")
public class TabConcepto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int IDCon;

	@Column(name="DesCon")
	private String desCon;

	public TabConcepto() {
	}

	public int getIDCon() {
		return this.IDCon;
	}

	public void setIDCon(int IDCon) {
		this.IDCon = IDCon;
	}

	public String getDesCon() {
		return this.desCon;
	}

	public void setDesCon(String desCon) {
		this.desCon = desCon;
	}

}