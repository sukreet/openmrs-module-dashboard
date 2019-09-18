package org.openmrs.module.dashboard;

import org.openmrs.module.BaseModuleActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardActivator extends BaseModuleActivator {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void started() {
        log.info("Started the OpenMRS Dashboard module");
    }

    @Override
    public void stopped() {
        log.info("Stopped the OpenMRS Dashboard module");
    }
}
