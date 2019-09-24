package org.openmrs.module.dashboard.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
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
    public void shouldFindDashboard() throws Exception {
        String applicationDataDirectory = OpenmrsUtil.getApplicationDataDirectory();
        File createdFile = new File(applicationDataDirectory, "dashboard_config.json");

        FileUtils.writeStringToFile(createdFile, "{\n" +
                "  \"dashboards\": [\n" +
                "    {\n" +
                "      \"name\": \"professional dashboard\",\n" +
                "      \"requiredPrivileges\": [\"doc\"],\n" +
                "      \"sections\": [{\"name\": \"widget one\"}," +
                "        {\"name\": \"widget two\"}]" +
                "    }\n" +
                "  ]\n" +
                "}");


        MvcResult result = mockMvc.perform(
                get("/rest/v1/dashboard/config"))
                .andExpect(status().isOk())
                .andReturn();


        String content = result.getResponse().getContentAsString();
        DashboardConfig config = new ObjectMapper().readValue(content, DashboardConfig.class);
        assertEquals("professional dashboard", config.getDashboards().get(0).getName());
        assertEquals(2, config.getDashboards().get(0).getSections().size());
    }
}