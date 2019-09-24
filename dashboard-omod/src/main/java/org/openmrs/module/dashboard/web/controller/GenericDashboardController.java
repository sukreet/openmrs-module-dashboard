package org.openmrs.module.dashboard.web.controller;

import org.openmrs.module.dashboard.api.model.DashboardConfig;
import org.openmrs.module.dashboard.api.service.DashboardService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseEntity<DashboardConfig> find() {
        DashboardConfig config = dashboardService.find();
        return new ResponseEntity<>(config, HttpStatus.OK);
    }
}
