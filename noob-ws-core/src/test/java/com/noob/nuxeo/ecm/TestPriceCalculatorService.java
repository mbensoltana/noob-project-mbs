package com.noob.nuxeo.ecm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.PartialDeploy;
import org.nuxeo.runtime.test.runner.TargetExtensions;


@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy("org.nuxeo.ecm.automation.core")
@Deploy({
	"com.noob.nuxeo.ecm.noob-ws-core",
	"org.nuxeo.ecm.directory",
	"org.nuxeo.ecm.platform.filemanager.api",
	"org.nuxeo.ecm.platform.filemanager.core",
	"org.nuxeo.ecm.platform.query.api",
    "com.noob.nuxeo.ecm.noob-ws-core.tests:test-extension-config.xml"
})
@PartialDeploy(bundle = "studio.extensions.mbensoltana-SANDBOX", extensions = { TargetExtensions.ContentModel.class })
public class TestPriceCalculatorService {

    @Inject
    protected PriceCalculatorService pricecalculatorservice;
    
    @Inject
    protected CoreSession coreSession;

    @Test
    public void testService() {
    	
    	assertNotNull(pricecalculatorservice);
    	assertNotNull(coreSession);
    	
    	DocumentModel visual1 = coreSession.createDocumentModel("/", "Visual 1", "visual");
    	visual1.setPropertyValue("Exo:price", 100);
    	visual1 = coreSession.createDocument(visual1);
    	coreSession.save();
    	
    	DocumentModel visual2 = coreSession.createDocumentModel("/", "Visual 2", "visual");
    	visual2.setPropertyValue("Exo:price", 200);
    	visual2 = coreSession.createDocument(visual2);
    	coreSession.save();
    	
    	DocumentModel product = coreSession.createDocumentModel("/", "Product", "product");
    	List<String> idsVisuals = new ArrayList<String>();
    	idsVisuals.add(visual1.getId());
    	idsVisuals.add(visual2.getId());
    	product.setPropertyValue("collection:documentIds", (Serializable) idsVisuals);
    	product = coreSession.createDocument(product);
    	coreSession.save();
    	
    	Double totalPrice = pricecalculatorservice.computePrice(product);
    	assertEquals(totalPrice.doubleValue(), 300d, 0.001);
    }
}
