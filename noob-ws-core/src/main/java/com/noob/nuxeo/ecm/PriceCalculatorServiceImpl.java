package com.noob.nuxeo.ecm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.runtime.model.ComponentContext;
import org.nuxeo.runtime.model.ComponentInstance;
import org.nuxeo.runtime.model.DefaultComponent;

import com.noob.nuxeo.ecm.annotations.PriceCalculatorDescriptor;

public class PriceCalculatorServiceImpl extends DefaultComponent implements PriceCalculatorService {

	private HashMap<String, PriceCalculatorDescriptor> settings;
	
    /**
     * Component activated notification.
     * Called when the component is activated. All component dependencies are resolved at that moment.
     * Use this method to initialize the component.
     *
     * @param context the component context.
     */
    @Override
    public void activate(ComponentContext context) {
        settings = new HashMap<String, PriceCalculatorDescriptor>();
    }

    /**
     * Component deactivated notification.
     * Called before a component is unregistered.
     * Use this method to do cleanup if any and free any resources held by the component.
     *
     * @param context the component context.
     */
    @Override
    public void deactivate(ComponentContext context) {
        settings = null;
    }

    /**
     * Application started notification.
     * Called after the application started.
     * You can do here any initialization that requires a working application
     * (all resolved bundles and components are active at that moment)
     *
     * @param context the component context. Use it to get the current bundle context
     * @throws Exception
     */
    @Override
    public void applicationStarted(ComponentContext context) {
        // do nothing by default. You can remove this method if not used.
    }

    @Override
    public void registerContribution(Object contribution, String extensionPoint, ComponentInstance contributor) {
    	PriceCalculatorDescriptor settingsObject = (PriceCalculatorDescriptor) contribution;
    	if (settingsObject.settingsId != null) {
    		settings.put(settingsObject.settingsId, settingsObject);
    	} else {
    		throw new NuxeoException("Price settings without id");
    	}

    }

    @Override
    public void unregisterContribution(Object contribution, String extensionPoint, ComponentInstance contributor) {
        // Logic to do when unregistering any contribution
    }

	@SuppressWarnings("unchecked")
	@Override
	public Double computePrice(DocumentModel document) {
		double totalPrice = 0L;
		CoreSession session = document.getCoreSession();
		List<String> idsVisuals = new ArrayList<String>();
		idsVisuals = (List<String>) document.getPropertyValue("collection:documentIds");
		
		for (String settingsId : settings.keySet()) {
			PriceCalculatorDescriptor settingsDescriptor = (PriceCalculatorDescriptor) settings.get(settingsId);
			for (String visualId : idsVisuals) {
				DocumentModel visual = session.getDocument(new IdRef(visualId));
				totalPrice+= (Double) visual.getPropertyValue("Exo:price") * settingsDescriptor.getVatValue() + settingsDescriptor.getCharge();
			}
		}
		
		return totalPrice;
	}
}
