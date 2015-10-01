package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.*;

import modelo.pk.TabNotasPK;

import java.sql.Timestamp;

/**
 * The persistent class for the tabNotas database table.
 * 
 */
@Entity
@Table(name = "tabNotas")
@NamedQuery(name = "TabNotas.findAll", query = "SELECT t FROM TabNotas t")
public class TabNotas implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private TabNotasPK id;
	
	private short anoNot;
	
	private String estNot;
	
	private Timestamp fecNot;
	
	private Timestamp horNot;
	
	private int IDPro;
	
	private String tipMon;
	
	private Double totNot;
	
	private String usuNot;
	
	private Double valMon;

	public TabNotas() {
	}
	
	@EmbeddedId
	public TabNotasPK getId() {
		return this.id;
	}

	public void setId(TabNotasPK id) {
		this.id = id;
	}

	@Column(name = "AnoNot")
	public short getAnoNot() {
		return this.anoNot;
	}

	public void setAnoNot(short anoNot) {
		this.anoNot = anoNot;
	}

	@Column(name = "EstNot")
	public String getEstNot() {
		return this.estNot;
	}

	public void setEstNot(String estNot) {
		this.estNot = estNot;
	}

	@Column(name = "FecNot")
	public Timestamp getFecNot() {
		return this.fecNot;
	}

	public void setFecNot(Timestamp fecNot) {
		this.fecNot = fecNot;
	}

	@Column(name = "HorNot")
	public Timestamp getHorNot() {
		return this.horNot;
	}

	public void setHorNot(Timestamp horNot) {
		this.horNot = horNot;
	}

	public int getIDPro() {
		return this.IDPro;
	}

	public void setIDPro(int IDPro) {
		this.IDPro = IDPro;
	}

	@Column(name = "TipMon")
	public String getTipMon() {
		return this.tipMon;
	}

	public void setTipMon(String tipMon) {
		this.tipMon = tipMon;
	}

	@Column(name = "TotNot")
	public Double getTotNot() {
		return this.totNot;
	}

	public void setTotNot(Double totNot) {
		this.totNot = totNot;
	}

	@Column(name = "UsuNot")
	public String getUsuNot() {
		return this.usuNot;
	}

	public void setUsuNot(String usuNot) {
		this.usuNot = usuNot;
	}

	@Column(name = "ValMon")
	public Double getValMon() {
		return this.valMon;
	}

	public void setValMon(Double valMon) {
		this.valMon = valMon;
	}

}