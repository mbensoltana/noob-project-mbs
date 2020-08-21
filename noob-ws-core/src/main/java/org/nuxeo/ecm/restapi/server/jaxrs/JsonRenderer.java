package org.nuxeo.ecm.restapi.server.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.webengine.model.WebObject;

import com.noob.nuxeo.ecm.ProductAdapter;

@WebObject(type = "workflow")
@Produces(MediaType.APPLICATION_JSON)
public class JsonRenderer {

	@Context
    protected CoreSession session;
	
	@GET
	@Path("{documentId}")
	public ProductAdapter getDocument(@PathParam("documentId") String documentId) {
		
		DocumentModel document;
        try {
        	document = session.getDocument(new IdRef(documentId));
            return document.getAdapter(ProductAdapter.class);
        } catch (NuxeoException e) {
            e.addInfo("Can not get document with id: " + documentId);
            throw e;
        }
        
	}
	
}
