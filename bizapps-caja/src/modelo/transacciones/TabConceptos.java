package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.*;

import modelo.pk.TabConceptosPK;


/**
 * The persistent class for the tabConceptos database table.
 * 
 */
@Entity
@Table(name="tabConceptos")
@NamedQuery(name="TabConceptos.findAll", query="SELECT t FROM TabConceptos t")
public class TabConceptos implements Serializable {
	private static final long serialVersionUID = 1L;
	private TabConceptosPK id;
	private int canCon;
	private double impCon;
	private double preCon;
	private double totRec;

	public TabConceptos() {
	}


	@EmbeddedId
	public TabConceptosPK getId() {
		return this.id;
	}

	public void setId(TabConceptosPK id) {
		this.id = id;
	}


	@Column(name="CanCon")
	public int getCanCon() {
		return this.canCon;
	}

	public void setCanCon(int canCon) {
		this.canCon = canCon;
	}


	@Column(name="ImpCon")
	public double getImpCon() {
		return this.impCon;
	}

	public void setImpCon(double impCon) {
		this.impCon = impCon;
	}


	@Column(name="PreCon")
	public double getPreCon() {
		return this.preCon;
	}

	public void setPreCon(double preCon) {
		this.preCon = preCon;
	}


	@Column(name="TotRec")
	public double getTotRec() {
		return this.totRec;
	}

	public void setTotRec(double totRec) {
		this.totRec = totRec;
	}

}