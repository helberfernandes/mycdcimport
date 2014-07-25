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
@Table(name="wof_capitulos")
public class Capitulo {
	@Id
	@GeneratedValue
	@Column(name="capitulo_id")
	private Integer capituloId;
	private String descricao;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="titulo_id", referencedColumnName="titulo_id")
	private Titulo  titulo= new Titulo();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="parte_id", referencedColumnName="parte_id")
	private Parte parte = new Parte();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="seccao_id", referencedColumnName="seccao_id")
	private Seccao seccao;
	
	
	@OneToMany	
	@JoinColumn(name="capitulo_id", referencedColumnName="capitulo_id")	
	private List<Canone> canones = new ArrayList<Canone>();
	
	@OneToMany	
	@JoinColumn(name="capitulo_id", referencedColumnName="capitulo_id")	
	private List<Artigo> artigos = new ArrayList<Artigo>();
	
	
	public Integer getCapituloId() {
		return capituloId;
	}
	public void setCapituloId(Integer capituloId) {
		this.capituloId = capituloId;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Titulo getTitulo() {
		return titulo;
	}
	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}
	public List<Canone> getCanones() {
		return canones;
	}
	public void setCanones(List<Canone> canones) {
		this.canones = canones;
	}
	public List<Artigo> getArtigos() {
		return artigos;
	}
	public void setArtigos(List<Artigo> artigos) {
		this.artigos = artigos;
	}
	public Parte getParte() {
		return parte;
	}
	public void setParte(Parte parte) {
		this.parte = parte;
	}
	public Seccao getSeccao() {
		return seccao;
	}
	public void setSeccao(Seccao seccao) {
		this.seccao = seccao;
	}
	
	
	
}
