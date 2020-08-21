package com.noob.nuxeo.ecm.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.EventService;
import org.nuxeo.ecm.core.event.impl.EventListenerDescriptor;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy("com.noob.nuxeo.ecm.noob-ws-core")
public class TestJsonRenderer {

    protected final List<String> events = Arrays.asList("addedevent", "changedevent", "notsoldanymoreevent");

    @Inject
    protected EventService s;

    @Inject
    CoreSession coreSession;
    
    @Inject
    protected CoreFeature coreFeature;
    
    @Test
    public void listenerRegistration() {
        EventListenerDescriptor listener = s.getEventListener("nooblistener");
        assertNotNull(listener);
        assertTrue(events.stream().allMatch(listener::acceptEvent));
    }
    
    
    @Test
    public void shouldFindProduct()
      throws ClientProtocolException, IOException {
     
    	DocumentModel product = coreSession.createDocumentModel("/", "Product", "product");
    	product = coreSession.createDocument(product);
    	coreSession.save();
    	
    	
    	CredentialsProvider provider = new BasicCredentialsProvider();
    	UsernamePasswordCredentials credentials
    	 = new UsernamePasswordCredentials("Administrator", "Administrator");
    	provider.setCredentials(AuthScope.ANY, credentials);
    	
        // Given
        HttpUriRequest request = new HttpGet( "http://localhost:8080/nuxeo/api/v1/workflow/" + product.getId() );
        
        // When
        HttpResponse httpResponse = HttpClientBuilder.create()
        		  .setDefaultCredentialsProvider(provider)
        		  .build().execute( request );
     
        System.out.println(httpResponse.getStatusLine().getStatusCode());
        
        // Then
        assertTrue(
          httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED);
    }
}
