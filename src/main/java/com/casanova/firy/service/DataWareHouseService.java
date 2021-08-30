package com.casanova.firy.service;

import com.casanova.firy.domain.DataGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service class for managing datawarehouse page.
 */
@Service
public class DataWareHouseService {

    private final Logger log = LoggerFactory.getLogger(DataWareHouseService.class);

    public DataWareHouseService() {
    }

    @Scheduled(cron = "0 0 * * * *")
    public void processDataToFactVisit() {

    }

    @Scheduled(cron = "0 0 * * * *")
    public void processDataToDimHost() {
    }

    @Scheduled(cron = "0 0 * * * *")
    public void processDataToDimSite() {
    }

    @Scheduled(cron = "0 0 * * * *")
    public void processDataToDimDate() {
    }

    private void processDataToDimType() {
        //
    }

    private void calculateNbVisitsByDayAndSiteAndType() {
    }

    private void calculateMnDurationVisitByDayAndSiteAndType() {
    }

    /**
     * Dashboard
     */
    public DataGrid getDataFromTable(final String nameTable) {
        DataGrid data = new DataGrid();

        return data;
    }

}
