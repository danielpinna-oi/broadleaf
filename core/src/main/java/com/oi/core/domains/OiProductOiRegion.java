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
@Table(name="OI_PRODUCT_OI_REGION")
@DirectCopyTransform({
		@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX),
		@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
		
})
public class OiProductOiRegion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator= "OiProductOiRegionId")
    @GenericGenerator(
        name="OiProductOiRegionId",
        strategy="org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="OiProductOiRegion"),
            @Parameter(name="entity_name", value="com.oi.core.domains.OiProductOiRegion")
        }
    )   
	@Column(name = "OI_PRODUCT_OI_REGION_ID")	
	protected Long id;


	@ManyToOne(targetEntity=OiProduct.class, cascade=CascadeType.ALL)
	@JoinColumn(name="OI_PRODUCT")
	@AdminPresentation(excluded=true)
	protected OiProduct oiProduct;

	@ManyToOne(targetEntity=OiRegion.class, cascade=CascadeType.ALL)
	@JoinColumn(name="OI_REGION")
	@AdminPresentation(friendlyName="Região",
					   group="Dados da Região",
					   order=1,
					   prominent = true,
					   gridOrder = 1)
	@AdminPresentationToOneLookup(lookupDisplayProperty="region")
	protected OiRegion oiRegion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OiProduct getOiProduct() {
		return oiProduct;
	}

	public void setOiProduct(OiProduct oiProduct) {
		this.oiProduct = oiProduct;
	}

	public OiRegion getOiRegion() {
		return oiRegion;
	}

	public void setOiRegion(OiRegion oiRegion) {
		this.oiRegion = oiRegion;
	}



}