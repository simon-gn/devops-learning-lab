package com.example.task.table

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

@QuarkusTest
class TaskTableTest {

    @Test
    fun `GET tasks returns all tasks`() {
        given()
            .contentType("application/json")
            .body("""{"title":"Task 1"}""")
            .`when`()
            .post("/tasks")
            .then()
            .statusCode(201)

        given()
            .contentType("application/json")
            .body("""{"title":"Task 2"}""")
            .`when`()
            .post("/tasks")
            .then()
            .statusCode(201)

        given()
            .`when`()
            .get("/tasks")
            .then()
            .statusCode(200)
            .body("find { it.title == 'Task 1' }.completed", equalTo(false))
            .body("find { it.title == 'Task 2' }.completed", equalTo(false))
    }

    @Test
    fun `GET task returns 404 for unknown id`() {
        given()
            .`when`()
            .get("/tasks/999999")
            .then()
            .statusCode(404)
    }

    @Test
    fun `POST creates a task`() {
        given()
            .contentType("application/json")
            .body("""{"title":"Learn Docker"}""")
            .`when`()
            .post("/tasks")
            .then()
            .statusCode(201)
            .body("title", equalTo("Learn Docker"))
            .body("completed", equalTo(false))
    }

    @Test
    fun `PUT updates a task`() {
        val id =
            given()
                .contentType("application/json")
                .body("""{"title":"Original"}""")
                .`when`()
                .post("/tasks")
                .then()
                .statusCode(201)
                .extract()
                .path<Int>("id")
                .toLong()

        given()
            .contentType("application/json")
            .body("""{"title":"Updated","completed":true}""")
            .`when`()
            .put("/tasks/$id")
            .then()
            .statusCode(200)
            .body("id", equalTo(id.toInt()))
            .body("title", equalTo("Updated"))
            .body("completed", equalTo(true))
    }

    @Test
    fun `PUT returns 404 for unknown id`() {
        given()
            .contentType("application/json")
            .body("""{"title":"Updated","completed":true}""")
            .`when`()
            .put("/tasks/999999")
            .then()
            .statusCode(404)
    }

    @Test
    fun `DELETE removes a task`() {
        val id =
            given()
                .contentType("application/json")
                .body("""{"title":"To be deleted"}""")
                .`when`()
                .post("/tasks")
                .then()
                .statusCode(201)
                .extract()
                .path<Int>("id")
                .toLong()

        given()
            .`when`()
            .delete("/tasks/$id")
            .then()
            .statusCode(204)

        given()
            .`when`()
            .get("/tasks/$id")
            .then()
            .statusCode(404)
    }

    @Test
    fun `DELETE returns 404 for unknown id`() {
        given()
            .`when`()
            .delete("/tasks/999999")
            .then()
            .statusCode(404)
    }
}