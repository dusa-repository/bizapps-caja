package modelo.transacciones;

import java.io.Serializable;

import javax.persistence.*;

import modelo.pk.TabGeneralPK;

import java.sql.Timestamp;


/**
 * The persistent class for the tabGeneral database table.
 * 
 */
@Entity
@Table(name="tabGeneral")
@NamedQuery(name="TabGeneral.findAll", query="SELECT t FROM TabGeneral t")
public class TabGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TabGeneralPK id;

	@Column(name="CamBol")
	private Double camBol;

	@Column(name="EstRec")
	private String estRec;

	@Column(name="FecRec")
	private Timestamp fecRec;

	@Column(name="ForPag")
	private String forPag;

	@Column(name="HorRec")
	private Timestamp horRec;

	private int IDBan;

	private int IDCli;

	@Column(name="NumChe")
	private String numChe;

	@Column(name="ObseRec")
	private String obseRec;

	@Column(name="TipMon")
	private String tipMon;

	@Column(name="TotFac")
	private Double totFac;

	public TabGeneral() {
	}

	public TabGeneralPK getId() {
		return this.id;
	}

	public void setId(TabGeneralPK id) {
		this.id = id;
	}

	public Double getCamBol() {
		return this.camBol;
	}

	public void setCamBol(Double camBol) {
		this.camBol = camBol;
	}

	public String getEstRec() {
		return this.estRec;
	}

	public void setEstRec(String estRec) {
		this.estRec = estRec;
	}

	public Timestamp getFecRec() {
		return this.fecRec;
	}

	public void setFecRec(Timestamp fecRec) {
		this.fecRec = fecRec;
	}

	public String getForPag() {
		return this.forPag;
	}

	public void setForPag(String forPag) {
		this.forPag = forPag;
	}

	public Timestamp getHorRec() {
		return this.horRec;
	}

	public void setHorRec(Timestamp horRec) {
		this.horRec = horRec;
	}

	public int getIDBan() {
		return this.IDBan;
	}

	public void setIDBan(int IDBan) {
		this.IDBan = IDBan;
	}

	public int getIDCli() {
		return this.IDCli;
	}

	public void setIDCli(int IDCli) {
		this.IDCli = IDCli;
	}

	public String getNumChe() {
		return this.numChe;
	}

	public void setNumChe(String numChe) {
		this.numChe = numChe;
	}

	public String getObseRec() {
		return this.obseRec;
	}

	public void setObseRec(String obseRec) {
		this.obseRec = obseRec;
	}

	public String getTipMon() {
		return this.tipMon;
	}

	public void setTipMon(String tipMon) {
		this.tipMon = tipMon;
	}

	public Double getTotFac() {
		return this.totFac;
	}

	public void setTotFac(Double totFac) {
		this.totFac = totFac;
	}

}