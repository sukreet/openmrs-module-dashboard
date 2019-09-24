package org.openmrs.module.dashboard.api.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DashboardConfig {

    @JsonProperty("dashboards")
    private ArrayList<Dashboard> dashboards;

    public DashboardConfig() {
    }

    public ArrayList<Dashboard> getDashboards() {
        return dashboards;
    }

    public void setDashboards(ArrayList<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }
}