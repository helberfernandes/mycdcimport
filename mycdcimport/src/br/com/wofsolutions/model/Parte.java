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
@Table(name="wof_partes")
public class Parte {
	
	@Id
	@GeneratedValue
	@Column(name="parte_id")
	private Integer parteId;
	
	private String descricao;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="livro_id", referencedColumnName="livro_id")
	private Livro livro;
	
	@OneToMany	
	@JoinColumn(name="parte_id", referencedColumnName="parte_id")	
	private List<Capitulo> capitulos = new ArrayList<Capitulo>();
	
	@OneToMany	
	@JoinColumn(name="parte_id", referencedColumnName="parte_id")	
	private List<Seccao> seccaos = new ArrayList<Seccao>();
	
	
	@OneToMany	
	@JoinColumn(name="parte_id", referencedColumnName="parte_id")		
	private List<Canone> canones = new ArrayList<Canone>();

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public Integer getParteId() {
		return parteId;
	}
	public void setParteId(Integer parteId) {
		this.parteId = parteId;
	}
	public List<Capitulo> getCapitulos() {
		return capitulos;
	}
	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
	}
	public List<Seccao> getSeccaos() {
		return seccaos;
	}
	public void setSeccaos(List<Seccao> seccaos) {
		this.seccaos = seccaos;
	}
	public List<Canone> getCanones() {
		return canones;
	}
	public void setCanones(List<Canone> canones) {
		this.canones = canones;
	}
	
	
}
