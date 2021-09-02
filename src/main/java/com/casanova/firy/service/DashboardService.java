package com.casanova.firy.service;

import com.casanova.firy.domain.DataChart;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.sum;

/**
 * Service class for managing dashboard.
 */
@Service
public class DashboardService {

    private final Logger log = LoggerFactory.getLogger(DashboardService.class);

    private final SparkSession sparkSession;

    public DashboardService(final SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    public DataChart getDataVisitByDay() {
        Dataset<Row> visits = this.sparkSession.read()
                                               .format("org.apache.spark.sql.cassandra")
                                               .option("keyspace", "cassandrafiry")
                                               .option("table", "f_visit")
                                               .load();

        Dataset<Row> visitsByDay = visits.select("visit_date_simple", "nb_visits")
                                         .groupBy("visit_date_simple")
                                         .agg(sum("nb_visits").alias("nb_visits_by_day"));

        DataChart data = new DataChart();
        data.setLabels(visitsByDay.select("visit_date_simple")
                                  .collectAsList()
                                  .stream()
                                  .map(d -> d.getDate(0).toString())
                                  .collect(Collectors.toList()));
        // data.setDatasets();
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
