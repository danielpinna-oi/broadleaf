package com.oi.core.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "OI_CITY")
@DirectCopyTransform({
	@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX),
	@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
	
})
public class OiCity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "OiCityId")
	@GenericGenerator(name = "OiCityId", 
					strategy = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator", 
						parameters = {
									@Parameter(name = "segment_value", value = "OiCity"),
									@Parameter(name = "entity_name", value = "com.oi.core.domains.OiCity") })
	@Column(name = "OI_CITY_ID")
	protected Long id;
	
	@Column(name = "CODE")
	@AdminPresentation(friendlyName = "Codigo da Cidade", 
					   group = "Dados da Cidade", 
					   fieldType = SupportedFieldType.STRING, 
					   order = 1, 
					   prominent = true, 
					   gridOrder = 1)
	protected String code;	

	@Column(name = "CITY")
	@AdminPresentation(friendlyName = "Cidade", 
					   group = "Dados da Cidade", 
					   fieldType = SupportedFieldType.STRING, 
					   order = 2, 
					   prominent = true, 
					   gridOrder = 2)
	protected String city;
	
	@Column(name = "STATE")
	@AdminPresentation(friendlyName = "Sigla do estado", 
					   group = "Dados da Cidade", 
					   fieldType = SupportedFieldType.STRING, 
					   order = 3, 
					   prominent = true, 
					   gridOrder = 3)
	protected String state;	
	
		

	@Column(name = "DESCRIPTION")
	@AdminPresentation(friendlyName = "Descrição da Cidade", 
					   group = "Dados da Cidade", 
					   fieldType = SupportedFieldType.HTML_BASIC, 
					   order = 4, prominent = true, 
					   gridOrder = 4)
	protected String description;
	
	@OneToMany(mappedBy="oiCity", targetEntity = OiCityOiRegion.class, cascade = {CascadeType.ALL})
	private List<OiCityOiRegion> oiCityOiRegions = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<OiCityOiRegion> getOiCityOiRegions() {
		return oiCityOiRegions;
	}

	public void setOiCityOiRegions(List<OiCityOiRegion> oiCityOiRegions) {
		this.oiCityOiRegions = oiCityOiRegions;
	}
	

}
