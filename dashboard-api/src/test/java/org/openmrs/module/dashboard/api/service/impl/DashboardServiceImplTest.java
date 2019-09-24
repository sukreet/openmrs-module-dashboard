package org.openmrs.module.dashboard.api.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.module.dashboard.api.loader.ConfigLoader;
import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.module.dashboard.api.model.DashboardPrivilege;
import org.openmrs.module.dashboard.api.model.PrivilegesConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DashboardServiceImplTest {
    private DashboardServiceImpl dashboardService;

    @Mock
    private ConfigLoader mockConfigLoader;


    @Before
    public void setUp() {
        initMocks(this);
        this.dashboardService = new DashboardServiceImpl(mockConfigLoader);
    }

    @Test
    public void shouldFindASampleDashboard() throws IOException {
        DashboardPrivilege privilegeMap = new DashboardPrivilege("dashboard_config", new ArrayList<>(Arrays.asList("provider")));
        PrivilegesConfig privilegesConfig = new PrivilegesConfig(new ArrayList<>(Arrays.asList(privilegeMap)));
        when(mockConfigLoader.loadDashboardPrivilegeConfig("dashboard_privilege.json")).thenReturn(privilegesConfig);

        DashboardConfig config = new DashboardConfig();
        config.setDashboards(new ArrayList<>(Arrays.asList("dashboard name")));
        when(mockConfigLoader.loadDashboardConfig("dashboard_config.json")).thenReturn(config);
        DashboardConfig dashboardConfig = dashboardService.find();
        assertEquals("dashboard name", dashboardConfig.getDashboards().get(0));
    }
}