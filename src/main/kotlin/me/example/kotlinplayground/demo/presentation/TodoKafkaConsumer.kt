package me.example.kotlinplayground.demo.presentation

import org.springframework.kafka.annotation.KafkaListener

class TodoKafkaConsumer {

    @KafkaListener(topics = ["todo-events"], groupId = "todo-group")
    fun consume(message: String) {
        println("Received message from Kafka: $message")
    }
}