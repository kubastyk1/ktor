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
import io.ktor.response.respondRedirect
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
                    val action = (call.request.queryParameters["action"] ?: "new")
                    when (action) {
                        "new" -> call.respond(FreeMarkerContent("signup.ftl",
                                mapOf("action" to action)))
                        "edit" -> {
                            val session = call.sessions.get<MySession>()
                            if (session != null) {
                                call.respond(FreeMarkerContent("user.ftl",
                                        mapOf("user" to dao.getUserByLogin(session.name), "action" to action)))
                            }
                        }
                    }
                }
                post {
                    val postParameters: Parameters = call.receiveParameters()
                    val action = postParameters["action"] ?: "new"
                    when (action) {
                        "new" -> {
                            if(dao.getUserByLogin(postParameters["login"]) == null) {
                                dao.createUser(postParameters["login"] ?: "", postParameters["email"]
                                        ?: "", postParameters["password"] ?: "")
                                call.sessions.set(postParameters["login"]?.let { it1 -> MySession(name = it1) })
                            }
                        }
                        "edit" -> {
                            val session = call.sessions.get<MySession>()
                            val id = dao.getUserByLogin(session?.name)?.id
                            if (id != null)
                                dao.updateUser(id.value, postParameters["login"] ?: "", postParameters["email"]
                                        ?: "", postParameters["password"] ?: "")
                        }
                    }
                    if (call.sessions.get<MySession>() != null) {
                        call.respond(FreeMarkerContent("categoryList.ftl", mapOf("categories" to dao.getAllCategories())))
                    } else {
                        call.respondRedirect("/user?action=" + action)
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
            route("/logout"){
                get{
                    call.sessions.clear<MySession>()
                    call.respond(FreeMarkerContent("login.ftl", mapOf("users" to dao.getAllUsers())))
                }
            }
            route("/article"){
                get {
                    val action = (call.request.queryParameters["action"] ?: "new")
                    when (action) {
                        "new" -> call.respond(FreeMarkerContent("createArticle.ftl",
                                mapOf("action" to action)))
                    }
                }
                post {
                    val postParameters: Parameters = call.receiveParameters()
                    val action = postParameters["action"] ?: "new"
                    when (action) {
                        "new" -> {
                            if(dao.getUserByLogin(postParameters["login"]) == null) {
                                val session = call.sessions.get<MySession>()
                                val id = dao.getUserByLogin(session?.name)?.id
                                dao.createArticle(postParameters["title"] ?: "", postParameters["category"] ?: "",
                                        id?.value.toString())
                            }
                            call.respond(FreeMarkerContent("categoryList.ftl", mapOf("categories" to dao.getAllCategories())))
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}
