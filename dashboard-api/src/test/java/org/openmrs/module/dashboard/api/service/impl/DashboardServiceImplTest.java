package org.openmrs.module.dashboard.api.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.module.dashboard.api.loader.ConfigLoader;
import org.openmrs.module.dashboard.api.model.DashboardPrivilege;
import org.openmrs.module.dashboard.api.model.DashboardPrivileges;
import org.openmrs.module.dashboard.api.model.Dashboards;
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

        Dashboards config = new Dashboards();
        config.setDashboards(new ArrayList<>(Arrays.asList("dashboard name")));
        when(mockConfigLoader.loadDashboardConfig("dashboard_config.json")).thenReturn(config);
        Dashboards dashboardConfig = dashboardService.find();

        assertEquals("dashboard name", dashboardConfig.getDashboards().get(0));
    }

    @Test
    public void shouldFindADashboard() throws IOException {
        DashboardPrivileges dashboardPrivileges = new DashboardPrivileges("[\n" +
                "   {\n" +
                "     \"dashboardName\" : \"dashboard_config\",\n" +
                "     \"requiredPrivileges\" : [\"provider\"]\n" +
                "   }\n" +
                " ]");

        when(mockConfigLoader.getDashboardPrivileges()).thenReturn(dashboardPrivileges);
        ArrayList<String> fileNames = new ArrayList<>(Arrays.asList("dashboard_config"));
        ArrayList<Object> dashboards = new ArrayList<>(Arrays.asList("dashboard one", "dashboard two"));
        when(mockConfigLoader.readAllFilesFromAppDataDirectory(fileNames)).thenReturn(dashboards);

        ArrayList<Object> dashboardConfig = dashboardService.getConfigurationByPrivileges();
        assertEquals("dashboard one", dashboardConfig.get(0));
        assertEquals("dashboard two", dashboardConfig.get(1));
    }
}