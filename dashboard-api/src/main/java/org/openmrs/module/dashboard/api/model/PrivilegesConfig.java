package org.openmrs.module.dashboard.api.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrivilegesConfig {

    private ArrayList<DashboardPrivilege> dashboardPrivileges;

    public PrivilegesConfig() {
    }

    public PrivilegesConfig(ArrayList<DashboardPrivilege> dashboardPrivileges) {
        this.dashboardPrivileges = dashboardPrivileges;
    }

    public ArrayList<DashboardPrivilege> getDashboardPrivileges() {
        return dashboardPrivileges;
    }

    public void setDashboardPrivileges(ArrayList<DashboardPrivilege> dashboardPrivileges) {
        this.dashboardPrivileges = dashboardPrivileges;
    }

    public ArrayList<String> getDashboardsFor(ArrayList<String> userPrivileges) {

        return (ArrayList<String>) dashboardPrivileges.stream().
                filter(d -> d.isDashboardApplicable(userPrivileges)).
                map(d -> d.getDashboardName()).
                collect(Collectors.toList());
    }
}
