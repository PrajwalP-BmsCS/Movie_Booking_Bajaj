package com.cinema_package.cinema_project.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingEventProducer {

    private final KafkaTemplate<String, BookingConfirmedEvent> kafkaTemplate;

    public BookingEventProducer(
        KafkaTemplate<String, BookingConfirmedEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishBookingConfirmed(BookingConfirmedEvent event) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        kafkaTemplate.send("booking-confirmed", event)
    .whenComplete((result, ex) -> {
        if (ex != null) {
            System.out.println("❌ Kafka send failed: " + ex.getMessage());
        } else {
            System.out.println("✅ Kafka send success");
        }
    });

    }
}
