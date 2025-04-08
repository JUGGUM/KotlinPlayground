package me.example.kotlinplayground.demo.presentation

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class TodoKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun sendMessage(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
    }
}