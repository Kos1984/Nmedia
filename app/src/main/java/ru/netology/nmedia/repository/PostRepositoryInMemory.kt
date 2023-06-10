package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemory : PostRepository {
    private var nextId = 0L
    private var posts = List(3) {
        Post(
            id = ++nextId,
            author = "Нетология, Университет-профессий будущего",
            published = "22 мая в 18:36",
            content =  "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по " +
                    "онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике " +
                    "и управлению. Мы растём сами и помогаем расти студентам: от новичков до " +
                    "уверенных профессионалов. Но самое важное остаётся с нами: мы верим, " +
                    "что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, " +
                    "бежать бастрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен",
            viewsCount = 1,
            shareCount = 1,
            likesCount = 1,
            likedByMe = false,
        )


    }.reversed()

    private var data = MutableLiveData(posts)

    override fun getData(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                if (post.likedByMe) {
                    post.copy(likedByMe = !post.likedByMe, likesCount = post.likesCount - 1)
                } else post.copy(likedByMe = !post.likedByMe, likesCount = post.likesCount + 1)
            } else post
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shareCount = post.shareCount + 1)
            } else post
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        if(post.id == 0L){
        posts = listOf(post.copy(id = ++nextId )) + posts
        data.value = posts
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts

    }

}