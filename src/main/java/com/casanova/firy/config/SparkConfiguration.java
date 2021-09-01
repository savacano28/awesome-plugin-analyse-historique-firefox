package com.casanova.firy.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfiguration {

    @Bean
    public SparkSession getSparkSession() {
        SparkSession sparkSession =
            SparkSession.builder()
                        .appName("FiryApp")
                        .master("local[2]")
                        .config("spark.cassandra.connection.host", "127.0.0.1")
                        .config("spark.cassandra.output.consistency.level", "LOCAL_ONE")
                        .getOrCreate();
        return sparkSession;
    }
}
