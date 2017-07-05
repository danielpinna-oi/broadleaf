package com.oi.api.services;

import java.util.List;

import com.oi.core.domains.OiProduct;

public interface OiProductService {

	public List<OiProduct> findAll();

	public OiProduct findById(Long id);
	
	public OiProduct findByCode(String code);
	
	public List<OiProduct> findCityCode(String code);

 
}
