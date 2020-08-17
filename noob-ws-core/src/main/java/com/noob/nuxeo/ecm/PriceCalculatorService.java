package com.noob.nuxeo.ecm;

import org.nuxeo.ecm.core.api.DocumentModel;

public interface PriceCalculatorService {
	/**
     * Returns a totalPrice for a product
     *
     * @param a document
     */
	Double computePrice(DocumentModel document);
}
