package org.openmrs.module.dashboard.web.controller;

import org.openmrs.module.dashboard.api.model.Dashboards;
import org.openmrs.module.dashboard.api.service.DashboardService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/dashboard")
public class GenericDashboardController extends BaseRestController {
    private DashboardService dashboardService;

    @Autowired
    public GenericDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/config")
    @ResponseBody
    public ResponseEntity<Dashboards> find() {
        Dashboards config = dashboardService.find();
        return new ResponseEntity<>(config, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/configuration", produces = "application/json; charset=utf-8 ")
    @ResponseBody
    public ResponseEntity<ArrayList<Object>> getConfigurationByPrivileges() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-type", "application/json");

        ArrayList<Object> dashboardConfigurations = dashboardService.getConfigurationByPrivileges();
        return new ResponseEntity<>(dashboardConfigurations, responseHeaders, HttpStatus.OK);
    }
}
