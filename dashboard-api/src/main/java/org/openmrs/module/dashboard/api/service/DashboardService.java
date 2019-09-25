package org.openmrs.module.dashboard.api.service;

import org.openmrs.module.dashboard.api.model.DashboardConfig;

import java.util.ArrayList;

public interface DashboardService {
    DashboardConfig find();

    ArrayList<String> getConfigurationByPrivileges();
}
