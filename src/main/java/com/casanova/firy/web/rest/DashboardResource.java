package com.casanova.firy.web.rest;

import com.casanova.firy.domain.DataChart;
import com.casanova.firy.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firy-dashboard")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);
    private final DashboardService dashboardService;

    public DashboardResource(final DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/data-chart-visit-by-day")
    public ResponseEntity<DataChart> getDataVisitByDay() {
        log.debug("REST request to generate data for figure : visit by day");
        return new ResponseEntity<>(dashboardService.getDataVisitByDay(), HttpStatus.OK);
    }

    @GetMapping("/data-chart-sites-with-duration")
    public ResponseEntity<DataChart> getDataSitesWithDuration() {
        log.debug("REST request to generate data for figure : sites with duration");
        return new ResponseEntity<>(dashboardService.getDataSitesWithDuration(), HttpStatus.OK);
    }

    @GetMapping("/data-chart-visits-by-type")
    public ResponseEntity<DataChart> getDataVisitsByType() {
        log.debug("REST request to generate data for figure : visit by type");
        return new ResponseEntity<>(dashboardService.getDataVisitsByType(), HttpStatus.OK);
    }

    @GetMapping("/data-chart-search-subject")
    public ResponseEntity<DataChart> getDataSearchSubject() {
        log.debug("REST request to generate data for figure : visit by type");
        return new ResponseEntity<>(dashboardService.getDataSearchSubject(), HttpStatus.OK);
    }

    @PostMapping("/add-figure")
    public ResponseEntity<Void> addFigure(@RequestBody String figureDescription) {
        log.debug("REST request to create a figure : {}", figureDescription);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
