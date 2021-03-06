/*******************************************************************************
 * Copyright (c) 2015 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.ibm.ws.repository.resources.internal;

import com.ibm.ws.repository.common.enums.ResourceType;
import com.ibm.ws.repository.connections.RepositoryConnection;
import com.ibm.ws.repository.exceptions.RepositoryBackendException;
import com.ibm.ws.repository.resources.RepositoryResource;
import com.ibm.ws.repository.transport.model.Asset;

/**
 *
 */
public class ResourceFactory {

    private static ResourceFactory _instance;

    // Make it private to ensure callers use getInstance
    private ResourceFactory() {

    }

    public synchronized static ResourceFactory getInstance() {
        if (_instance == null) {
            _instance = new ResourceFactory();
        }
        return _instance;
    }

    /**
     * Creates a resources from the supplied asset
     *
     * @param ass The asset to create the resource from.
     * @param userId user id to log on to massive with
     * @param password password to log on to massive with
     * @param apiKey the apikey for the marketplace
     * @return
     * @throws RepositoryBackendException
     */

    public RepositoryResourceImpl createResourceFromAsset(Asset ass, RepositoryConnection connection) throws RepositoryBackendException {

        // No wlp information means no type set, so can't create a resource from this....
        RepositoryResourceImpl result;
        if (null == ass.getWlpInformation() ||
            ass.getType() == null) {
            result = new RepositoryResourceImpl(connection, ass) {};
        } else {
            result = createResource(ass.getType(), connection, ass);
        }
        result.parseAttachmentsInAsset();
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T extends RepositoryResource> T createResource(ResourceType type, RepositoryConnection connection, Asset ass) {
        T resource = null;
        switch (type) {
            case ADMINSCRIPT:
                resource = (T) new AdminScriptResourceImpl(connection, ass);
                break;
            case CONFIGSNIPPET:
                resource = (T) new ConfigSnippetResourceImpl(connection, ass);
                break;
            case FEATURE:
                resource = (T) new EsaResourceImpl(connection, ass);
                break;
            case IFIX:
                resource = (T) new IfixResourceImpl(connection, ass);
                break;
            case ADDON:
            case INSTALL:
                ProductResourceImpl product = new ProductResourceImpl(connection, ass);
                product.setType(type);
                resource = (T) product;
                break;
            case OPENSOURCE:
            case PRODUCTSAMPLE:
                SampleResourceImpl sample = new SampleResourceImpl(connection, ass);
                sample.setType(type);
                resource = (T) sample;
                break;
            case TOOL:
                resource = (T) new ToolResourceImpl(connection, ass);
                break;
            default:
                resource = (T) new RepositoryResourceImpl(connection, ass) {};
                break;
        }
        return resource;
    }
}
