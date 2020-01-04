package ktor.model

data class User(val id: Int, val login: String,
                    val email: String, val password: String)