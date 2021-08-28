package com.casanova.firy.service;

import com.casanova.firy.config.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

        try (KafkaConsumer<String, String> consumer = createConsumer(topics, consumerParams)) {
            while (true) {
                final ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofSeconds(5));

                consumerRecords.forEach(record -> {
                    System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                                      record.key(), record.value(),
                                      record.partition(), record.offset());
                });
                consumer.commitAsync();
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private KafkaConsumer<String, String> createConsumer(final List<String> topics, final Map<String, String> consumerParams) {
        Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();
        consumerProps.putAll(consumerParams);
        consumerProps.remove("topic");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
        // Subscribe to the topic.
        consumer.subscribe(topics);
        return consumer;
    }

}
