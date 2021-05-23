package com.example.routings

import com.example.controllers.CategoryController
import com.example.model.Category
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.categoryRouting() {
    route("/category") {
        get("/list") {
            handleException(call) { call.respond(CategoryController.getList()) }
        }

        post("/create") {
            handleException(call) {
                val category = call.receive<Category>()
                call.respond(CategoryController.create(category))
            }
        }

        post("/update") {
            handleException(call) {
                val category = call.receive<Category>()
                CategoryController.update(category)
                call.ok()
            }
        }

        delete("/delete/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                CategoryController.delete(id.toInt())
                call.ok()
            }
        }
    }
}