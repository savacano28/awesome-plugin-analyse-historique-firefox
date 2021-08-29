package com.casanova.firy.service;

import com.casanova.firy.config.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service class for managing datawarehouse page.
 */
@Service
public class FiryKafkaService {

    private final Logger log = LoggerFactory.getLogger(FiryKafkaService.class);
    private final KafkaProperties kafkaProperties;

    public FiryKafkaService(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public void consume(final List<String> topics, final Map<String, String> consumerParams) {
        processSpark(topics, consumerParams);
    }

    private void processSpark(final List<String> topics, final Map<String, String> consumerParams) {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("FiryApp");
        sparkConf.set("spark.cassandra.connection.host", "127.0.0.1");

        Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();
        consumerProps.putAll(consumerParams);
        consumerProps.remove("topic");

        JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(1));

        JavaInputDStream<ConsumerRecord<String, String>> messages =
            KafkaUtils.createDirectStream(
                streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, consumerProps));
    }

    private void processDataToFactVisit() {
    }

    private void calculateNbVisitsByDayAndSiteAndType() {
    }

    private void calculateMnDurationVisitByDayAndSiteAndType() {
    }

    private void processDataToDimHost() {
    }

    private void processDataToDimSite() {
    }

    private void processDataToDimDate() {
    }

    private void processDataToDimType() {
    }
}
