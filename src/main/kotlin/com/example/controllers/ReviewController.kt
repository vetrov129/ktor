package com.example.controllers

import com.example.model.Id
import com.example.model.Review
import com.example.model.Reviews
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ReviewController {
    fun create(new: Review): Id = transaction {
        Reviews
            .insert {
                it[productId] = new.productId
                it[userId] = new.productId
                it[review] = new.review
                it[score] = new.score
            }[Reviews.id].let { Id(it) }
    }

    fun getForUser(userId: Int): List<Review> = transaction {
        Reviews
            .select { Reviews.userId eq userId }
            .map {
                Review(
                    id = it[Reviews.id],
                    productId = it[Reviews.productId],
                    userId = it[Reviews.userId],
                    review = it[Reviews.review],
                    score = it[Reviews.score]
                )
            }
    }

    fun getForProduct(productId: Int): List<Review> = transaction {
        Reviews
            .select { Reviews.productId eq productId }
            .map {
                Review(
                    id = it[Reviews.id],
                    productId = it[Reviews.productId],
                    userId = it[Reviews.userId],
                    review = it[Reviews.review],
                    score = it[Reviews.score]
                )
            }
    }

    fun update(new: Review) {
        transaction {
            Reviews
                .update({ Reviews.id eq new.id }) {
                    it[productId] = new.productId
                    it[userId] = new.productId
                    it[review] = new.review
                    it[score] = new.score
                }
        }
    }

    fun delete(id: Int) {
        transaction { Reviews.deleteWhere { Reviews.id eq id } }
    }
}