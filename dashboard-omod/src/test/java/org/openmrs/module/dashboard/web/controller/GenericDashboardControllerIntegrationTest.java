package org.openmrs.module.dashboard.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openmrs.module.dashboard.api.model.Dashboards;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:TestingApplicationContext.xml"})
public class GenericDashboardControllerIntegrationTest extends BaseModuleWebContextSensitiveTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Ignore
    public void shouldFindDashboard() throws Exception {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();
        File privilegeFile = new File(applicationDataDirectory, "dashboard_privilege.json");
        privilegeFile.deleteOnExit();
        FileUtils.writeStringToFile(privilegeFile, "{\n" +
                " \"dashboardPrivileges\" :[\n" +
                "   {\n" +
                "     \"dashboardName\" : \"dashboard_config\",\n" +
                "     \"requiredPrivileges\" : [\"provider\"]\n" +
                "   }\n" +
                " ]\n" +
                "}");

        File configFile = new File(applicationDataDirectory, "dashboard_config.json");
        configFile.deleteOnExit();
        FileUtils.writeStringToFile(configFile, "{\"name\": \"test dashboard\"}");

        MvcResult result = mockMvc.perform(
                get("/rest/v1/dashboard/config"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Dashboards config = new ObjectMapper().readValue(content, Dashboards.class);
        Assert.assertEquals("{name=test dashboard}", config.getDashboards().get(0).toString());
    }

    @Test
    public void shouldReturnDashboardConfiguration() throws Exception {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();
        File privilegeFile = new File(applicationDataDirectory, "dashboard_privileges.json");
        privilegeFile.deleteOnExit();
        FileUtils.writeStringToFile(privilegeFile, "[\n" +
                "   {\n" +
                "     \"dashboardName\" : \"dashboard_config\",\n" +
                "     \"requiredPrivileges\" : [\"provider\"]\n" +
                "   }\n" +
                " ]");

        File configFile = new File(applicationDataDirectory, "dashboard_config.json");
        configFile.deleteOnExit();
        FileUtils.writeStringToFile(configFile, "{\"name\": \"test dashboard\"}");

        MvcResult result = mockMvc.perform(
                get("/rest/v1/dashboard/configuration"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
//        DashboardConfig config = new ObjectMapper().readValue(content, DashboardConfig.class);
        Assert.assertEquals("[{\"name\":\"test dashboard\"}]", content);
//        assertEquals(2, config.getDashboards().get(0).getSections().size());
    }
}