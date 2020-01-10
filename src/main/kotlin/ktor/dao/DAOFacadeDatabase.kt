package ktor.dao

import ktor.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable

interface DAOFacade: Closeable{
    fun init()
    fun createUser(login:String, email:String, password:String)
    fun updateUser(id:Int, login:String, email:String, password:String)
    fun deleteUser(id:Int)
    fun getUser(id:Int): User
    fun getAllUsers(): List<User>
    fun validateUser(login: String, password: String): User?
    fun getCategory(id:Int): Category
    fun getAllCategories(): List<Category>
    fun getArticlesFromCategory(id:Int): List<Article>
    fun getUserByLogin(login:String?): User?
}

class DAOFacadeDatabase(val db: Database): DAOFacade{

    override fun init() = transaction(db) {
        SchemaUtils.create(Users, Categories, Articles)
        //Initial data
        User.new {
            login ="user1"
            email = "user1@gmail.com"
            password = "user1"
        }
        User.new {
            login ="user2"
            email = "user2@gmail.com"
            password = "user2"
        }

        Category.new {
            name = "Football"
        }
        Category.new {
            name = "Basketball"
        }
        Category.new {
            name = "Volleyball"
        }

        Article.new {
            title = "Football news"
            user = getUser(1)
            category = getCategory(1)
        }
        Article.new {
            title = "Football lets play"
            user = getUser(2)
            category = getCategory(1)
        }
        Article.new {
            title = "Basketball news"
            user = getUser(1)
            category = getCategory(2)
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
    override fun getUser(id: Int): User = transaction(db) {
        User.find { Users.id eq id }.single()
    }
    override fun getAllUsers() = transaction(db) {
        User.all().toList()
    }
    override fun validateUser(login: String, password: String) = transaction(db) {
        User.find { Users.login eq login and (Users.password eq password)}.singleOrNull()
    }
    override fun getCategory(id: Int): Category = transaction(db){
        Category.find { Categories.id eq id }.single()
    }
    override fun getAllCategories() = transaction(db) {
        Category.all().toList()
    }
    override fun getArticlesFromCategory(id: Int): List<Article> = transaction(db) {
        Article.find(Articles.category eq id).toList()
    }
    override fun getUserByLogin(login: String?): User? = transaction(db) {
        User.find{Users.login eq login.toString()}.singleOrNull()
    }
    override fun close() { }
}
//http://127.0.0.1:8080/
