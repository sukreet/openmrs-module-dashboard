package org.openmrs.module.dashboard.api.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.module.dashboard.api.loader.Config;
import org.openmrs.module.dashboard.api.model.Dashboard;
import org.openmrs.module.dashboard.api.model.DashboardConfig;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DashboardServiceImplTest {
    private DashboardServiceImpl dashboardService;

    @Mock
    private Config mockDashboardConfig;


    @Before
    public void setUp() throws IOException {
        initMocks(this);
        this.dashboardService = new DashboardServiceImpl(mockDashboardConfig);
    }

    @Test
    public void shouldFindASampleDashboard() {
//        JSONArray sample = new JSONArray("[{\"name\":\"dashboard name\"}]");
        DashboardConfig config = new DashboardConfig();
        Dashboard dashboard = new Dashboard();
        dashboard.setName("dashboard name");
        ArrayList<Dashboard> dashboards = new ArrayList<>();
        dashboards.add(dashboard);
        config.setDashboards(dashboards);
        when(mockDashboardConfig.readDashboardConfig("dashboard_config.json")).thenReturn(config);

        DashboardConfig dashboardConfig = dashboardService.find();

        assertEquals("dashboard name", dashboardConfig.getDashboards().get(0).getName());
    }
}