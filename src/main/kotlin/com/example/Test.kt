package com.example

import com.example.model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

fun main() {
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