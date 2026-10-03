package com.example.database

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import javax.sql.DataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

@ApplicationScoped
class JooqConfiguration(
    private val dataSource: DataSource
) {

    @Produces
    @ApplicationScoped
    fun dslContext(): DSLContext =
        DSL.using(dataSource, SQLDialect.POSTGRES)
}