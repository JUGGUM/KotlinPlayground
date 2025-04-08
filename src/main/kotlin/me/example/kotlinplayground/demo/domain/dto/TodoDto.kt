package me.example.kotlinplayground.demo.domain.dto

data class TodoRequest(
    val title: String,
    val done: Boolean = false
)

data class TodoResponse(
    val id: Long,
    val title: String,
    val done: Boolean
)