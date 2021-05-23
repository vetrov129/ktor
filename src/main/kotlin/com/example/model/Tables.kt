package com.example.model

import org.jetbrains.exposed.sql.Table

object Users: Table("users") {
    val id = integer("id").primaryKey().autoIncrement()
    val login = varchar("login", 64).uniqueIndex()
    val password = varchar("password", 64)
    val money = decimal("money", 2, 0) // TODO: 11.05.2021 если какая-то дичь с деньгами нужно попробовать поменять scale (на 1 например)
}

object Categories: Table("categories") {
    val id = integer("id").primaryKey().autoIncrement()
    val name = text("name")
}

object Subcategories: Table("subcategories") {
    val id = integer("id").primaryKey().autoIncrement()
    val categoryId = integer("category_id").references(Categories.id)
    val name = text("name")
}

object Manufacturers: Table("manufacturers") {
    val id = integer("id").primaryKey().autoIncrement()
    val name = text("name")
    val place = text("place")
}

object Products: Table("products") {
    val id = integer("id").primaryKey().autoIncrement()
    val subcategoryId = integer("subcategory_id").references(Categories.id)
    val manufacturerId = integer("manufacturer_id").references(Manufacturers.id)
    val name = text("name")
    val description = text("description")
}

object Reviews: Table("reviews") {
    val id = integer("id").primaryKey().autoIncrement()
    val productId = integer("product_id").references(Products.id)
    val userId = integer("user_id").references(Users.id)
    val review = text("review")
    val score = integer("score")
}