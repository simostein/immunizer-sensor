package org.immunizer.microagents.sensor;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class InvocationProducer {

    private static InvocationProducer singleton;
    private KafkaProducer<String, Invocation> producer;
    private static final String BOOTSTRAP_SERVERS = "kafka:9092";
    private static final String BASE_TOPIC = "Invocations_";

    private InvocationProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.immunizer.microagents.sensor.InvocationSerializer");
        producer = new KafkaProducer<String, Invocation>(props);
    }

    public static InvocationProducer getSingleton() {
        if (singleton == null) {
            singleton = new InvocationProducer();
        }
        return singleton;
    }

    public void send(Invocation invocation) {
        try{
            producer.send(new ProducerRecord<String, Invocation>(BASE_TOPIC + invocation.getCallStackId(), 0, "", invocation));
        } catch(Throwable th) {
        }
    }
}