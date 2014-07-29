package br.com.wofsolutions.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wof_glossario")
public class Glossario implements Serializable {

	
	@Id
	@GeneratedValue
	@Column(name="glossario_id")
	private Integer glossarioId;
	
	private String palavra;
	
	@Column(length=1000)
	private String significado="";

	public Integer getGlossarioId() {
		return glossarioId;
	}

	public void setGlossarioId(Integer glossarioId) {
		this.glossarioId = glossarioId;
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public String getSignificado() {
		return significado;
	}

	public void setSignificado(String significado) {
		this.significado = significado;
	}
	
	
}
