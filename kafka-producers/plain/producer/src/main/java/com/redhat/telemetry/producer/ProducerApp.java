package com.redhat.telemetry.producer;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SslConfigs;

public class ProducerApp {
    public static Properties configureProperties() {
        Properties props = new Properties();

        // TODO: configure the bootstrap server
props.put(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            " my-cluster-kafka-bootstrap-iazhud-kafka-cluster.apps.na46a.prod.ole.redhat.com:443" 
    );

    // TODO: configure the key and value serializers
    props.put(
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.StringSerializer" 
    );
    props.put(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.IntegerSerializer" 
    );

    // TODO: configure the SSL connection
    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL"); 
    props.put(
            SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
            "C:/Users/LENOVO/amq-broker-7.7.0-bin/amq-broker-7.7.0/examples/features/standard/ssl-enabled-crl-mqtt/src/main/resources/truststore.jks" 
    );
    props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "password"); 

        return props;
    }

    public static void main(String[] args) {
        Random random = new Random(); 
    Producer<Void,Integer> producer = new KafkaProducer<>( 
             configureProperties()
    );

    for (int i = 0; i < 10; i++) { 
        ProducerRecord<Void, Integer> record = new ProducerRecord<>( 
                "total-connected-devices", 
                random.nextInt(100) 
        );

        producer.send(record); 
        printRecord(record);
    }

    producer.close(); 
    }

    private static void printRecord(ProducerRecord record) {
        System.out.println("Sent record:");
        System.out.println("\tTopic = " + record.topic());
        System.out.println("\tPartition = " + record.partition());
        System.out.println("\tKey = " + record.key());
        System.out.println("\tValue = " + record.value());
    }
}
