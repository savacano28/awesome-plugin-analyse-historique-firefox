package com.casanova.firy.service;

import com.casanova.firy.domain.DataChart;
import com.casanova.firy.domain.DataSet;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.spark.sql.functions.*;

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
                                         .agg(sum("nb_visits").alias("nb_visits_by_day"))
                                         .orderBy("visit_date_simple");

        DataChart data = new DataChart();
        //Labels
        data.setLabels(transformDataString(visitsByDay, "visit_date_simple"));
        //Dataset
        DataSet ds = new DataSet();
        ds.setData(transformDataDouble(visitsByDay, "nb_visits_by_day"));
        data.setDatasets(Collections.singletonList(ds));
        return data;
    }

    public DataChart getDataSitesWithDuration() {
        Dataset<Row> visits = this.sparkSession.read()
                                               .format("org.apache.spark.sql.cassandra")
                                               .option("keyspace", "cassandrafiry")
                                               .option("table", "f_visit")
                                               .load();

        Dataset<Row> durationVisitBySite = visits.select("host", "dur_mean_vis")
                                                 .groupBy("host")
                                                 .agg(avg("dur_mean_vis").alias("dur_mean_by_site"))
                                                 .filter(col("dur_mean_by_site").$greater(1))
                                                 .limit(15)
                                                 .orderBy(desc("dur_mean_by_site"));

        Dataset<Row> nbVisitBySite = visits.join(durationVisitBySite, visits.col("host")
                                                                            .equalTo(durationVisitBySite.col("host")))
                                           .select(visits.col("host"), visits.col("nb_visits"))
                                           .groupBy("host")
                                           .agg(sum("nb_visits").alias("nb_visits_by_site"));

        visits.filter(visits.col("host").isin(durationVisitBySite.col("host")))
              .select(visits.col("host"), visits.col("nb_visits"))
              .groupBy("host")
              .agg(sum("nb_visits").alias("nb_visits_by_site"));

        DataChart data = new DataChart();
        DataChart dataLine = new DataChart();
        //Labels
        data.setLabels(transformDataString(durationVisitBySite, "host"));
        dataLine.setLabels(transformDataString(nbVisitBySite, "host"));
        //Dataset
        DataSet ds = new DataSet();
        DataSet dsLine = new DataSet();
        ds.setData(transformDataDouble(durationVisitBySite, "dur_mean_by_site"));
        dsLine.setData(transformDataDouble(nbVisitBySite, "nb_visits_by_site"));

        data.setDatasets(Arrays.asList(ds, dsLine));
        return data;
    }

    public DataChart getDataVisitsByType() {
        Dataset<Row> visits = this.sparkSession.read()
                                               .format("org.apache.spark.sql.cassandra")
                                               .option("keyspace", "cassandrafiry")
                                               .option("table", "f_visit")
                                               .load();

        Dataset<Row> durationVisitByType = visits.select("type_id", "nb_visits")
                                                 .groupBy("type_id")
                                                 .agg(sum("nb_visits").alias("nb_visits_by_type"))
                                                 .orderBy("type_id");
        DataChart data = new DataChart();
        //Labels
        data.setLabels(transformDataString(durationVisitByType, "type_id"));
        //Dataset
        DataSet ds = new DataSet();
        ds.setData(transformDataDouble(durationVisitByType, "nb_visits_by_type"));
        data.setDatasets(Collections.singletonList(ds));
        return data;
    }

    public DataChart getDataSearchSubject() {
        Dataset<Row> sites = this.sparkSession.read()
                                              .format("org.apache.spark.sql.cassandra")
                                              .option("keyspace", "cassandrafiry")
                                              .option("table", "d_site")
                                              .load();
        Dataset<Row> words = sites.select(
            trim(
                lower(
                    regexp_replace(
                        lower(
                            sites.col("title")), "([^\\s\\w_]|_)+", ""))).alias("sentence"))
                                  .filter(col("sentence").isNotNull());
        Dataset<Row> wordSplit = words.select(split(words.col("sentence"), "\\s+").alias("split"));
        Dataset<Row> wordSingle = wordSplit.select(explode(wordSplit.col("split")).alias("word"));
        Dataset<Row>
            wordCount =
            wordSingle.groupBy("word")
                      .count()
                      .filter(functions.not(col("word").isin("de", "para", "en", "el", "la", "you", "js", "parte")))
                      .orderBy(desc("count"))
                      .limit(50);

        DataChart data = new DataChart();
        //Labels
        data.setLabels(transformDataString(wordCount, "word"));
        //Dataset
        DataSet ds = new DataSet();
        ds.setData(transformDataDouble(wordCount, "count"));
        data.setDatasets(Collections.singletonList(ds));
        return data;
    }

    private List<Double> transformDataDouble(final Dataset<Row> dataToTransform, final String column) {
        List<Double> list = new ArrayList<>();
        for (Row d : dataToTransform.select(column)
                                    .collectAsList()) {
            Double value = Double.valueOf(d.get(0).toString());
            list.add(value);
        }
        return list;
    }

    private List<String> transformDataString(final Dataset<Row> dataToTransform, final String column) {
        List<String> list = new ArrayList<>();
        for (Row d : dataToTransform.select(column)
                                    .collectAsList()) {
            String value = d.get(0).toString();
            list.add(value);
        }
        return list;
    }
}
