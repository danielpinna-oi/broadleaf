package com.oi.api.wrappers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "regiao")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class RegiaoWrapper {

	@XmlElement(name = "idRegiao")
    protected Long id;

	@XmlElement(name = "regiao")
    protected String regiao;
    
	@XmlElement(name = "descricao")
    protected String descricao;   
    
    @XmlElement(name = "cidadesRegiao")
    @XmlElementWrapper(name = "cidadesRegiao")
    protected List<CidadeWrapper> cidadesRegiao = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<CidadeWrapper> getCidadesRegiao() {
		return cidadesRegiao;
	}

	public void setCidadesRegiao(List<CidadeWrapper> cidadesRegiao) {
		this.cidadesRegiao = cidadesRegiao;
	}
	
    
    

}