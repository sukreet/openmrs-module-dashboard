package org.openmrs.module.dashboard.api.loader;

import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.AdministrationService;
import org.openmrs.module.dashboard.api.model.DashboardPrivileges;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Component("dashboardConfigLoader")
public class ConfigLoader {

    private ObjectMapper objectMapper;

    private AdministrationService administrationService;

    private String dataDirectory;

    public ConfigLoader() {
//        this.administrationService = Context.getAdministrationService();
        this.objectMapper = new ObjectMapper();
        this.dataDirectory = OpenmrsUtil.getApplicationDataDirectory();
    }

    public ConfigLoader(AdministrationService administrationService) {
//        this.administrationService = administrationService;
        this.objectMapper = new ObjectMapper();
        this.dataDirectory = OpenmrsUtil.getApplicationDataDirectory();
    }

    private String readFileFromAppDir(String fileName) throws IOException {
        File file = new File(dataDirectory, fileName);
        return OpenmrsUtil.getFileAsString(file);
    }

    public DashboardPrivileges getDashboardPrivileges() throws IOException {
        String dashboardPrivilegesFileName = null;
//                administrationService.getGlobalProperty("dashboard.privileges.file");
        String privilegeFileName = StringUtils.isEmpty(dashboardPrivilegesFileName) ? "dashboard_privileges.json" : dashboardPrivilegesFileName;
        String dashboardPrivilegesFileContent = readFileFromAppDir(privilegeFileName);
        return new DashboardPrivileges(dashboardPrivilegesFileContent);
    }

    public ArrayList<Object> readAllFilesFromAppDataDirectory(ArrayList<String> dashboardNames) throws IOException {

        ArrayList<Object> dashboards = new ArrayList<>();
        for (String dashboardName : dashboardNames) {
            String fileContent = readFileFromAppDir(dashboardName + ".json");
            Object dashboard = objectMapper.readValue(fileContent, Object.class);
            dashboards.add(dashboard);
        }
        return dashboards;
    }
}
