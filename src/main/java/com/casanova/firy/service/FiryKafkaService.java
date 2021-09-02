package com.casanova.firy.service;

import com.casanova.firy.config.KafkaProperties;
import com.casanova.firy.domain.Host;
import com.casanova.firy.domain.Site;
import com.casanova.firy.domain.Visit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

/**
 * Service class for managing datawarehouse page.
 */
@Service
public class FiryKafkaService {

    private final Logger log = LoggerFactory.getLogger(FiryKafkaService.class);

    private final KafkaProperties kafkaProperties;

    private final Map<String, Object> consumerProps;

    private final JavaStreamingContext streamingContext;

    private final SparkSession sparkSession;

    public FiryKafkaService(final KafkaProperties kafkaProperties, final SparkSession sparkSession) {
        this.kafkaProperties = kafkaProperties;
        this.sparkSession = sparkSession;

        consumerProps = this.kafkaProperties.getConsumerProps();
        consumerProps.remove("topic");

        this.streamingContext = new JavaStreamingContext(JavaSparkContext.fromSparkContext(this.sparkSession.sparkContext()), new Duration(20000));
    }

    public void consume(final List<String> topics) throws InterruptedException {
        processSpark(topics);
    }

    private void processSpark(final List<String> topics) throws InterruptedException {
        JavaInputDStream<ConsumerRecord<String, String>> messages =
            KafkaUtils.createDirectStream(
                this.streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, consumerProps));

        topics.forEach(topic -> saveInformationTemporarily(messages, topic));

        this.streamingContext.start();
        this.streamingContext.awaitTermination();
    }

    private void saveInformationTemporarily(final JavaInputDStream<ConsumerRecord<String, String>> messages,
                                            final String topic) {
        switch (topic) {
            case "firefox-moz_places":
                saveTemporarily(messages.filter(m -> m.topic().equals(topic)), Site.class, "site");
                break;
            case "firefox-moz_historyvisits":
                saveTemporarily(messages.filter(m -> m.topic().equals(topic)), Visit.class, "visit");
                break;
            case "firefox-moz_origins":
                saveTemporarily(messages.filter(m -> m.topic().equals(topic)), Host.class, "host");
                break;
        }
    }

    private void saveTemporarily(final JavaDStream<ConsumerRecord<String, String>> messages,
                                 final Class mapperClass,
                                 final String nameTable) {
        ObjectMapper objectMapper = new ObjectMapper();
        messages.map(msg -> ((JSONObject) JSONValue.parse(msg.value())).get("payload"))
                .foreachRDD(javaRdd -> {
                    List<Object> list = new ArrayList<>();
                    for (Object record : javaRdd.collect()) {
                        String value = record.toString();
                        Object temp = objectMapper.readValue(value, mapperClass);
                        list.add(temp);
                    }
                    if (!list.isEmpty()) {
                        JavaRDD<Object> rdd = this.streamingContext.sparkContext().parallelize(list);
                        javaFunctions(rdd).writerBuilder("cassandrafiry", nameTable, mapToRow(mapperClass)).saveToCassandra();
                    }
                });
    }

}
