package com.example.routings

import com.example.controllers.ReviewController
import com.example.model.Review
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.reviewRouting() {
    route("/review") {
        get("/forUser/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                call.respond(ReviewController.getForUser(id.toInt()))
            }
        }

        get("/forProduct/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                call.respond(ReviewController.getForProduct(id.toInt()))
            }
        }

        post("/update") {
            handleException(call) {
                val review = call.receive<Review>()
                ReviewController.update(review)
                call.ok()
            }
        }

        post("/create") {
            val review = call.receive<Review>()
            ReviewController.create(review)
            call.ok()
        }

        delete("/delete/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                ReviewController.delete(id.toInt())
                call.ok()
            }
        }
    }
}