package me.example.kotlinplayground.demo.application.service

import me.example.kotlinplayground.demo.domain.dto.TodoRequest
import me.example.kotlinplayground.demo.domain.dto.TodoResponse
import me.example.kotlinplayground.demo.domain.entity.Todo
import me.example.kotlinplayground.demo.domain.repository.TodoRepository
import me.example.kotlinplayground.demo.presentation.TodoKafkaProducer
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoRepository,
    private val kafkaProducer: TodoKafkaProducer) {

    fun getAllTodos(): List<TodoResponse> =
        todoRepository.findAll().map { TodoResponse(it.id, it.title, it.done) }

    fun createTodo(request: TodoRequest): TodoResponse {
        val saved = todoRepository.save(Todo(title = request.title, done = request.done))

        // Kafka 메시지 발행
        //kafkaProducer.sendMessage("todo-events", "Created TODO: ${saved.id}, ${saved.title}")

        return TodoResponse(saved.id, saved.title, saved.done)
    }

    fun updateTodo(id: Long, request: TodoRequest): TodoResponse {
        val todo = todoRepository.findById(id).orElseThrow { NoSuchElementException("Todo not found") }
        todo.title = request.title
        todo.done = request.done
        val updated = todoRepository.save(todo)

        // Kafka 메시지 발행
        kafkaProducer.sendMessage("todo-events", "Updated TODO: ${updated.id}, ${updated.title}")

        return TodoResponse(updated.id, updated.title, updated.done)
    }

    fun deleteTodo(id: Long) {
        todoRepository.deleteById(id)
    }
}