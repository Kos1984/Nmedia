package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val viewsCount: Long,
    val shareCount: Long,
    val likesCount: Long,
    val likedByMe: Boolean = false,
)