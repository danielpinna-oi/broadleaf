package com.oi.core.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import org.broadleafcommerce.core.catalog.domain.ProductImpl;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "OI_PRODUCT")
@DirectCopyTransform({
	@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX),
	@DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
	
})
public class OiProduct extends ProductImpl {
	
    private static final long serialVersionUID = 1L;
    
    @Column(name = "CODE")
    @AdminPresentation(friendlyName="Codigo do Produto",
    		group = "ProductImpl_Product_Description",
    		fieldType = SupportedFieldType.STRING,
    		order = 1,
    		prominent = true, gridOrder = 1)
    protected String code;

	@OneToMany(mappedBy="oiProduct", targetEntity = OiProductOiRegion.class, cascade = {CascadeType.ALL}, fetch=FetchType.EAGER)
	@AdminPresentationCollection(friendlyName="Região", addType = AddMethodType.PERSIST)
	private List<OiProductOiRegion> oiProductOiRegions = new ArrayList<>();
	


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<OiProductOiRegion> getOiProductOiRegions() {
		return oiProductOiRegions;
	}

	public void setOiProductOiRegions(List<OiProductOiRegion> oiProductOiRegions) {
		this.oiProductOiRegions = oiProductOiRegions;
	}	

}