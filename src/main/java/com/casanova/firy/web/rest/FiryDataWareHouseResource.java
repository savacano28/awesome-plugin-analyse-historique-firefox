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
@RequestMapping("/firy-datawarehouse")
public class FiryDataWareHouseResource {

    private final Logger log = LoggerFactory.getLogger(FiryDataWareHouseResource.class);
    private final DataWareHouseService dataWareHouseService;

    public FiryDataWareHouseResource(final DataWareHouseService dataWareHouseService) {
        this.dataWareHouseService = dataWareHouseService;
    }

    @GetMapping("/data-from-table")
    public ResponseEntity<DataGrid> getDataFromTable(@RequestParam String nameTable) {
        log.debug("REST request to get data for table : {}", nameTable);
        return new ResponseEntity<>(dataWareHouseService.getDataFromTable(nameTable), HttpStatus.OK);
    }

}
