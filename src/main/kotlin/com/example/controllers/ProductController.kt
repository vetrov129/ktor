package com.example.controllers

import com.example.model.Id
import com.example.model.Manufacturer
import com.example.model.Product
import com.example.model.Products
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ProductController {
    fun create(product: Product): Id = transaction {
        Products
            .insert {
                it[subcategoryId]  = product.subcategoryId
                it[manufacturerId] = product.manufacturerId
                it[name] = product.name
                it[description] = product.description
            }[Products.id].let { Id(it) }
    }

    fun getAll(): List<Product> = transaction {
        Products
            .selectAll()
            .map {
                Product(
                    id = it[Products.id],
                    subcategoryId = it[Products.subcategoryId],
                    manufacturerId = it[Products.manufacturerId],
                    name = it[Products.name],
                    description = it[Products.description]
                )
            }
    }

    fun getForSubcategory(subcategoryId: Int): List<Product> = transaction {
        Products
            .select { Products.subcategoryId eq subcategoryId }
            .map {
                Product(
                    id = it[Products.id],
                    subcategoryId = it[Products.subcategoryId],
                    manufacturerId = it[Products.manufacturerId],
                    name = it[Products.name],
                    description = it[Products.description]
                )
            }
    }

    fun getForManufacturer(manufacturerId: Int): List<Product> = transaction {
        Products
            .select { Products.manufacturerId eq manufacturerId }
            .map {
                Product(
                    id = it[Products.id],
                    subcategoryId = it[Products.subcategoryId],
                    manufacturerId = it[Products.manufacturerId],
                    name = it[Products.name],
                    description = it[Products.description]
                )
            }
    }

    fun search(query: String): List<Product> = transaction {
        val rs = connection.createStatement().executeQuery(
            "select products.* " +
                    "from products " +
                    "where " +
                    "   ts_rank(to_tsvector('russian', name), plainto_tsquery('russian', '$query')) > 0.0001 " +
                    "   or ts_rank(to_tsvector('russian', description), plainto_tsquery('russian', '$query')) > 0.000001 " +
                    "order by ts_rank(to_tsvector('russian', name), plainto_tsquery('russian', '$query')) desc;"
        )
        mutableListOf<Product>().apply {
            while (rs.next()) add(
                Product(
                    id = rs.getInt("id"),
                    subcategoryId = rs.getInt("subcategory_id"),
                    manufacturerId = rs.getInt("manufacturer_id"),
                    name = rs.getString("name"),
                    description = rs.getString("description")
                )
            )
            rs.close()
        }
    }

    fun update(new: Product) {
        transaction {
            Products
                .update({ Products.id eq new.id }) {
                    it[subcategoryId]  = new.subcategoryId
                    it[manufacturerId] = new.manufacturerId
                    it[name] = new.name
                    it[description] = new.description
                }
        }
    }

    fun delete(id: Int) {
        transaction { Products.deleteWhere { Products.id eq id } }
    }
}