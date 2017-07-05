package com.oi.api.wrappers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "regiao")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CidadeWrapper {

	@XmlElement(name = "idCidade")
	protected Long id;

	@XmlElement(name = "codigo")
	protected String codigo;
	
	@XmlElement(name = "cidade")
	protected String cidade;
	
	@XmlElement(name = "estado")
	protected String estado;	

	@XmlElement(name = "descricaoCidade")
	protected String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}
