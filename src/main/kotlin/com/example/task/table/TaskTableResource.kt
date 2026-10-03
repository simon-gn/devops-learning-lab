package com.example.task.table

import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.Response

@Path("/tasks")
class TaskTableResource(
    private val storage: TaskTableStorage,
) {

    @GET
    fun getTasks(): List<ApiTask> =
        storage.findAll()

    @GET
    @Path("/{id}")
    fun findById(@PathParam("id") id: Long): Response =
        storage.findById(id)
            ?.let { Response.ok(it).build() }
            ?: Response.status(Response.Status.NOT_FOUND).build()

    @POST
    fun create(request: ApiCreateTaskRequest): Response =
        storage.create(request.title)
            ?.let { Response.ok(it).status(Response.Status.CREATED).build() }
            ?: Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()

    @PUT
    @Path("/{id}")
    fun update(
        @PathParam("id") id: Long,
        request: ApiUpdateTaskRequest
    ): Response =
        storage.update(id, request.title, request.completed)
            ?.let { Response.ok(it).build() }
            ?: Response.status(Response.Status.NOT_FOUND).build()

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: Long): Response =
        if (storage.delete(id)) {
            Response.noContent().build()
        } else {
            Response.status(Response.Status.NOT_FOUND).build()
        }
}