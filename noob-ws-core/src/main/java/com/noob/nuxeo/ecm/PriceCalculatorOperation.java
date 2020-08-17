package com.noob.nuxeo.ecm;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.runtime.api.Framework;

/**
 *
 */
@Operation(id=PriceCalculatorOperation.ID, category=Constants.CAT_DOCUMENT, label="Operation to calculate total price", description="It calculates the total price for a product.")
public class PriceCalculatorOperation {

    public static final String ID = "Document.PriceCalculatorOperation";

    @Context
    protected CoreSession session;
    
    @OperationMethod
    public void run(DocumentModelList docs) {
    	PriceCalculatorService priceCalculatorService = Framework.getService(PriceCalculatorService.class);
    	for (DocumentModel documentModel : docs) {
    		documentModel.setPropertyValue("Exo:price", priceCalculatorService.computePrice(documentModel));
    		session.saveDocument(documentModel);
    		session.save();
		}
    }
    
//    
//    @OperationMethod
//    public void updatePrice(List<DocumentModel> documents) {
//    	PriceCalculatorService priceCalculatorService = Framework.getService(PriceCalculatorService.class);
//    	for (DocumentModel documentModel : documents) {
//    		documentModel.setPropertyValue("Exo:price", priceCalculatorService.computePrice(documentModel));
//    		session.saveDocument(documentModel);
//    		session.save();
//		}
//    }
}
