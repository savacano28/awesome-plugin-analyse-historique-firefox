package com.casanova.firy.web.rest;

import com.casanova.firy.domain.DataGrid;
import com.casanova.firy.service.DataWareHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/firy-datawarehouse")
public class DataWareHouseResource {

    private final Logger log = LoggerFactory.getLogger(DataWareHouseResource.class);
    private final DataWareHouseService dataWareHouseService;

    public DataWareHouseResource(final DataWareHouseService dataWareHouseService) {
        this.dataWareHouseService = dataWareHouseService;
    }

    @GetMapping("/data-from-table")
    public ResponseEntity<DataGrid> getDataFromTable(@RequestParam String nameTable) {
        log.debug("REST request to get data for table : {}", nameTable);
        return new ResponseEntity<>(dataWareHouseService.getDataFromTable(nameTable), HttpStatus.OK);
    }

    @GetMapping("/dim-site")
    public ResponseEntity<Void> getDataFromTable() {
        log.debug("REST request to stocke data in dim site");
        dataWareHouseService.processDataToFactVisit();
        dataWareHouseService.processDataToDimHost();
        dataWareHouseService.processDataToDimSite();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
