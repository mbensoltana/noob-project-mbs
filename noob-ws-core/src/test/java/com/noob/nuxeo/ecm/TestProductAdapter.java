package com.noob.nuxeo.ecm;

import javax.inject.Inject;

import org.junit.Assert;
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

import com.noob.nuxeo.ecm.ProductAdapter;
import com.noob.nuxeo.ecm.extensions.PriceCalculatorTargetExtension;

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy("com.noob.nuxeo.ecm.noob-ws-core")
@Deploy("org.nuxeo.ecm.automation.core")
@PartialDeploy(bundle = "studio.extensions.mbensoltana-SANDBOX", extensions = { PriceCalculatorTargetExtension.class, TargetExtensions.ContentModel.class })
public class TestProductAdapter {
  @Inject
  CoreSession session;

  @Test
  public void shouldCallTheAdapter() {
    String doctype = "product";
    String testTitle = "My Adapter Title";

    DocumentModel doc = session.createDocumentModel("/", "test-adapter", doctype);
    ProductAdapter adapter = doc.getAdapter(ProductAdapter.class);
    adapter.setTitle(testTitle);
    Assert.assertEquals("Document title does not match when using the adapter", testTitle, adapter.getTitle());
  }
}
