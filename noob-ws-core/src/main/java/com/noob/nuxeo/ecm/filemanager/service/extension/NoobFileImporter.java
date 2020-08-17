package com.noob.nuxeo.ecm.filemanager.service.extension;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.platform.filemanager.api.FileImporterContext;
import org.nuxeo.ecm.platform.filemanager.service.extension.AbstractFileImporter;

public class NoobFileImporter extends AbstractFileImporter {

	private static final long serialVersionUID = 1L;

	private static final String docType = "visual";
	
	@Override
	public DocumentModel createOrUpdate(FileImporterContext context) throws NuxeoException {
		DocumentModel doc = createDocType(context);
		return doc;
	}

	protected DocumentModel createDocType(FileImporterContext context) {
		CoreSession session = context.getSession();
		Blob blob = context.getBlob();
		String fileName = StringUtils.defaultIfBlank(context.getFileName(), blob.getFilename());
		blob.setFilename(fileName);		
		DocumentModel doc = session.createDocumentModel(docType);
		doc.setPropertyValue("dc:title", docType + "_" + fileName);
		doc.setPropertyValue("file:content", (Serializable) blob);
		return doc;
	}
}
