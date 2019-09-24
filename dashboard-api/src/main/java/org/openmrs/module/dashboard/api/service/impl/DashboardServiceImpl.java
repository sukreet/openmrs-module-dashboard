package org.openmrs.module.dashboard.api.service.impl;

import org.openmrs.module.dashboard.api.loader.Config;
import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.module.dashboard.api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private Config config;

    @Autowired
    public DashboardServiceImpl(Config config) {
        this.config = config;
    }

    @Override
    public DashboardConfig find() {

        DashboardConfig dashboardConfig = config.readDashboardConfig("dashboard_config.json");

        return dashboardConfig;
    }
}
