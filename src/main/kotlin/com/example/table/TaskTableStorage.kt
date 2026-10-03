package com.example.table

import com.example.jooq.tables.Task.TASK
import jakarta.enterprise.context.ApplicationScoped
import org.jooq.DSLContext


@ApplicationScoped
class TaskTableStorage(
    private val dsl: DSLContext
) {
    fun findAll(): List<ApiTask> =
        dsl.selectFrom(TASK)
            .fetch()
            .map { ApiTask(it.id, it.title, it.completed) }
}