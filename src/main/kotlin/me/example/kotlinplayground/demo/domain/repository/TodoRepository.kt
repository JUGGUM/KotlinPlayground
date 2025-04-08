package me.example.kotlinplayground.demo.domain.repository

import me.example.kotlinplayground.demo.domain.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long>