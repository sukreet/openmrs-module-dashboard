package org.openmrs.module.dashboard.api.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.dashboard.api.model.DashboardConfig;

import static org.junit.Assert.assertEquals;

public class DashboardServiceImplTest {
    private DashboardServiceImpl dashboardService;

    @Before
    public void setUp() {
        this.dashboardService = new DashboardServiceImpl();
    }

    @Test
    public void shouldFindASamplePatient() {
        DashboardConfig config = dashboardService.find();

        assertEquals("sample dashboard", config.getName());
        assertEquals("dashboard details", config.getDetails());
    }
}