package com.noob.nuxeo.ecm.extensions;

import org.nuxeo.runtime.test.runner.TargetExtensions;

public class PriceCalculatorExtension extends TargetExtensions {
    @Override
    protected void initialize() {
        addTargetExtension("org.nuxeo.ecm.core.operation.OperationServiceComponent", "operations");
    }
}
