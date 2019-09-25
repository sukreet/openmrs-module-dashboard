package org.openmrs.module.dashboard.api.service.impl;

import org.openmrs.api.context.Context;
import org.openmrs.module.dashboard.api.loader.ConfigLoader;
import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.module.dashboard.api.model.DashboardPrivileges;
import org.openmrs.module.dashboard.api.model.PrivilegesConfig;
import org.openmrs.module.dashboard.api.service.DashboardService;
import org.openmrs.util.OpenmrsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class DashboardServiceImpl implements DashboardService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private ConfigLoader configLoader;

    @Autowired
    public DashboardServiceImpl(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    @Override
    public DashboardConfig find() {

        DashboardConfig dashboardConfig = null;
        try {
            PrivilegesConfig privilegesConfig = configLoader.loadDashboardPrivilegeConfig("dashboard_privilege.json");
            // todo: get logged in user privileges and call the method
            ArrayList<String> dashboardNames = privilegesConfig.getDashboardsFor(new ArrayList<>(Arrays.asList("provider")));
            dashboardConfig = configLoader.loadDashboardConfig(dashboardNames.get(0) + ".json");
        } catch (IOException e) {
            log.error("Failed to read configLoader files for dashboard");
            e.printStackTrace();
        }

        return dashboardConfig;
    }

    @Override
    public String getConfigurationByPrivileges() {
        String dashboardConfiguration = "Sample Config";
        try {
            DashboardPrivileges dashboardPrivileges = configLoader.getDashboardPrivileges();
            ArrayList<String> currentUserPrivileges= new ArrayList<>(Arrays.asList("provider"));
            ArrayList<String> dashboardNames = dashboardPrivileges.filterByPrivileges(currentUserPrivileges);
            dashboardConfiguration = configLoader.readFileFromAppDir(dashboardNames.get(0) + ".json");
        } catch (Exception e) {
            log.error("Failed to read configLoader files for dashboard");
            e.printStackTrace();
        }

        return dashboardConfiguration;
    }

}
