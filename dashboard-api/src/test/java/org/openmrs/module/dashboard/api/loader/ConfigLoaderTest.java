package org.openmrs.module.dashboard.api.loader;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.module.dashboard.api.model.DashboardPrivileges;
import org.openmrs.module.dashboard.api.model.PrivilegesConfig;
import org.openmrs.util.OpenmrsUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConfigLoaderTest {
    private ConfigLoader configLoader;
    private static final String PRIVILEGES_CONFIG_FILE_NAME = "dashboard_privileges.json";
    ;

    @Before
    public void setUp() throws Exception {
        configLoader = new ConfigLoader();
    }

    @Test(expected = Test.None.class)
    public void shouldReadDashboardConfig() throws IOException {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();

        File configFile = new File(applicationDataDirectory, "dashboard_config.json");
        FileUtils.writeStringToFile(configFile, "{\"name\": \"test dashboard\"}");
        configFile.deleteOnExit();

        DashboardConfig dashboardConfig = configLoader.loadDashboardConfig("dashboard_config.json");
        assertNotNull(dashboardConfig.getDashboards());
    }

    @Test(expected = Test.None.class)
    public void shouldNotThrowExceptionIfDashboardConfigFileNotFound() throws IOException {
        DashboardConfig dashboardConfig = configLoader.loadDashboardConfig("dashboard_config_missing.json");
        assertNotNull(dashboardConfig.getDashboards().get(0));
    }

    @Test(expected = Test.None.class)
    public void shouldReadPrivilegeConfig() throws Exception {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();
        File privilegeFile = new File(applicationDataDirectory, PRIVILEGES_CONFIG_FILE_NAME);

        FileUtils.writeStringToFile(privilegeFile, "{\n" +
                "     \"dashboardName\" : \"dashboard_config\",\n" +
                "     \"requiredPrivileges\" : [\"provider\"]\n" +
                "   }\n" +
                " ]\n");

        privilegeFile.deleteOnExit();
        DashboardPrivileges dashboardPrivileges = configLoader.getDashboardPrivileges();
        assertNotNull(dashboardPrivileges);
        assertEquals(1, dashboardPrivileges.getDashboardPrivileges().size());
    }

    @Test(expected = JsonParseException.class)
    public void shouldThrowExceptionIfPrivilegeConfigIsNotInValidFormat() throws IOException {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();
        File privilegeFile = new File(applicationDataDirectory, "dashboard_privilege.json");
        FileUtils.writeStringToFile(privilegeFile, "some content");
        privilegeFile.deleteOnExit();

        PrivilegesConfig privilegesConfig = configLoader.loadDashboardPrivilegeConfig("dashboard_privilege.json");
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowExceptionIfPrivilegeConfigFileIsMissing() throws IOException {
        configLoader.loadDashboardPrivilegeConfig("dashboard_privilege_missing.json");
    }
}