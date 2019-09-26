package org.openmrs.module.dashboard.api.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DashboardPrivilegesTest {
    @Test
    public void shouldGetDashboardNameWhenOnlyOneDashboardPrivilegeMatches() {
        DashboardPrivileges dashboardPrivileges = new DashboardPrivileges();
        DashboardPrivilege privilegeMapOne = new DashboardPrivilege("dashboard_one", new ArrayList<>(Arrays.asList("p1", "p2")));
        DashboardPrivilege privilegeMapTwo = new DashboardPrivilege("dashboard_two", new ArrayList<>(Arrays.asList("p3", "p4")));

        dashboardPrivileges.setDashboardPrivileges(new ArrayList<>(Arrays.asList(privilegeMapOne, privilegeMapTwo)));
        ArrayList<String> dashboardNames = dashboardPrivileges.filterByPrivileges(new ArrayList<>(Arrays.asList("p2", "p1")));

        assertEquals(1, dashboardNames.size());
        assertEquals("dashboard_one", dashboardNames.get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenNoPrivilegesMatchesWithDashboardPrivileges() {
        DashboardPrivileges dashboardPrivileges = new DashboardPrivileges();
        DashboardPrivilege privilegeMapOne = new DashboardPrivilege("dashboard_one", new ArrayList<>(Arrays.asList("p1")));
        DashboardPrivilege privilegeMapTwo = new DashboardPrivilege("dashboard_two", new ArrayList<>(Arrays.asList("p3")));

        dashboardPrivileges.setDashboardPrivileges(new ArrayList<>(Arrays.asList(privilegeMapOne, privilegeMapTwo)));
        ArrayList<String> dashboardNames = dashboardPrivileges.filterByPrivileges(new ArrayList<>(Arrays.asList("p5")));

        assertEquals(0, dashboardNames.size());
    }
}