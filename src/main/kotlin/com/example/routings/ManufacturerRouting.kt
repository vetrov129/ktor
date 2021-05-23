package com.example.routings

import com.example.controllers.ManufacturerController
import com.example.model.Manufacturer
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.manufactureRouting() {
    route("/manufacturer") {
        get("/all") {
            handleException(call) { call.respond(ManufacturerController.getAll()) }
        }

        get("/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                call.respond(ManufacturerController.getById(id.toInt()))
            }
        }

        post("/create") {
            handleException(call) {
                val manufacturer = call.receive<Manufacturer>()
                ManufacturerController.create(manufacturer)
                call.ok()
            }
        }

        post("/update") {
            handleException(call) {
                val manufacturer = call.receive<Manufacturer>()
                ManufacturerController.update(manufacturer)
                call.ok()
            }
        }

        post("delete/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                ManufacturerController.delete(id.toInt())
                call.ok()
            }
        }
    }
}