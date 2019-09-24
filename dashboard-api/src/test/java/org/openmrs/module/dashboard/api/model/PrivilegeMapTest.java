package org.openmrs.module.dashboard.api.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrivilegeMapTest {

    @Test
    public void shouldReturnFalseIfDoesnotContainsUserPrivilege() {
        DashboardPrivilege privilegeMap = new DashboardPrivilege("dashboard_one", new ArrayList<>(Arrays.asList("p1", "p2")));

        assertFalse(privilegeMap.isDashboardApplicable(new ArrayList<>(Arrays.asList("p3", "p4"))));
    }


    @Test
    public void shouldReturnTrueIfContainsPrivilege() {
        DashboardPrivilege privilegeMap = new DashboardPrivilege("dashboard_one", new ArrayList<>(Arrays.asList("p1", "p2")));

        assertTrue(privilegeMap.isDashboardApplicable(new ArrayList<>(Arrays.asList("p1"))));
        assertTrue(privilegeMap.isDashboardApplicable(new ArrayList<>(Arrays.asList("p2"))));
        assertTrue(privilegeMap.isDashboardApplicable(new ArrayList<>(Arrays.asList("p1", "p2"))));
        assertTrue(privilegeMap.isDashboardApplicable(new ArrayList<>(Arrays.asList("p1", "p2", "p3"))));
    }
}