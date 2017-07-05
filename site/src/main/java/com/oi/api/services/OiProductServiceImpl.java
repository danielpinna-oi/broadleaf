package com.oi.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oi.api.repository.OiProductRepository;
import com.oi.core.domains.OiProduct;

@Service
public class OiProductServiceImpl implements OiProductService {
	
    @Autowired
    private OiProductRepository oiProductRepository;
	

	@Override
	@Transactional(readOnly=true)
	public List<OiProduct> findAll() {
		return oiProductRepository.findAll();
	}


	@Override
	@Transactional(readOnly=true)
	public OiProduct findById(Long id) {
		return oiProductRepository.findById(id);
	}


	@Override
	@Transactional(readOnly=true)
	public OiProduct findByCode(String code) {
		return oiProductRepository.findByCode(code);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<OiProduct> findCityCode(String code) {
		return oiProductRepository.findByCityCode(code);
	}

}
