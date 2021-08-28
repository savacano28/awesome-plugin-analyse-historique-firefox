package com.casanova.firy.service;

import com.casanova.firy.domain.DataGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for managing datawarehouse page.
 */
@Service
public class DataWareHouseService {

    private final Logger log = LoggerFactory.getLogger(DataWareHouseService.class);

    public DataWareHouseService() {
    }

    public DataGrid getDataFromTable(final String nameTable) {
        DataGrid data = new DataGrid();

        return data;
    }

}
