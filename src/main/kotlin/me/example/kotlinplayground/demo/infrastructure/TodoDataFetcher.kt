package me.example.kotlinplayground.demo.infrastructure

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import me.example.kotlinplayground.demo.application.service.TodoService
import me.example.kotlinplayground.demo.domain.dto.TodoResponse
import me.example.kotlinplayground.demo.domain.entity.Todo
import me.example.kotlinplayground.demo.domain.repository.TodoRepository

// GraphQl Resolver -- RestController로 들어오는게 아니고 DgsComponent로 처리
// 가장먼저들어오는곳
@DgsComponent
class TodoDataFetcher(
    private val todoRepository: TodoRepository, private val todoService: TodoService
) {

    @DgsQuery
    fun todos(): List<Todo> = todoRepository.findAll()

    @DgsQuery
    fun todo(@InputArgument id: Long): TodoResponse {
        return todoService.getTodo(id)
    }

    @DgsMutation
    fun updateTodo(@InputArgument id: Long, @InputArgument title: String, @InputArgument done: Boolean): TodoResponse {
        return todoService.updateTodo(id, title, done)
    }

    @DgsMutation
    fun createTodo(@InputArgument title: String): TodoResponse {
        return todoService.createTodo(title)
    }

    @DgsMutation
    fun deleteTodo(@InputArgument id: Long): Boolean {
        return todoService.deleteTodo(id)
    }
}
