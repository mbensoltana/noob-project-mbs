package com.noob.nuxeo.ecm;

import org.codehaus.jackson.map.ObjectMapper;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;

import java.io.IOException;

/**
 *
 */
@Operation(id = NoobAddDistributorOperation.ID, category = Constants.CAT_DOCUMENT, label = "Adds a distributor to a product", description = "Describe here what your operation does.")
public class NoobAddDistributorOperation {

    private ObjectMapper objectMapper = new ObjectMapper();

    public static final String ID = "Document.NoobAddDistributorOperation";

    @Context
    protected CoreSession session;

    @OperationMethod
    public String run(DocumentModel doc) throws IOException {
        return objectMapper.writeValueAsString(doc.getAdapter(ProductAdapter.class));
    }
}
