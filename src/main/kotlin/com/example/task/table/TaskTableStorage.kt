package com.example.task.table

import com.example.jooq.tables.Task.TASK
import jakarta.enterprise.context.ApplicationScoped
import org.jooq.DSLContext


@ApplicationScoped
class TaskTableStorage(
    private val dsl: DSLContext
) {
    internal fun findAll(): List<ApiTask> =
        dsl.selectFrom(TASK)
            .fetch()
            .map { ApiTask(it.id, it.title, it.completed) }

    internal fun findById(id: Long): ApiTask? =
        dsl.selectFrom(TASK)
            .where(TASK.ID.eq(id))
            .fetchOne()
            ?.let { ApiTask(it.id, it.title, it.completed) }

    internal fun create(title: String): ApiTask? =
        dsl.insertInto(TASK)
            .set(TASK.TITLE, title)
            .set(TASK.COMPLETED, false)
            .returning()
            .fetchOne()
            ?.let { ApiTask(it.id, it.title, it.completed) }

    internal fun update(
        id: Long,
        title: String,
        completed: Boolean
    ): ApiTask? =
        dsl.update(TASK)
            .set(TASK.TITLE, title)
            .set(TASK.COMPLETED, completed)
            .where(TASK.ID.eq(id))
            .returning()
            .fetchOne()
            ?.let { ApiTask(it.id, it.title, it.completed) }

    internal fun delete(id: Long): Boolean =
        dsl.deleteFrom(TASK)
            .where(TASK.ID.eq(id))
            .execute() > 0
}