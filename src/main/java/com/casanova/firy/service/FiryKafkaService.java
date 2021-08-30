package com.casanova.firy.service;

import com.casanova.firy.config.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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

    public void consume(final List<String> topics, final Map<String, String> consumerParams) throws InterruptedException {
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(topics);
        while (true) {
            final ConsumerRecords<String, String> consumerRecords;
            consumerRecords = consumer.poll(Duration.ofSeconds(5L));
            consumerRecords.forEach(record -> {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                                  record.key(), record.value(),
                                  record.partition(), record.offset());
            });
            consumer.commitAsync();
        }
        processSpark(topics, consumerParams);
    }

    private void processSpark(final List<String> topics, final Map<String, String> consumerParams) throws InterruptedException {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("FiryApp");
        sparkConf.setMaster("local[2]");
        sparkConf.set("spark.cassandra.connection.host", "127.0.0.1");

        Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();
        consumerProps.putAll(consumerParams);
        consumerProps.remove("topic");

        JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, new Duration(20000));

        JavaInputDStream<ConsumerRecord<String, String>> messages =
            KafkaUtils.createDirectStream(
                streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, consumerProps));

        log.debug("Print RDDs");
        messages.filter(m -> m.topic().equals("firefox-moz_origins"))
                .map(record -> ((JSONObject) JSONValue.parse(record.value())).get("payload"))
                .print();

        streamingContext.start();
        streamingContext.awaitTermination();

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
