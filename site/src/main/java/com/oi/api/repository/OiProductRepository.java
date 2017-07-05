package com.oi.api.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.oi.core.domains.OiProduct;

@Repository
public class OiProductRepository {
	
    @PersistenceContext(unitName="blPU")
    protected EntityManager em;	
    
    public List<OiProduct> findAll() {
		TypedQuery<OiProduct> oiProducts = em.createQuery("SELECT p FROM OiProduct p", OiProduct.class);
		
		List<OiProduct> results = oiProducts.getResultList();
		
		return results;
	}


	public OiProduct findById(Long id) {
		return em.find(OiProduct.class, id);
	}


	public OiProduct findByCode(String code) {
		TypedQuery<OiProduct> oiProduct = em.createQuery("SELECT p FROM OiProduct p where p.code = :code", OiProduct.class);
		oiProduct.setParameter("code", code);
		return oiProduct.getSingleResult();
	}
	
	public List<OiProduct> findByCityCode(String code) {
		TypedQuery<OiProduct> oiProduct = em.createQuery(
				"SELECT p FROM OiProduct p "
				+ "inner join p.oiProductOiRegions pr "
				+ "where pr.oiRegion.id in "
				+ "(SELECT cr.id FROM OiCity c inner join c.oiCityOiRegions cr "
				+ "where c.code = :code)", OiProduct.class);
		oiProduct.setParameter("code", code);
		return oiProduct.getResultList();
	}	

}
