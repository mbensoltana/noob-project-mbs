package com.noob.nuxeo.ecm.features;

import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.PartialDeploy;
import org.nuxeo.runtime.test.runner.RunnerFeature;
import org.nuxeo.runtime.test.runner.TargetExtensions;

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy({
	"org.nuxeo.ecm.automation.core",
	"com.noob.nuxeo.ecm.noob-ws-core",
	"org.nuxeo.ecm.directory",
	"org.nuxeo.ecm.platform.filemanager.api",
	"org.nuxeo.ecm.platform.filemanager.core",
	"org.nuxeo.ecm.platform.query.api",
    "com.noob.nuxeo.ecm.noob-ws-core.tests:test-extension-config.xml"
})
@PartialDeploy(bundle = "studio.extensions.mbensoltana-SANDBOX", extensions = { TargetExtensions.ContentModel.class })
public class NoobTestFeature implements RunnerFeature {

}
