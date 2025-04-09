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

    fun getTodo(id: Long): TodoResponse {
        val todo = todoRepository.findById(id)
            .orElseThrow { NoSuchElementException("Todo not found") }
        return TodoResponse(todo.id, todo.title, todo.done)
    }

    fun createTodo(request: TodoRequest): TodoResponse {
        val saved = todoRepository.save(Todo(title = request.title, done = request.done))

        // Kafka 메시지 발행
        //kafkaProducer.sendMessage("todo-events", "Created TODO: ${saved.id}, ${saved.title}")

        return TodoResponse(saved.id, saved.title, saved.done)
    }

    fun updateTodo(id: Long, title: String, done: Boolean): TodoResponse {
        val todo = todoRepository.findById(id)
            .orElseThrow { NoSuchElementException("Todo not found") }
        todo.title = title
        todo.done = done

        // Kafka 메시지 발행
        // kafkaProducer.sendMessage("todo-events", "Updated TODO: ${updated.id}, ${updated.title}")

        val updated = todoRepository.save(todo)
        return TodoResponse(updated.id, updated.title, updated.done)
    }

    fun createTodo(title: String): TodoResponse {
        val newTodo = Todo(title = title, done = false)
        val saved = todoRepository.save(newTodo)
        return TodoResponse(saved.id, saved.title, saved.done)
    }

    fun deleteTodo(id: Long): Boolean {
        return if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}