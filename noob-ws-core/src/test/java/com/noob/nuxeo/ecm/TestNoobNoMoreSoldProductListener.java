package com.noob.nuxeo.ecm;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.event.EventService;
import org.nuxeo.ecm.core.event.impl.EventListenerDescriptor;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import com.noob.nuxeo.ecm.features.NoobTestFeature;

@RunWith(FeaturesRunner.class)
@Features({ NoobTestFeature.class })
public class TestNoobNoMoreSoldProductListener {

    protected final List<String> events = Arrays.asList("documentModified");

    @Inject
    protected EventService s;

    @Inject
    CoreSession coreSession;
    
    @Inject
    protected CoreFeature coreFeature;

    @Test
    public void listenerRegistration() {
        EventListenerDescriptor listener = s.getEventListener("noobnomoresoldproductlistener");
        assertNotNull(listener);
        assertTrue(events.stream().allMatch(listener::acceptEvent));
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void shouldCopyAllVisualsIntoHiddenFolder() {
    	
    	DocumentModel folder = coreSession.createDocumentModel("/", "Hidden", "Folder");
    	folder = coreSession.createDocument(folder);
    	
        // GIVEN
    	DocumentModel visual1 = coreSession.createDocumentModel("/", "Visual 1", "visual");
    	visual1.setPropertyValue("Exo:price", 200);
    	visual1 = coreSession.createDocument(visual1);
    	
    	DocumentModel visual2 = coreSession.createDocumentModel("/", "Visual 2", "visual");
    	visual2.setPropertyValue("Exo:price", 200);
    	visual2 = coreSession.createDocument(visual2);
    	
    	DocumentModel product = coreSession.createDocumentModel("/", "Product", "product");
    	List<String> idsVisuals = new ArrayList<String>();
    	idsVisuals.add(visual1.getId());
    	idsVisuals.add(visual2.getId());
    	product.setPropertyValue("collection:documentIds", (Serializable) idsVisuals);
    	product = coreSession.createDocument(product);
    	product.setPropertyValue("Exo:available", (Serializable) true);
    	product = coreSession.saveDocument(product);
    	
    	// When
    	product.setPropertyValue("Exo:available", (Serializable) false);
    	product = coreSession.saveDocument(product);
    	
    	// then
		idsVisuals = (List<String>) product.getPropertyValue("collection:documentIds");
		for (String visualId : idsVisuals) {
			DocumentModel visual = coreSession.getDocument(new IdRef(visualId));
			assertTrue(visual.getPathAsString().startsWith("/Hidden"));
		}
    }
    
}
