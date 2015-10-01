package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.*;

import modelo.pk.TabDetallesPK;

import java.sql.Time;
import java.sql.Timestamp;


/**
 * The persistent class for the tabDetalles database table.
 * 
 */
@Entity
@Table(name="tabDetalles")
@NamedQuery(name="TabDetalles.findAll", query="SELECT t FROM TabDetalles t")
public class TabDetalles implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TabDetallesPK id;

	@Column(name="AutNot")
	private String autNot;

	@Column(name="ConNot")
	private String conNot;

	@Column(name="HorNot")
	private Timestamp horNot;

	@Column(name="ImpNot")
	private double impNot;

	@Column(name="MonNot")
	private double monNot;

	@Column(name="RefNot")
	private String refNot;

	@Column(name="RevNot")
	private String revNot;
	public TabDetalles() {
	}

	public TabDetallesPK getId() {
		return this.id;
	}

	public void setId(TabDetallesPK id) {
		this.id = id;
	}

	public String getAutNot() {
		return this.autNot;
	}

	public void setAutNot(String autNot) {
		this.autNot = autNot;
	}

	public String getConNot() {
		return this.conNot;
	}

	public void setConNot(String conNot) {
		this.conNot = conNot;
	}

	public Timestamp getHorNot() {
		return this.horNot;
	}

	public void setHorNot(Timestamp horNot) {
		this.horNot = horNot;
	}

	public double getImpNot() {
		return this.impNot;
	}

	public void setImpNot(double impNot) {
		this.impNot = impNot;
	}

	public double getMonNot() {
		return this.monNot;
	}

	public void setMonNot(double monNot) {
		this.monNot = monNot;
	}

	public String getRefNot() {
		return this.refNot;
	}

	public void setRefNot(String refNot) {
		this.refNot = refNot;
	}

	public String getRevNot() {
		return this.revNot;
	}

	public void setRevNot(String revNot) {
		this.revNot = revNot;
	}

}