package com.example.task.table

data class ApiTask(
    val id: Long,
    val title: String,
    val completed: Boolean
)

data class ApiCreateTaskRequest(
    val title: String
)

data class ApiUpdateTaskRequest(
    val title: String,
    val completed: Boolean
)