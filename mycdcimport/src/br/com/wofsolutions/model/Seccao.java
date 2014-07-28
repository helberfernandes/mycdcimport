package br.com.wofsolutions.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="wof_seccao")
public class Seccao {
	
	@Id
	@GeneratedValue
	@Column(name="seccao_id")
	private Integer seccaoId;
	
	private String descricao;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="parte_id", referencedColumnName="parte_id")
	private Parte  parte;
	
	@OneToMany	
	@JoinColumn(name="seccao_id", referencedColumnName="seccao_id")	
	private List<Canone> canones = new ArrayList<Canone>();
	
	
	
	@OneToMany	
	@JoinColumn(name="seccao_id", referencedColumnName="seccao_id")	
	private List<Titulo> titulos  = new ArrayList<Titulo>();
	
		
	@OneToMany	
	@JoinColumn(name="seccao_id", referencedColumnName="seccao_id")	
	private List<Capitulo> capitulos = new ArrayList<Capitulo>();

	

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public Integer getSeccaoId() {
		return seccaoId;
	}
	public void setSeccaoId(Integer seccaoId) {
		this.seccaoId = seccaoId;
	}
	public Parte getParte() {
		return parte;
	}
	public void setParte(Parte parte) {
		this.parte = parte;
	}
	public List<Canone> getCanones() {
		return canones;
	}
	public void setCanones(List<Canone> canones) {
		this.canones = canones;
	}
	public List<Capitulo> getCapitulos() {
		return capitulos;
	}
	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
	}
	public List<Titulo> getTitulos() {
		return titulos;
	}
	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}
	
	
	
}
