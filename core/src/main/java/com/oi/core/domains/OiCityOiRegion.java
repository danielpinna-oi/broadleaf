package com.oi.core.domains;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="OI_CITY_OI_REGION")
@DirectCopyTransform({
		@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX),
		@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
		
})
public class OiCityOiRegion implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator= "OiCityOiRegionId")
    @GenericGenerator(
        name="OiCityOiRegionId",
        strategy="org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="OiCityOiRegion"),
            @Parameter(name="entity_name", value="com.oi.core.domains.OiCityOiRegion")
        }
    )   
	@Column(name = "OI_CITY_OI_REGION_ID")	
	protected Long id;


	@ManyToOne(targetEntity=OiRegion.class, cascade=CascadeType.ALL)
	@JoinColumn(name="OI_REGION_ID")
	@AdminPresentation(excluded=true)
	protected OiRegion oiRegion;

	@ManyToOne(targetEntity=OiCity.class, cascade=CascadeType.ALL)
	@JoinColumn(name="OI_CITY_ID")
	@AdminPresentation(friendlyName="Cidades",
					   group="Dados da Região",
					   order=1,
					   prominent = true,
					   gridOrder = 1)
	@AdminPresentationToOneLookup(lookupDisplayProperty="city")
	protected OiCity oiCity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OiRegion getOiRegion() {
		return oiRegion;
	}

	public void setOiRegion(OiRegion oiRegion) {
		this.oiRegion = oiRegion;
	}

	public OiCity getOiCity() {
		return oiCity;
	}

	public void setOiCity(OiCity oiCity) {
		this.oiCity = oiCity;
	}

}