package org.openmrs.module.dashboard.api.loader;

import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.module.dashboard.api.model.PrivilegesConfig;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class ConfigLoader {

    private ObjectMapper objectMapper;
    private String dataDirectory;

    public ConfigLoader() {
        dataDirectory = OpenmrsUtil.getApplicationDataDirectory();
        this.objectMapper = new ObjectMapper();
    }

    public DashboardConfig loadDashboardConfig(String fileName) throws IOException {
        //Todo: get a list of flieNames and do same for each
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
}
