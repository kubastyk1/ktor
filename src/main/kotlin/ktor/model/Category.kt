package ktor.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Categories: IntIdTable(){
    val name = varchar("name", 100)
}

class Category(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Category>(Categories)

    var name by Categories.name
}