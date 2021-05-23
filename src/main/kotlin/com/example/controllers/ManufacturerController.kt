package com.example.controllers

import com.example.model.Id
import com.example.model.Manufacturer
import com.example.model.Manufacturers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ManufacturerController {
    fun create(manufacturer: Manufacturer): Id = transaction {
        Manufacturers.insert {
            it[name] = manufacturer.name
            it[place] = manufacturer.place
        }[Manufacturers.id].let { Id(it) }
    }

    fun getAll(): List<Manufacturer> = transaction {
        Manufacturers
            .selectAll()
            .map {
                Manufacturer(
                    id = it[Manufacturers.id],
                    name = it[Manufacturers.name],
                    place = it[Manufacturers.place]
                )
            }
    }

    fun getById(id: Int): Manufacturer = transaction {
        Manufacturers
            .select { Manufacturers.id eq id }
            .map {
                Manufacturer(
                    id = it[Manufacturers.id],
                    name = it[Manufacturers.name],
                    place = it[Manufacturers.place]
                )
            }.first()
    }

    fun update(new: Manufacturer) {
        transaction {
            Manufacturers
                .update({Manufacturers.id eq new.id}) {
                    it[name] = new.name
                    it[place] = new.place
                }
        }
    }

    fun delete(id: Int) {
        transaction {
            Manufacturers.deleteWhere { Manufacturers.id eq id }
        }
    }
}