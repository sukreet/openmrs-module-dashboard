package org.openmrs.module.dashboard.api.service.impl;

import org.openmrs.module.dashboard.api.loader.ConfigLoader;
import org.openmrs.module.dashboard.api.model.DashboardPrivileges;
import org.openmrs.module.dashboard.api.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ArrayList<Object> getConfigurationByPrivileges() {
        ArrayList<Object> dashboardConfigurations = new ArrayList<>();
        try {
            DashboardPrivileges dashboardPrivileges = configLoader.getDashboardPrivileges();
            // todo: get logged in user privileges and call the method
            ArrayList<String> currentUserPrivileges = new ArrayList<>(Arrays.asList("provider"));
            ArrayList<String> dashboardNames = dashboardPrivileges.filterByPrivileges(currentUserPrivileges);
            dashboardConfigurations = configLoader.readAllFilesFromAppDataDirectory(dashboardNames);
        } catch (Exception e) {
            log.error("Failed to read configLoader files for dashboard");
            e.printStackTrace();
        }

        return dashboardConfigurations;
    }

}
