package com.noob.nuxeo.ecm.filemanager.service.extension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.impl.blob.URLBlob;
import org.nuxeo.ecm.platform.filemanager.api.FileImporterContext;
import org.nuxeo.ecm.platform.test.PlatformFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.PartialDeploy;
import org.nuxeo.runtime.test.runner.TargetExtensions;

@RunWith(FeaturesRunner.class)
@Features(PlatformFeature.class)
@Deploy("com.noob.nuxeo.ecm.noob-ws-core")
@Deploy("org.nuxeo.ecm.platform.tag")
@Deploy("org.nuxeo.ecm.platform.rendition.core")
@Deploy("org.nuxeo.ecm.platform.picture.core")
@PartialDeploy(bundle = "studio.extensions.mbensoltana-SANDBOX", extensions = { TargetExtensions.ContentModel.class })
public class TestNoobImporter {

	public static final String PATH_WORKSPACE = "/default-domain/workspaces";
	
	@Inject
	protected CoreSession session;

	@Inject
	protected NoobFileImporter noobFileImporter;

	@Test
	public void testVisualImportType() throws Exception {
		assertNotNull(session);
		
		URL resource = getClass().getClassLoader().getResource("images/visualCSV2.jpg");
        Blob blob = new URLBlob(resource, "image/jpg");

        FileImporterContext context = FileImporterContext.builder(session, blob, "/")
                                                         .overwrite(true)
                                                         .fileName("visualCSV2.jpg")
                                                         .build();
        
        DocumentModel doc = noobFileImporter.createOrUpdate(context);
        assertEquals("visual", doc.getType());
        assertEquals("visualCSV2.jpg", doc.getProperty("file:content").getValue("name"));
	}
}
