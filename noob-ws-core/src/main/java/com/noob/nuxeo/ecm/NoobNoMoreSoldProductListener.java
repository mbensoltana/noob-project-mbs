package com.noob.nuxeo.ecm;


import java.util.List;

import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;

import com.nuxeo.studio.StudioConstant;

public class NoobNoMoreSoldProductListener implements EventListener {

	private static final String COLLECTION_DOCUMENT_IDS = "collection:documentIds";

	private static final String PRODUCT = "product";

	private static final String EXO_AVAILABLE = "Exo:available";
	
	protected final String path = "Hidden";
	
	@Override
    public void handleEvent(Event event) {
    	EventContext ctx = event.getContext();
        if (!(ctx instanceof DocumentEventContext)) {
          return;
        }

        DocumentEventContext docCtx = (DocumentEventContext) ctx;
        DocumentModel doc = docCtx.getSourceDocument();
        // if docType match and if doc have target property 
        if (StudioConstant.PRODUCT_DOC_TYPE.equals(doc.getType()) && doc.getProperty(EXO_AVAILABLE) != null) {
        	Boolean available = (Boolean) doc.getPropertyValue(EXO_AVAILABLE);
            if (!available) {
            	
        		CoreSession session = doc.getCoreSession();
        		@SuppressWarnings("unchecked")
				List<String> idsVisuals = (List<String>) doc.getPropertyValue(COLLECTION_DOCUMENT_IDS);
        		
    			for (String visualId : idsVisuals) {
    				DocumentModel visual = session.getDocument(new IdRef(visualId));
    				// move the document
    				session.move(visual.getRef(), new PathRef(path), visual.getName());
    				session.save();
    			}
            }
        }
    }
}
