package org.openmrs.module.dashboard.api.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dashboards {

    private ArrayList<Object> dashboards;

    public Dashboards(ArrayList<Object> dashboards) {
        this.dashboards = dashboards;
    }

    public Dashboards() {

    }

    public ArrayList<Object> getDashboards() {
        return dashboards;
    }

    public void setDashboards(ArrayList<Object> dashboards) {
        this.dashboards = dashboards;
    }

    public void add(Object o) {
        this.dashboards.add(o);
    }
}