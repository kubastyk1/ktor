package ktor.dao

import org.jetbrains.exposed.sql.Table

object Users: Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val login = varchar("login", 50)
    val email = varchar("email", 100)
    val password = varchar("password", 50)
}