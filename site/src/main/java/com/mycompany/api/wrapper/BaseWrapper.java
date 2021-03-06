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

import org.apache.commons.collections4.CollectionUtils;
import org.broadleafcommerce.profile.core.domain.AdditionalFields;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Base class for APIWrapper implementations to inject the EntityConfiguration reference.
 */
public abstract class BaseWrapper implements ApplicationContextAware {

    @XmlTransient
    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Utility method.
     * Traverses the domain object's additional fields, and generates a list of MapElementWrappers with them
     * @param model  the domain object
     */
    protected List<MapElementWrapper> createElementWrappers(AdditionalFields model) {

        if (model.getAdditionalFields() != null && !model.getAdditionalFields().isEmpty()) {
            List<MapElementWrapper> mapElementWrappers = new ArrayList<MapElementWrapper>();
            for (String key : model.getAdditionalFields().keySet()) {
                MapElementWrapper mapElementWrapper = new MapElementWrapper();
                mapElementWrapper.setKey(key);
                mapElementWrapper.setValue(model.getAdditionalFields().get(key));
                mapElementWrappers.add(mapElementWrapper);
            }
            return mapElementWrappers;
        }
        return null;

    }

    /**
     * Used method, to be used by Wrappers that implement the WrapperAdditionalFields interface.
     * Transfers the additional fields from the wrapper into the domain object
     * @param model
     * @param me
     */
    public void transferAdditionalFieldsFromWrapper(WrapperAdditionalFields from, AdditionalFields to) {
        Map<String, String> destination = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(from.getAdditionalFields())) {
            for (MapElementWrapper elem : from.getAdditionalFields()) {
                destination.put(elem.key, elem.value);
            }
        }
        to.setAdditionalFields(destination);
    }

}
