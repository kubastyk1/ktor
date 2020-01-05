package ktor.dao

import ktor.model.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable

interface DAOFacade: Closeable{
    fun init()
    fun createUser(login:String, email:String, password:String)
    fun updateUser(id:Int, login:String, email:String, password:String)
    fun deleteUser(id:Int)
    fun getUser(id:Int): User?
    fun getAllUsers(): List<User>
    fun validateUser(login: String, password: String): User?
}

class DAOFacadeDatabase(val db: Database): DAOFacade{

    override fun init() = transaction(db) {
        SchemaUtils.create(Users)
        //Initial data
        val users = listOf(User(1, "user1","user1@gmail.com", "user1"),
                User(2, "user2","user2@gmail.com", "user2"),
                User(3, "user3","user3@gmail.com", "user3"))
        Users.batchInsert(users){ user ->
            this[Users.id] = user.id
            this[Users.login] = user.login
            this[Users.email] = user.email
            this[Users.password] = user.password
        }
        Unit
    }
    override fun createUser(login: String, email: String, password: String) = transaction(db) {
        Users.insert {it[Users.login] = login;
            it[Users.email] = email; it[Users.password] = password;
        }
        Unit
    }
    override fun updateUser(id: Int, login: String, email: String, password: String) = transaction(db) {
        Users.update({Users.id eq id}){
            it[Users.login] = login
            it[Users.email] = email
            it[Users.password] = password
        }
        Unit
    }
    override fun deleteUser(id: Int) = transaction(db) {
        Users.deleteWhere { Users.id eq id }
        Unit
    }
    override fun getUser(id: Int) = transaction(db) {
        Users.select { Users.id eq id }.map {
            User(it[Users.id], it[Users.login], it[Users.email], it[Users.password]
            )
        }.singleOrNull()
    }
    override fun getAllUsers() = transaction(db) {
        Users.selectAll().map {
            User(it[Users.id], it[Users.login], it[Users.email], it[Users.password]
            )
        }
    }
    override fun validateUser(login: String, password: String) = transaction(db) {
        Users.select { Users.login eq login and (Users.password eq password) }.map {
            User(it[Users.id], it[Users.login], it[Users.email], it[Users.password]
            )
        }.singleOrNull()
    }
    override fun close() { }
}
