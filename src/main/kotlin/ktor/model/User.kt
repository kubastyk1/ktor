package ktor.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Users: IntIdTable(){
    val login = Users.varchar("login", 50)
    val email = Users.varchar("email", 100)
    val password = Users.varchar("password", 50)
}

class User(id: EntityID<Int>/*, login: String, email: String, password: String*/): IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var login by Users.login
    var email by Users.email
    var password by Users.password
}