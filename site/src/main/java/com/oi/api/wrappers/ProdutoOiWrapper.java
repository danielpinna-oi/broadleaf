package com.oi.api.wrappers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.core.catalog.domain.Product;

import com.mycompany.api.wrapper.ProductWrapper;
import com.oi.core.domains.OiCityOiRegion;
import com.oi.core.domains.OiProduct;
import com.oi.core.domains.OiProductOiRegion;

@XmlRootElement(name = "produtooi")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProdutoOiWrapper extends ProductWrapper {

	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "regioes")
	@XmlElementWrapper(name = "regioes")
    private List<RegiaoWrapper> regioes = new ArrayList<>();

    @Override
    public void wrapDetails(Product model, HttpServletRequest request) {
    	
        //First, call the super method to get the default behavior
        super.wrapDetails(model, request);
                
        
        //this.produtoOiRegiaos = ((ProdutoOi)model).getProdutoOiRegiaos();
        
        for (OiProductOiRegion oiProductOiRegion : ((OiProduct)model).getOiProductOiRegions()) {
        	
        	RegiaoWrapper regiaoWrapper = new RegiaoWrapper();
        	
        	regiaoWrapper.setId(oiProductOiRegion.getOiRegion().getId());
        	regiaoWrapper.setRegiao(oiProductOiRegion.getOiRegion().getRegion());
        	regiaoWrapper.setDescricao(oiProductOiRegion.getOiRegion().getDescription());     
        	
        	List<CidadeWrapper> cidadesWrappers = new ArrayList<>();
        	
        	for (OiCityOiRegion cityOiRegion : oiProductOiRegion.getOiRegion().getCityOiRegions()) {
        		
        		CidadeWrapper cidadeWrapper = new CidadeWrapper();
        		
        		cidadeWrapper.setId(cityOiRegion.getOiCity().getId());
        		cidadeWrapper.setCidade(cityOiRegion.getOiCity().getCity());
        		cidadeWrapper.setDescricao(cityOiRegion.getOiCity().getDescription());  
        		cidadeWrapper.setCodigo(cityOiRegion.getOiCity().getCode());
        		cidadeWrapper.setEstado(cityOiRegion.getOiCity().getState());
        		
				
        		cidadesWrappers.add(cidadeWrapper);
			}
        	
        	regiaoWrapper.setCidadesRegiao(cidadesWrappers);
        	
        	
        	regioes.add(regiaoWrapper);
			
		}
        
    	
    }  
    
    
}