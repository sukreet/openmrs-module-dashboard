package org.openmrs.module.dashboard.api.model;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DashboardPrivileges {
    private ArrayList<DashboardPrivilege> dashboardPrivileges;

    public DashboardPrivileges(String dashboardPrivilegesFileContent) throws Exception {
        this.dashboardPrivileges = fromJSON(new TypeReference<ArrayList<DashboardPrivilege>>() {
        }, dashboardPrivilegesFileContent);
    }

    public static <T> T fromJSON(final TypeReference<T> type,
                                 final String jsonPacket) throws Exception {
        T data = null;
        try {
            data = new ObjectMapper().readValue(jsonPacket, type);
        } catch (Exception e) {
            throw new Exception("Privileges file do not have expected format.");
        }
        return data;
    }

    public ArrayList<DashboardPrivilege> getDashboardPrivileges() {
        return dashboardPrivileges;
    }

    public DashboardPrivileges() {
    }

    public ArrayList<String> filterByPrivileges(ArrayList<String> currentUserPrivileges) {
        return (ArrayList<String>) dashboardPrivileges.stream().
                filter(d -> d.isDashboardApplicable(currentUserPrivileges)).
                map(d -> d.getDashboardName()).
                collect(Collectors.toList());
    }

//    public DashboardPrivileges(ArrayList<DashboardPrivilege> dashboardPrivileges) {
//        this.dashboardPrivileges = dashboardPrivileges;
//    }

    public void setDashboardPrivileges(ArrayList<DashboardPrivilege> dashboardPrivileges) {
        this.dashboardPrivileges = dashboardPrivileges;
    }

//    public ArrayList<String> getDashboardsFor(ArrayList<String> userPrivileges) {
//
//        return (ArrayList<String>) dashboardPrivileges.stream().
//                filter(d -> d.isDashboardApplicable(userPrivileges)).
//                map(d -> d.getDashboardName()).
//                collect(Collectors.toList());
//    }
}
