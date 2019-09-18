package org.openmrs.module.dashboard.api.service.impl;

import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.module.dashboard.api.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    public DashboardServiceImpl(){}

    @Override
    public DashboardConfig find() {
        DashboardConfig dashboardConfig = new DashboardConfig("sample dashboard", "dashboard details");
        return dashboardConfig;
    }
}
