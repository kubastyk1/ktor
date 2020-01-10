/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package ktor

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.*
import ktor.dao.DAOFacadeDatabase
import org.jetbrains.exposed.sql.Database

data class MySession(val name: String)

fun main(args: Array<String>) {
    val dao = DAOFacadeDatabase(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))

    embeddedServer(Netty, port = 8080){
        dao.init()
        install(FreeMarker){
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }
        install(Sessions) {
            cookie<MySession>("COOKIE_NAME")
        }
        routing {
            route("/"){
                get{
                    call.respond(FreeMarkerContent("login.ftl", mapOf("users" to dao.getAllUsers())))
                }
                post{
                    val postParameters: Parameters = call.receiveParameters()
                    val user = dao.validateUser(postParameters["login"] ?: "", postParameters["password"] ?: "")
                    if (user == null) {
                        call.respond(FreeMarkerContent("login.ftl", mapOf("users" to dao.getAllUsers())))
                    } else {
                        call.sessions.set(MySession(name = user.login))
                        call.respond(FreeMarkerContent("categoryList.ftl", mapOf("categories" to dao.getAllCategories())))
                    }
                }
            }
            route("/user"){
                get {
                    if (call.sessions.get<MySession>() != null) {
                        val action = (call.request.queryParameters["action"] ?: "new")
                        when (action) {
                            "new" -> call.respond(FreeMarkerContent("signup.ftl",
                                    mapOf("action" to action)))
                            "edit" -> {
                                val id = call.request.queryParameters["id"]
                                if (id != null) {
                                    call.respond(FreeMarkerContent("user.ftl",
                                            mapOf("user" to dao.getUser(id.toInt()),
                                                    "action" to action)))
                                }
                            }
                        }
                    } else {
                        call.respond(FreeMarkerContent("login.ftl", mapOf("users" to dao.getAllUsers())))
                    }
                }
                post {
                    if (call.sessions.get<MySession>() != null) {
                        val postParameters: Parameters = call.receiveParameters()
                        val action = postParameters["action"] ?: "new"
                        when (action) {
                            "new" -> dao.createUser(postParameters["login"] ?: "", postParameters["email"]
                                    ?: "", postParameters["password"] ?: "")
                            "edit" -> {
                                val id = postParameters["id"]
                                if (id != null)
                                    dao.updateUser(id.toInt(), postParameters["login"] ?: "", postParameters["email"]
                                            ?: "", postParameters["password"] ?: "")
                            }
                        }
                        call.respond(FreeMarkerContent("categoryList.ftl", mapOf("categories" to dao.getAllCategories())))
                    } else {
                        call.respond(FreeMarkerContent("login.ftl", mapOf("users" to dao.getAllUsers())))
                    }
                }
            }
            route("/delete"){
                get {
                    if (call.sessions.get<MySession>() != null) {
                        val id = call.request.queryParameters["id"]
                        if (id != null) {
                            dao.deleteUser(id.toInt())
                            call.respond(FreeMarkerContent("index.ftl", mapOf("users" to dao.getAllUsers())))
                        }
                    } else {
                        call.respond(FreeMarkerContent("login.ftl", mapOf("users" to dao.getAllUsers())))
                    }
                }
            }
            route("/category"){
                get{
                    if (call.sessions.get<MySession>() != null) {
                        val id = call.request.queryParameters["id"]
                        if (id != null) {
                            call.respond(FreeMarkerContent("articleList.ftl", mapOf("articles" to dao.getArticlesFromCategory(id.toInt()))))
                        }
                    } else {
                        call.respond(FreeMarkerContent("login.ftl", mapOf("users" to dao.getAllUsers())))
                    }
                }
            }
        }
    }.start(wait = true)
}
