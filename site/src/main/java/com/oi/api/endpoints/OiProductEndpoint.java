package com.oi.api.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.broadleafcommerce.rest.api.endpoint.BaseEndpoint;
import com.oi.api.services.OiProductService;
import com.oi.api.wrappers.ProdutoOiWrapper;
import com.oi.core.domains.OiProduct;


@RestController
@RequestMapping(value = "/oiproduct",
produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class OiProductEndpoint extends BaseEndpoint{

	@Autowired
	OiProductService oiProductService;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")	
	public ResponseEntity<?> getProdutos(HttpServletRequest request){
		
		List<OiProduct> oiProducts = oiProductService.findAll();
		
		List<ProdutoOiWrapper> oiWrapperList = new ArrayList<>();
			
		for (OiProduct oiProduct : oiProducts) {
			
			ProdutoOiWrapper oiWrapper = new ProdutoOiWrapper();
			
			
			oiWrapper.wrapDetails(oiProduct, request);
			
			oiWrapperList.add(oiWrapper);
			
		}
		
		
		return new ResponseEntity<List<ProdutoOiWrapper>>(oiWrapperList, HttpStatus.OK);
		
	}
	
    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ResponseEntity<?> findByCode(HttpServletRequest request, @PathVariable("code") String code){
		
		ProdutoOiWrapper oiWrapper = new ProdutoOiWrapper();
		
		OiProduct oiProduct = oiProductService.findByCode(code);
		

		oiWrapper.wrapDetails(oiProduct, request);
		
		return new ResponseEntity<ProdutoOiWrapper>(oiWrapper, HttpStatus.OK);
	}
    
    @RequestMapping(value = "/city/{code}", method = RequestMethod.GET)
	public ResponseEntity<?> findByCity(HttpServletRequest request, @PathVariable("code") String code){
		
		List<OiProduct> oiProducts = oiProductService.findCityCode(code);
		
		List<ProdutoOiWrapper> oiWrapperList = new ArrayList<>();
			
		for (OiProduct oiProduct : oiProducts) {
			
			ProdutoOiWrapper oiWrapper = new ProdutoOiWrapper();
			
			
			oiWrapper.wrapDetails(oiProduct, request);
			
			oiWrapperList.add(oiWrapper);
			
		}
		
		
		return new ResponseEntity<List<ProdutoOiWrapper>>(oiWrapperList, HttpStatus.OK);
	}

}
