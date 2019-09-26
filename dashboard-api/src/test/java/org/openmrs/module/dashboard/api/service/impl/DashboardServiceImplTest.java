package org.openmrs.module.dashboard.api.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.module.dashboard.api.loader.ConfigLoader;
import org.openmrs.module.dashboard.api.model.DashboardPrivileges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
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

    @Test
    public void shouldNotGetDashboardIfPrivilegeDoesNotMatch() throws IOException {
        DashboardPrivileges dashboardPrivileges = new DashboardPrivileges("[\n" +
                "   {\n" +
                "     \"dashboardName\" : \"dashboard_config\",\n" +
                "     \"requiredPrivileges\" : [\"doc\"]\n" +
                "   }\n" +
                " ]");

        when(mockConfigLoader.getDashboardPrivileges()).thenReturn(dashboardPrivileges);
        when(mockConfigLoader.readAllFilesFromAppDataDirectory(any())).thenReturn(new ArrayList<>());

        ArrayList<Object> acctualDashboardConfig = dashboardService.getConfigurationByPrivileges();
        assertEquals(0, acctualDashboardConfig.size());
        verify(mockConfigLoader).readAllFilesFromAppDataDirectory(new ArrayList<>());
    }
}