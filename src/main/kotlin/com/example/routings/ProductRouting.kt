package com.example.routings

import com.example.controllers.ProductController
import com.example.model.Product
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.productRouting() {
    route("/product") {
        get("/all") {
            handleException(call) { call.respond(ProductController.getAll()) }
        }

        get("/forSubcategory/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                call.respond(ProductController.getForSubcategory(id.toInt()))
            }
        }

        get("/forManufacturer/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                call.respond(ProductController.getForManufacturer(id.toInt()))
            }
        }

        get("/search/{query}") {
            handleException(call) {
                val query = call.parameters["id"] ?: return@handleException call.badRequest()
                call.respond(ProductController.search(query))
            }
        }

        post("/update") {
            handleException(call) {
                val product = call.receive<Product>()
                ProductController.update(product)
                call.ok()
            }
        }

        post ("/create") {
            handleException(call) {
                val product = call.receive<Product>()
                call.respond(ProductController.create(product))
            }
        }

        delete("/delete/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                ProductController.delete(id.toInt())
                call.ok()
            }
        }
    }
}