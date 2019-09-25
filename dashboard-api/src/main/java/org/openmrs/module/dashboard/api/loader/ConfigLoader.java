package org.openmrs.module.dashboard.api.loader;

import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.module.dashboard.api.model.DashboardPrivileges;
import org.openmrs.module.dashboard.api.model.PrivilegesConfig;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@Component("dashboardConfigLoader")
public class ConfigLoader {

    private ObjectMapper objectMapper;

    private AdministrationService administrationService;

    private String dataDirectory;

    public ConfigLoader() {
        this.administrationService = Context.getAdministrationService();
        this.objectMapper = new ObjectMapper();
        this.dataDirectory = OpenmrsUtil.getApplicationDataDirectory();
    }

    public ConfigLoader(AdministrationService administrationService) {
        this.administrationService = administrationService;
        this.objectMapper = new ObjectMapper();
        this.dataDirectory = OpenmrsUtil.getApplicationDataDirectory();
    }

    public DashboardConfig loadDashboardConfig(String fileName) throws IOException {
        //Todo: get a list of fileNames and do same for each
        ArrayList<Object> dashboards = new ArrayList<>();
        String fileContent = null;
        try {
            fileContent = readFileFromAppDir(fileName);
        } catch (FileNotFoundException e) {
            fileContent = "{}";
        } catch (IOException e) {
            e.printStackTrace();
        }
        dashboards.add(mapDashboardConfig(fileContent));
        return new DashboardConfig(dashboards);
    }

    public PrivilegesConfig loadDashboardPrivilegeConfig(String fileName) throws IOException {
        String fileAsString = readFileFromAppDir(fileName);
        return mapPrivilegeConfig(fileAsString);
    }

    private Object mapDashboardConfig(String fileContent) throws IOException {
        Object config = objectMapper.readValue(fileContent, Object.class);
        return config;
    }

    private PrivilegesConfig mapPrivilegeConfig(String fileAsString) throws IOException {
        return objectMapper.readValue(fileAsString, PrivilegesConfig.class);
    }

    private String readFileFromAppDir(String fileName) throws IOException {
        File file = new File(dataDirectory, fileName);
        return OpenmrsUtil.getFileAsString(file);
    }

    public DashboardPrivileges getDashboardPrivileges() throws IOException {
        String dashboardPrivilegesFileName = administrationService.getGlobalProperty("dashboard.privileges.file");
        String privilegeFileName = StringUtils.isEmpty(dashboardPrivilegesFileName) ? "dashboard_privileges.json" : dashboardPrivilegesFileName;
        String dashboardPrivilegesFileContent = readFileFromAppDir(privilegeFileName);
        return new DashboardPrivileges(dashboardPrivilegesFileContent);
    }

    public ArrayList<String> readAllFilesFromAppDataDirectory(ArrayList<String> dashboardNames) throws IOException {

        ArrayList<String> dashboards = new ArrayList<>();

        for (String dashboardName : dashboardNames) {
            dashboards.add(readFileFromAppDir(dashboardName + ".json"));
        }
        return dashboards;
    }
}
