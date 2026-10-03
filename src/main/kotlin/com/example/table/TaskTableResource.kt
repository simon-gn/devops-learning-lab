package com.example.table

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path

@Path("/tasks")
class TaskTableResource(
    private val storage: TaskTableStorage,
) {

    @GET
    fun getTasks(): List<ApiTask> =
        storage.findAll()
}