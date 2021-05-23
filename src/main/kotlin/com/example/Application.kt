package com.example

import com.example.model.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.routings.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        module()
    }.start(wait = true)
}

fun initDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/ivan",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "0000"
    )


    transaction {
        SchemaUtils.create(
            Users,
            Categories,
            Subcategories,
            Manufacturers,
            Products,
            Reviews
        )
    }
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
        initDatabase()
        install(Routing) {
            registerRoutes()
        }
    }
}

fun Application.registerRoutes() {
    routing {
        get("/") {
            call.respondText("OK")
        }

    }
}