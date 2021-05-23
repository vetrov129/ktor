package com.example.routings

import com.example.controllers.SubcategoryController
import com.example.model.Subcategory
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.subcategoryRouting() {
    route("/subcategory") {
        get("/forCategory/{categoryId}") {
            handleException(call) {
                val categoryId = call.parameters["categoryId"] ?: return@handleException call.badRequest()
                call.respond(SubcategoryController.getForCategory(categoryId.toInt()))
            }
        }

        post("/create") {
            handleException(call) {
                val subcategory = call.receive<Subcategory>()
                call.respond(SubcategoryController.create(subcategory))
            }
        }

        post("/update") {
            handleException(call) {
                val subcategory = call.receive<Subcategory>()
                SubcategoryController.update(subcategory)
                call.ok()
            }
        }

        delete("delete/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                SubcategoryController.delete(id.toInt())
                call.ok()
            }
        }
    }
}