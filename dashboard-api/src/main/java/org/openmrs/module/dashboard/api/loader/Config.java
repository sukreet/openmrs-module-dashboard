package org.openmrs.module.dashboard.api.loader;

import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class Config {

    public DashboardConfig readDashboardConfig(String fileName) {
        String fileAsString = readFileFromAppDir(fileName);
        return mapDashboardConfig(fileAsString);
    }

    private DashboardConfig mapDashboardConfig(String fileAsString) {
        DashboardConfig dashboardConfig = null;
        try {
            dashboardConfig = new ObjectMapper().readValue(fileAsString, DashboardConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dashboardConfig;
    }

    private String readFileFromAppDir(String fileName) {
        File file = new File(OpenmrsUtil.getApplicationDataDirectory(), fileName);
        String fileContent = null;
        try {
            fileContent = OpenmrsUtil.getFileAsString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
