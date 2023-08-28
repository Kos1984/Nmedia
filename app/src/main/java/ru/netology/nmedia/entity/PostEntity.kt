package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity // делегируем создание и заполнение таблицы
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val viewsCount: Long,
    val shareCount: Long,
    val likesCount: Long,
    val likedByMe: Boolean = false,
    val videoUrl: String?
) {
    fun toDto() = Post(id, author, published, content, viewsCount, shareCount, likesCount, likedByMe, videoUrl)

    companion object {
        fun fromDto(post: Post) = with(post){PostEntity(id, author, published, content, viewsCount, shareCount, likesCount, likedByMe, videoUrl)}
    }
}