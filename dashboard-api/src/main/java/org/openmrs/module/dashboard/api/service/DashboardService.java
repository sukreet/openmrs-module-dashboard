package org.openmrs.module.dashboard.api.service;

import org.openmrs.module.dashboard.api.model.Dashboards;

import java.util.ArrayList;

public interface DashboardService {
    Dashboards find();

    ArrayList<Object> getConfigurationByPrivileges();
}
