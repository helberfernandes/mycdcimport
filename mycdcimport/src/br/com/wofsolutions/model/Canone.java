package br.com.wofsolutions.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity 
@Table(name="wof_canones", uniqueConstraints = { @UniqueConstraint( columnNames = "numero" ) } ) 
public class Canone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="canone_id")
	private Integer canoneId;
	
	
	@Column(length=10)
	private String numero="";
	@Column(length=10000)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="livro_id", referencedColumnName="livro_id")
	private Livro livro = new Livro();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="titulo_id", referencedColumnName="titulo_id")
	private Titulo titulo ;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="capitulo_id", referencedColumnName="capitulo_id")
	private Capitulo capitulo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="parte_id", referencedColumnName="parte_id")
	private Parte parte;
	
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="artigo_id", referencedColumnName="artigo_id")
	private Artigo artigo;
	
	public Integer getCanoneId() {
		return canoneId;
	}
	public void setCanoneId(Integer canoneId) {
		this.canoneId = canoneId;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
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
	public Titulo getTitulo() {
		return titulo;
	}
	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}
	public Capitulo getCapitulo() {
		return capitulo;
	}
	public void setCapitulo(Capitulo capitulo) {
		this.capitulo = capitulo;
	}
	public Parte getParte() {
		return parte;
	}
	public void setParte(Parte parte) {
		this.parte = parte;
	}
	public Artigo getArtigo() {
		return artigo;
	}
	public void setArtigo(Artigo artigo) {
		this.artigo = artigo;
	}
	
	
	

}
