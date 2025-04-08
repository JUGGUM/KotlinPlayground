package me.example.kotlinplayground.demo.presentation.controller

import me.example.kotlinplayground.demo.domain.dto.TodoRequest
import me.example.kotlinplayground.demo.domain.dto.TodoResponse
import me.example.kotlinplayground.demo.application.service.TodoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {

    @GetMapping
    fun getAll(): List<TodoResponse> = todoService.getAllTodos()

    @PostMapping
    fun create(@RequestBody request: TodoRequest): ResponseEntity<TodoResponse> {
        return ResponseEntity.ok(todoService.createTodo(request))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long, @RequestBody request: TodoRequest
    ): ResponseEntity<TodoResponse> {
        return ResponseEntity.ok(todoService.updateTodo(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        todoService.deleteTodo(id)
        return ResponseEntity.noContent().build()
    }
}