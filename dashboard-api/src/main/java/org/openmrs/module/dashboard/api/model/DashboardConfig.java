package org.openmrs.module.dashboard.api.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DashboardConfig {

    private ArrayList<Object> dashboards;

    public DashboardConfig(ArrayList<Object> dashboards) {
        this.dashboards = dashboards;
    }

    public DashboardConfig() {

    }

    public ArrayList<Object> getDashboards() {
        return dashboards;
    }

    public void setDashboards(ArrayList<Object> dashboards) {
        this.dashboards = dashboards;
    }
}