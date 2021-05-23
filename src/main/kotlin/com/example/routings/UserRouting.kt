package com.example.routings

import com.example.controllers.UserController
import com.example.model.User
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Route.userRouting() {
    route("/user") {
        get("/exists/{login}/{password}") {
            handleException(call) {
                val login = call.parameters["login"] ?: return@handleException call.badRequest()
                val password = call.parameters["password"] ?: return@handleException call.badRequest()
                call.respond(UserController.exists(login, password))
            }
        }

        get("/{id}") {
            handleException(call) {
                val id = call.parameters["id"] ?: return@handleException call.badRequest()
                call.respond(UserController.getById(id.toInt()))
            }
        }

        post("/create") {
            handleException(call) {
                val user = call.receive<User>()
                call.respond(UserController.create(user))
            }
        }

        post("/update") {
            handleException(call) {
                val new = call.receive<User>()
                UserController.update(new)
                call.ok()
            }
        }

        delete("/delete/{login}") {
            handleException(call) {
                val login = call.parameters["login"] ?: return@handleException call.badRequest()
                UserController.delete(login)
                call.ok()
            }
        }
    }
}
