package com.casanova.firy.service;

import com.casanova.firy.domain.*;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.temporal.WeekFields;
import java.util.*;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;
import static org.apache.spark.sql.functions.*;

/**
 * Service class for managing datawarehouse page.
 */
@Service
public class DataWareHouseService {

    private final Logger log = LoggerFactory.getLogger(DataWareHouseService.class);

    private final JavaStreamingContext streamingContext;

    private final SparkSession sparkSession;

    public DataWareHouseService(final SparkSession sparkSession) {
        this.sparkSession = sparkSession;
        this.streamingContext = new JavaStreamingContext(JavaSparkContext.fromSparkContext(this.sparkSession.sparkContext()), new Duration(20000));
    }

    public void processDataToFactVisit() {
        Dataset<Row> visits = this.sparkSession.read()
                                               .format("org.apache.spark.sql.cassandra")
                                               .option("keyspace", "cassandrafiry")
                                               .option("table", "visit")
                                               .load();
        if (!visits.isEmpty()) {
            //Visits
            Dataset<Row> visits_plus_duration = calculateDurationVisite(visits);
            //f_visit
            Dataset<Row> nb_visits = calculateNbVisitsByDayAndSiteAndType(visits);
            Dataset<Row> mean_dur = calculateMeanDurationVisitByDayAndSiteAndType(visits_plus_duration);
            Dataset<Row> min_dur = calculateMnDurationVisitByDayAndSiteAndType(visits_plus_duration);
            Dataset<Row> max_dur = calculateMxDurationVisitByDayAndSiteAndType(visits_plus_duration);
            //Join
            //Schem table f_visit : id, date_id, type_id, site_id, nb_vis, mx_dur, mn_dur, mean_dur
            Dataset<Row> f_visits = nb_visits.join(mean_dur, mean_dur.col("place_id").equalTo(nb_visits.col("place_id"))
                                                                     .and(mean_dur.col("visit_type").equalTo(nb_visits.col("visit_type")))
                                                                     .and(mean_dur.col("visit_date_simple")
                                                                                  .equalTo(nb_visits.col("visit_date_simple"))))
                                             .select(nb_visits.col("place_id"),
                                                     nb_visits.col("visit_type"),
                                                     nb_visits.col("visit_date_simple"),
                                                     nb_visits.col("nb_visits"),
                                                     mean_dur.col("dur_mean_vis"));
            Dataset<Row> f_visits_0 = f_visits.join(min_dur, min_dur.col("place_id").equalTo(f_visits.col("place_id").alias("place_id_v"))
                                                                    .and(min_dur.col("visit_type").equalTo(f_visits.col("visit_type")))
                                                                    .and(min_dur.col("visit_date_simple").equalTo(f_visits.col("visit_date_simple"))))
                                              .select(f_visits.col("place_id"),
                                                      f_visits.col("visit_type"),
                                                      f_visits.col("visit_date_simple"),
                                                      f_visits.col("nb_visits"),
                                                      f_visits.col("dur_mean_vis"),
                                                      min_dur.col("dur_min_vis"));
            Dataset<Row> f_visits_1 = f_visits_0.join(max_dur, max_dur.col("place_id").equalTo(f_visits_0.col("place_id"))
                                                                      .and(max_dur.col("visit_type").equalTo(f_visits_0.col("visit_type")))
                                                                      .and(max_dur.col("visit_date_simple")
                                                                                  .equalTo(f_visits_0.col("visit_date_simple"))))
                                                .select(f_visits_0.col("place_id").cast("int"),
                                                        f_visits_0.col("visit_type"),
                                                        f_visits_0.col("visit_date_simple"),
                                                        f_visits_0.col("nb_visits").cast("int"),
                                                        f_visits_0.col("dur_mean_vis"),
                                                        f_visits_0.col("dur_min_vis"),
                                                        max_dur.col("dur_max_vis"));

            Dataset<Row> f_visits_with_id = f_visits_1.withColumnRenamed("visit_type", "type_id")
                                                      .withColumn("id", row_number().over(Window.orderBy("visit_date_simple")).cast("int"));

            Dataset<Row> dates = f_visits_with_id.select("visit_date_simple")
                                                 .dropDuplicates("visit_date_simple")
                                                 .withColumn("id", row_number()
                                                     .over(Window.orderBy("visit_date_simple"))
                                                     .cast("int"));

            Dataset<Row>
                f_visits_final =
                f_visits_with_id.join(dates, dates.col("visit_date_simple").equalTo(f_visits_with_id.col("visit_date_simple")))
                                .select(f_visits_with_id.col("id"),
                                        f_visits_with_id.col("place_id"),
                                        f_visits_with_id.col("type_id"),
                                        dates.col("id").alias("date_id"), //date_id
                                        f_visits_with_id.col("visit_date_simple"),
                                        f_visits_with_id.col("nb_visits"),
                                        f_visits_with_id.col("dur_mean_vis"),
                                        f_visits_with_id.col("dur_min_vis"),
                                        f_visits_with_id.col("dur_max_vis")
                                );
            List<FactVisit> list = new ArrayList<>();
            for (Row visit : f_visits_final.collectAsList()) {
                Integer id = (Integer) visit.get(0);
                Integer place_id = (Integer) visit.get(1);
                Integer type_id = (Integer) visit.get(2);
                Integer date_id = (Integer) visit.get(3);
                Integer nb_visits_ = (Integer) visit.get(5);
                Double dur_mean_vis = (double) visit.get(6);
                Integer dur_min_vis = (Integer) visit.get(7);
                Integer dur_max_vis = (Integer) visit.get(8);
                Date visit_date_simple = (Date) visit.get(4);

                list.add(new FactVisit(id, place_id, date_id, type_id, nb_visits_, dur_max_vis, dur_mean_vis, dur_min_vis, visit_date_simple));
            }

            JavaRDD<FactVisit> rdd = this.streamingContext.sparkContext()
                                                          .parallelize(list);
            javaFunctions(rdd).writerBuilder("cassandrafiry", "f_visit", mapToRow(FactVisit.class)).saveToCassandra();

            //dim date
            processDataToDimDate(dates);
        }
    }

    public void processDataToDimHost() {
        Dataset<Row> hosts = this.sparkSession.read()
                                              .format("org.apache.spark.sql.cassandra")
                                              .option("keyspace", "cassandrafiry")
                                              .option("table", "host")
                                              .load();
        if (!hosts.isEmpty()) {
            JavaRDD<DimHost> rdd = this.streamingContext.sparkContext()
                                                        .parallelize(hosts.as(Encoders.bean(DimHost.class)).collectAsList());
            javaFunctions(rdd).writerBuilder("cassandrafiry", "d_host", mapToRow(DimHost.class)).saveToCassandra();
        }
    }

    public void processDataToDimSite() {
        Dataset<Row> sites = this.sparkSession.read()
                                              .format("org.apache.spark.sql.cassandra")
                                              .option("keyspace", "cassandrafiry")
                                              .option("table", "site")
                                              .load()
                                              .drop("rev_host", "last_visit_date");
        if (!sites.isEmpty()) {
            List<DimSite> list = new ArrayList<>();
            for (Row site : sites.collectAsList()) {
                Integer id = (Integer) site.get(0);
                String url = (String) site.get(8);
                String title = (String) site.get(6);
                Integer visit_count = (Integer) site.get(9);
                Integer hidden = (Integer) site.get(3);
                Integer typed = (Integer) site.get(7);
                Integer frecency = (Integer) site.get(2);
                String description = (String) site.get(1);
                Date last_visit_date_simple = (Date) site.get(4);
                Integer origin_id = (Integer) site.get(5);
                list.add(new DimSite(id, url, title, visit_count, hidden, typed, frecency, description, last_visit_date_simple, origin_id));
            }
            JavaRDD<DimSite> rdd = this.streamingContext.sparkContext()
                                                        .parallelize(list);
            javaFunctions(rdd).writerBuilder("cassandrafiry", "d_site", mapToRow(DimSite.class)).saveToCassandra();
        }
    }

    public void processDataToDimDate(Dataset<Row> dates) {
        if (!dates.isEmpty()) {
            List<DimDate> list = new ArrayList<>();
            for (Row date : dates.collectAsList()) {
                Integer day = date.getDate(0).toLocalDate().getDayOfMonth();

                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                Integer week = date.getDate(0).toLocalDate().get(weekFields.weekOfWeekBasedYear());

                Integer month = date.getDate(0).toLocalDate().getMonth().getValue();
                Integer year = date.getDate(0).toLocalDate().getYear();
                list.add(new DimDate((Integer) date.get(1), day, week, month, year));
            }
            JavaRDD<DimDate> rdd = this.streamingContext.sparkContext()
                                                        .parallelize(list);
            javaFunctions(rdd).writerBuilder("cassandrafiry", "d_date", mapToRow(DimDate.class)).saveToCassandra();
        }
    }

    private void processDataToDimType() {
        //Les Valeurs ont été déjà inserés dans la table d_type
    }

    private Dataset<Row> calculateDurationVisite(final Dataset<Row> visits) {
        WindowSpec windowSpec = Window.orderBy("visit_date");
        Random r = new Random();
        r.nextInt();
        Dataset<Row> withDuration = visits.withColumn("duration",
                                                      visits.col("visit_date")
                                                            .minus(when((lag("visit_date", 1).over(windowSpec)).isNull(), 0)
                                                                       .otherwise(lag("visit_date", 1).over(windowSpec))));
        Dataset<Row> withDurationRandom = withDuration.withColumn("duration", when(withDuration.col("duration").$less$eq(18), r.nextInt(112)));
        return withDurationRandom.withColumn("duration", when(col("duration").isNull(), r.nextInt(158)).otherwise(col("duration")));
    }

    private Dataset<Row> calculateNbVisitsByDayAndSiteAndType(final Dataset<Row> visits) {
        return visits.groupBy("place_id", "visit_type", "visit_date_simple")
                     .agg(count("id").alias("nb_visits"));
    }

    private Dataset<Row> calculateMnDurationVisitByDayAndSiteAndType(final Dataset<Row> visits) {
        return visits.groupBy("place_id", "visit_type", "visit_date_simple")
                     .agg(min("duration").alias("dur_min_vis"));
    }

    private Dataset<Row> calculateMxDurationVisitByDayAndSiteAndType(final Dataset<Row> visits) {
        return visits.groupBy("place_id", "visit_type", "visit_date_simple")
                     .agg(max("duration").alias("dur_max_vis"));
    }

    private Dataset<Row> calculateMeanDurationVisitByDayAndSiteAndType(final Dataset<Row> visits) {
        return visits.groupBy("place_id", "visit_type", "visit_date_simple")
                     .agg(avg("duration").alias("dur_mean_vis"));
    }

    /**
     * Dashboard
     */
    public DataGrid getDataFromTable(final String nameTable) {
        DataGrid data = new DataGrid();

        return data;
    }

}
