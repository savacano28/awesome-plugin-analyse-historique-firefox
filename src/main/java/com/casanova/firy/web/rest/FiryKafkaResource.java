package com.casanova.firy.web.rest;

import com.casanova.firy.service.FiryKafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/firy-kafka")
public class FiryKafkaResource {

    private final Logger log = LoggerFactory.getLogger(FiryKafkaResource.class);
    private final FiryKafkaService firyKafkaService;

    public FiryKafkaResource(FiryKafkaService firyKafkaService) {
        this.firyKafkaService = firyKafkaService;
    }

    @GetMapping("/consume")
    public ResponseEntity<Void> consume(@RequestParam("topic") List<String> topics) throws
                                                                                    InterruptedException {
        log.debug("REST request to consume records from Kafka topics {}", topics);
        firyKafkaService.consume(topics);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
