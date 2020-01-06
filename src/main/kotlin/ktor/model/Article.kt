package ktor.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Articles: IntIdTable(){
    val title = varchar("title", 500)
    val user = reference("user", Users)
    val category = reference("category", Categories)
}

class Article(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Article>(Articles)

    var title by Articles.title
    var category by Category referencedOn Articles.category
    var user by User referencedOn Articles.user
}