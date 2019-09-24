package org.openmrs.module.dashboard.api.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PrivilegesConfigTest {


    @Test
    public void shouldGetPrivilegesForADashboard() {

        DashboardPrivilege privilegeMapOne = new DashboardPrivilege("dashboard_one", new ArrayList<>(Arrays.asList("p1", "p2")));
        DashboardPrivilege privilegeMapTwo = new DashboardPrivilege("dashboard_two", new ArrayList<>(Arrays.asList("p3", "p4")));
        PrivilegesConfig privilegesConfig = new PrivilegesConfig(new ArrayList<>(Arrays.asList(privilegeMapOne, privilegeMapTwo)));

        ArrayList<String> userPrivileges = new ArrayList<>(Arrays.asList("p2", "p1"));
        ArrayList<String> dashboardsNames = privilegesConfig.getDashboardsFor(userPrivileges);

        assertEquals(1, dashboardsNames.size());
        assertEquals("dashboard_one", dashboardsNames.get(0));

    }
}