package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Id(
    val id: Int
)

@Serializable
data class Status(
    val status: String,
    val message: String = ""
)

@Serializable
data class User(
    val id: Int,
    val login: String,
    val password: String,
    val money: Double
)

@Serializable
data class Category(
    val id: Int,
    val name: String
)

@Serializable
data class Subcategory(
    val id: Int,
    val categoryId: Int,
    val name: String
)

@Serializable
data class Manufacturer(
    val id: Int,
    val name: String,
    val place: String
)

@Serializable
data class Product(
    val id: Int,
    val subcategoryId: Int,
    val manufacturerId: Int,
    val name: String,
    val description: String
)

@Serializable
data class Review(
    val id: Int,
    val productId: Int,
    val userId: Int,
    val review: String,
    val score: Int
)