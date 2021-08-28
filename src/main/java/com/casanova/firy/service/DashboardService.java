package com.casanova.firy.service;

import com.casanova.firy.domain.DataChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for managing dashboard.
 */
@Service
public class DashboardService {

    private final Logger log = LoggerFactory.getLogger(DashboardService.class);

    public DashboardService() {
    }

    public DataChart getDataVisitByDay() {
        DataChart data = new DataChart();

        return data;
    }

    public DataChart getDataSitesWithDuration() {
        DataChart data = new DataChart();

        return data;
    }

    public DataChart getDataVisitsByType() {
        DataChart data = new DataChart();

        return data;
    }

    public DataChart getDataSearchSubject() {
        DataChart data = new DataChart();

        return data;
    }
}
