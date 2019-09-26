package org.openmrs.module.dashboard.api.loader;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.api.AdministrationService;
import org.openmrs.module.dashboard.api.model.DashboardPrivileges;
import org.openmrs.module.dashboard.api.model.Dashboards;
import org.openmrs.util.OpenmrsUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConfigLoaderTest {
    private ConfigLoader configLoader;
    private static final String PRIVILEGES_CONFIG_FILE_NAME = "dashboard_privileges.json";

    @Mock
    private AdministrationService mockAdministrationService;

    @Before
    public void setUp()  {
        initMocks(this);
        configLoader = new ConfigLoader(mockAdministrationService);
    }

    @Test(expected = Test.None.class)
    public void shouldReadDashboardConfig() throws IOException {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();

        File configFile = new File(applicationDataDirectory, "dashboard_config.json");
        FileUtils.writeStringToFile(configFile, "{\"name\": \"test dashboard\"}");
        configFile.deleteOnExit();

        Dashboards dashboardConfig = configLoader.loadDashboardConfig("dashboard_config.json");
        assertNotNull(dashboardConfig.getDashboards());
    }

    @Test(expected = Test.None.class)
    public void shouldNotThrowExceptionIfDashboardConfigFileNotFound() throws IOException {
        Dashboards dashboardConfig = configLoader.loadDashboardConfig("dashboard_config_missing.json");
        assertNotNull(dashboardConfig.getDashboards().get(0));
    }


    @Test(expected = Test.None.class)
    public void shouldReadPrivilegeConfig() throws Exception {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();
        File privilegeFile = new File(applicationDataDirectory, PRIVILEGES_CONFIG_FILE_NAME);
        FileUtils.writeStringToFile(privilegeFile, "[\n" +
                "   {\n" +
                "     \"dashboardName\" : \"dashboard_config\",\n" +
                "     \"requiredPrivileges\" : [\"provider\"]\n" +
                "   }\n" +
                " ]");
        privilegeFile.deleteOnExit();

        when(mockAdministrationService.getGlobalProperty(anyString())).thenReturn(null);
        DashboardPrivileges dashboardPrivileges = configLoader.getDashboardPrivileges();

        assertNotNull(dashboardPrivileges);
        assertEquals(1, dashboardPrivileges.getDashboardPrivileges().size());
    }

    @Test(expected = JsonParseException.class)
    public void shouldThrowExceptionIfPrivilegeConfigIsNotInValidFormat() throws Exception {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();
        File privilegeFile = new File(applicationDataDirectory, "dashboard_privileges.json");
        FileUtils.writeStringToFile(privilegeFile, "some content");

        when(mockAdministrationService.getGlobalProperty(anyString())).thenReturn(null);

        privilegeFile.deleteOnExit();
        configLoader.getDashboardPrivileges();
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowExceptionIfPrivilegeConfigFileIsMissing() throws Exception {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();
        File privilegeFile = new File(applicationDataDirectory, "dashboard_privileges.json");

        privilegeFile.delete();
        configLoader.getDashboardPrivileges();
    }

    @Test(expected = Test.None.class)
    public void shouldReadAllFilesFromAppDataDir() throws IOException {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();

        File dashboardOne = new File(applicationDataDirectory, "dashboard_one.json");
        FileUtils.writeStringToFile(dashboardOne, "{\"name\": \"test dashboard\"}");
        dashboardOne.deleteOnExit();

        File dashboardTwo = new File(applicationDataDirectory, "dashboard_two.json");
        FileUtils.writeStringToFile(dashboardTwo, "{\"name\": \"test dashboard two\"}");
        dashboardTwo.deleteOnExit();

        ArrayList<String> dashboardNames = new ArrayList<>(Arrays.asList("dashboard_one", "dashboard_two"));
        ArrayList<Object> dashboards = configLoader.readAllFilesFromAppDataDirectory(dashboardNames);

        assertNotNull(dashboards);
        assertEquals(2, dashboards.size());
        assertEquals("{name=test dashboard}", dashboards.get(0).toString());
        assertEquals("{name=test dashboard two}", dashboards.get(1).toString());
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowExceptionIfAnyFileIsMissingInAppDataDir() throws IOException {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();

        File dashboardOne = new File(applicationDataDirectory, "dashboard_one.json");
        FileUtils.writeStringToFile(dashboardOne, "{\"name\": \"test dashboard\"}");
        dashboardOne.deleteOnExit();

        ArrayList<String> dashboardNames = new ArrayList<>(Arrays.asList("dashboard_one", "dashboard_two"));
        configLoader.readAllFilesFromAppDataDirectory(dashboardNames);
    }

    @Test
    public void shouldReturnEmptyListIfNoFileNamesArePassed() throws IOException {
        ArrayList<String> dashboardNames = new ArrayList<>();
        ArrayList<Object> dashboards = configLoader.readAllFilesFromAppDataDirectory(dashboardNames);

        assertEquals(0, dashboards.size());
    }
}