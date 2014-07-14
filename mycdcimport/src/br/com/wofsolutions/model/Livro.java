package br.com.wofsolutions.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="wof_livros")
public class Livro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="livro_id")
	private Integer livroId;	
	private String descricao;
	
	
	@OneToMany	
	@JoinColumn(name="livro_id", referencedColumnName="livro_id")	
	private List<Canone> canones = new ArrayList<Canone>();

	@OneToMany	
	@JoinColumn(name="livro_id", referencedColumnName="livro_id")	
	private List<Parte> partes = new ArrayList<Parte>();
	
	
	@OneToMany	
	@JoinColumn(name="livro_id", referencedColumnName="livro_id")	
	private List<Titulo> titulos  = new ArrayList<Titulo>();
	
	
	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Titulo> getTitulos() {
		return titulos;
	}

	public void setTitulos(List<Titulo> titulos) {
		this.titulos = titulos;
	}

	public List<Canone> getCanones() {
		return canones;
	}

	public void setCanones(List<Canone> canones) {
		this.canones = canones;
	}

	public List<Parte> getPartes() {
		return partes;
	}

	public void setPartes(List<Parte> partes) {
		this.partes = partes;
	}
	
	
	
	
}
