package com.noob.nuxeo.ecm.annotations;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

@XObject("settings")
public class PriceCalculatorDescriptor {

	@XNode("@id")
	public String settingsId;
	
	@XNode("vat")
	public float vatValue;
	
	@XNode(value="charge@number")
	public float charge;

	public float getVatValue() {
		return vatValue;
	}

	public float getCharge() {
		return charge;
	}
	
}
