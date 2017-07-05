package com.oi.core.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "OI_REGION")
@DirectCopyTransform({
	@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX),
	@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
	
})
public class OiRegion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator= "OiRegionId")
    @GenericGenerator(
        name="OiRegionId",
        strategy="org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="OiRegion"),
            @Parameter(name="entity_name", value="com.oi.core.domains.OiRegion")
        }
    )      
    @Column(name = "OI_REGION_ID")
    protected Long id;

    @Column(name = "REGION")
    @AdminPresentation(friendlyName="Região",
    		group = "Dados da região",
    		fieldType = SupportedFieldType.STRING,
    		order = 1,
    		prominent = true, gridOrder = 1)
    protected String region;
    
    @Column(name = "DESCRIPTION")
    @AdminPresentation(friendlyName="Descrição",
    		group = "Dados da Região",
    		fieldType = SupportedFieldType.HTML_BASIC,
    		order = 2,
    		prominent = true, gridOrder = 2)
    protected String description;   
    
	@OneToMany(mappedBy="oiRegion", targetEntity = OiCityOiRegion.class, cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	@AdminPresentationCollection(friendlyName="Cidades da região", addType = AddMethodType.PERSIST)
	private List<OiCityOiRegion> cityOiRegions = new ArrayList<>();
	
	@OneToMany(mappedBy="oiRegion", targetEntity = OiProductOiRegion.class, cascade = {CascadeType.ALL})
	private List<OiProductOiRegion> oiProductOiRegions = new ArrayList<>();	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<OiCityOiRegion> getCityOiRegions() {
		return cityOiRegions;
	}

	public void setCityOiRegions(List<OiCityOiRegion> cityOiRegions) {
		this.cityOiRegions = cityOiRegions;
	}

	public List<OiProductOiRegion> getOiProductOiRegions() {
		return oiProductOiRegions;
	}

	public void setOiProductOiRegions(List<OiProductOiRegion> oiProductOiRegions) {
		this.oiProductOiRegions = oiProductOiRegions;
	}


    

}