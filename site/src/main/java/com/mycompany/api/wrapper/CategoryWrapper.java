/*
 * #%L
 * BroadleafCommerce Framework Web
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.mycompany.api.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.util.xml.ISO8601DateAdapter;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryAttribute;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.broadleafcommerce.core.search.domain.SearchCriteria;
import org.broadleafcommerce.core.search.domain.SearchResult;
import org.broadleafcommerce.core.search.service.SearchService;
import org.springframework.http.HttpStatus;

/**
 *  This is a JAXB wrapper for a Broadleaf Category.  There may be several reasons to extend this class.
 *  First, you may want to extend Broadleaf's CategroryImpl and expose those extensions via a RESTful
 *  service.  You may also want to suppress properties that are being serialized.  To expose new properties, or
 *  suppress properties that have been exposed, do the following:<br/>
 *  <br/>
 *  1. Extend this class   <br/>
 *  2. Override the <code>wrap</code> method. <br/>
 *  3. Within the wrap method, either override all properties that you want to set, or call <code>super.wrap(Category)</code>   <br/>
 *  4. Set additional property values that you have added.  <br/>
 *  5. Set super properties to null if you do not want them serialized. (e.g. <code>super.name = null;</code>  <br/>
 */
@XmlRootElement(name = "category")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategoryWrapper extends BaseWrapper implements APIWrapper<Category> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String name;

    @XmlElement
    protected String description;

    @XmlElement
    protected Boolean active;

    @XmlElement
    protected String url;

    @XmlElement
    protected String urlKey;

    @XmlElement
    @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
    protected Date activeStartDate;

    @XmlElement
    @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
    protected Date activeEndDate;

    @XmlElement(name = "category")
    @XmlElementWrapper(name = "subcategories")
    protected List<CategoryWrapper> subcategories;

    @XmlElement(name = "product")
    @XmlElementWrapper(name = "products")
    protected List<ProductWrapper> products;

    @XmlElement(name = "categoryAttribute")
    @XmlElementWrapper(name = "categoryAttributes")
    protected List<CategoryAttributeWrapper> categoryAttributes;

    @Override
    public void wrapDetails(Category category, HttpServletRequest request) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.active = category.isActive();
        this.activeStartDate = category.getActiveStartDate();
        this.activeEndDate = category.getActiveEndDate();
        this.url = category.getUrl();
        this.urlKey = category.getUrlKey();

        if (category.getCategoryAttributes() != null && !category.getCategoryAttributes().isEmpty()) {
            categoryAttributes = new ArrayList<CategoryAttributeWrapper>();
            for (CategoryAttribute attribute : category.getCategoryAttributes()) {
                CategoryAttributeWrapper wrapper = (CategoryAttributeWrapper) context.getBean(CategoryAttributeWrapper.class.getName());
                wrapper.wrapSummary(attribute, request);
                categoryAttributes.add(wrapper);
            }
        }

        Integer productLimit = (Integer) request.getAttribute("productLimit");
        Integer productOffset = (Integer) request.getAttribute("productOffset");
        Integer subcategoryLimit = (Integer) request.getAttribute("subcategoryLimit");
        Integer subcategoryOffset = (Integer) request.getAttribute("subcategoryOffset");

        if (productLimit != null && productOffset == null) {
            productOffset = 1;
        }

        if (subcategoryLimit != null && subcategoryOffset == null) {
            subcategoryOffset = 1;
        }

        if (productLimit != null && productOffset != null) {
            SearchService searchService = getSearchService();
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setPage(productOffset);
            searchCriteria.setPageSize(productLimit);
            searchCriteria.setFilterCriteria(new HashMap<String, String[]>());
            try {
                SearchResult result = searchService.findExplicitSearchResultsByCategory(category, searchCriteria);
                List<Product> productList = result.getProducts();
                if (productList != null && !productList.isEmpty()) {
                    if (products == null) {
                        products = new ArrayList<ProductWrapper>();
                    }

                    for (Product p : productList) {
                        ProductWrapper productSummaryWrapper = (ProductWrapper) context.getBean(ProductWrapper.class.getName());
                        productSummaryWrapper.wrapSummary(p, request);
                        products.add(productSummaryWrapper);
                    }
                }
            } catch (ServiceException e) {
                throw com.broadleafcommerce.rest.api.exception.BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e);
            }
        }

        if (subcategoryLimit != null && subcategoryOffset != null) {
            subcategories = buildSubcategoryTree(subcategories, category, request);
        }
    }

    @Override
    public void wrapSummary(Category category, HttpServletRequest request) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.active = category.isActive();
    }


    protected List<CategoryWrapper> buildSubcategoryTree(List<CategoryWrapper> wrappers, Category root, HttpServletRequest request) {
        CatalogService catalogService = (CatalogService) context.getBean("blCatalogService");

        Integer subcategoryLimit = (Integer) request.getAttribute("subcategoryLimit");
        Integer subcategoryOffset = (Integer) request.getAttribute("subcategoryOffset");

        List<Category> subcategories = catalogService.findActiveSubCategoriesByCategory(root, subcategoryLimit, subcategoryOffset);
        if (subcategories !=null && !subcategories.isEmpty() && wrappers == null) {
            wrappers = new ArrayList<CategoryWrapper>();
        }

        for (Category c : subcategories) {
            CategoryWrapper subcategoryWrapper = (CategoryWrapper) context.getBean(CategoryWrapper.class.getName());
            subcategoryWrapper.wrapSummary(c, request);
            wrappers.add(subcategoryWrapper);
        }

        return wrappers;
    }

    protected SearchService getSearchService() {
        return (SearchService) context.getBean("blSearchService");
    }

    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    
    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    
    /**
     * @return the urlKey
     */
    public String getUrlKey() {
        return urlKey;
    }

    
    /**
     * @param urlKey the urlKey to set
     */
    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    
    /**
     * @return the activeStartDate
     */
    public Date getActiveStartDate() {
        return activeStartDate;
    }

    
    /**
     * @param activeStartDate the activeStartDate to set
     */
    public void setActiveStartDate(Date activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    
    /**
     * @return the activeEndDate
     */
    public Date getActiveEndDate() {
        return activeEndDate;
    }

    
    /**
     * @param activeEndDate the activeEndDate to set
     */
    public void setActiveEndDate(Date activeEndDate) {
        this.activeEndDate = activeEndDate;
    }

    
    /**
     * @return the subcategories
     */
    public List<CategoryWrapper> getSubcategories() {
        return subcategories;
    }

    
    /**
     * @param subcategories the subcategories to set
     */
    public void setSubcategories(List<CategoryWrapper> subcategories) {
        this.subcategories = subcategories;
    }

    
    /**
     * @return the products
     */
    public List<ProductWrapper> getProducts() {
        return products;
    }

    
    /**
     * @param products the products to set
     */
    public void setProducts(List<ProductWrapper> products) {
        this.products = products;
    }

    
    /**
     * @return the categoryAttributes
     */
    public List<CategoryAttributeWrapper> getCategoryAttributes() {
        return categoryAttributes;
    }

    
    /**
     * @param categoryAttributes the categoryAttributes to set
     */
    public void setCategoryAttributes(List<CategoryAttributeWrapper> categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
    }
}
