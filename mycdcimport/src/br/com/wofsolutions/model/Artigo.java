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
@Table(name="wof_artigos")
public class Artigo {
	
	@Id
	@GeneratedValue
	@Column(name="artigo_id")
	private Integer artigoId;
	private String titulo;
	private String descricao;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="capitulo_id", referencedColumnName="capitulo_id")
	private Capitulo  capitulo ;
	
	@OneToMany	
	@JoinColumn(name="artigo_id", referencedColumnName="artigo_id")	
	private List<Canone> canones = new ArrayList<Canone>();
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

	public Integer getArtigoId() {
		return artigoId;
	}
	public void setArtigoId(Integer artigoId) {
		this.artigoId = artigoId;
	}
	public Capitulo getCapitulo() {
		return capitulo;
	}
	public void setCapitulo(Capitulo capitulo) {
		this.capitulo = capitulo;
	}
	public List<Canone> getCanones() {
		return canones;
	}
	public void setCanones(List<Canone> canones) {
		this.canones = canones;
	}

	
	
}
