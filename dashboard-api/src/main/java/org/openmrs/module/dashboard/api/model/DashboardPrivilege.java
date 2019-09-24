package org.openmrs.module.dashboard.api.model;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DashboardPrivilege {
    private String dashboardName;
    private ArrayList<String> requiredPrivileges;

    public DashboardPrivilege() {
    }

    public DashboardPrivilege(String dashboardName, ArrayList<String> requiredPrivileges) {
        this.dashboardName = dashboardName;
        this.requiredPrivileges = requiredPrivileges;
    }

    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    public ArrayList<String> getRequiredPrivileges() {
        return requiredPrivileges;
    }

    public void setRequiredPrivileges(ArrayList<String> requiredPrivileges) {
        this.requiredPrivileges = requiredPrivileges;
    }

    public boolean isDashboardApplicable(ArrayList<String> userPriviledges) {
        return !CollectionUtils.intersection(userPriviledges, this.requiredPrivileges).isEmpty();

    }
}
