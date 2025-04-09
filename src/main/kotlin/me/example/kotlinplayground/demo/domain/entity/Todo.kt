package me.example.kotlinplayground.demo.domain.entity

import jakarta.persistence.*

@Entity
data class Todo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var title: String,
    var done: Boolean = false
)