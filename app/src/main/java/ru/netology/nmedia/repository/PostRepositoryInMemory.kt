package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemory : PostRepository{
    private var post = Post(
        id = 1,
        author = "Нетология, Университет-профессий будущего",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов " +
               "по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению." +
               " Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов." +
               " Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет" +
               " хотеть больше, целиться выше, бежать бастрее. Наша миссия - помочь встать на путь роста" +
               " и начать цепочку перемен -> http://netolo.gy/fyb",
        viewsCount = 1,
        shareCount = 1,
        likesCount = 1,
        likedByMe = false,
    )

    private var data = MutableLiveData(post)

    override fun getData(): LiveData<Post> = data

    override fun like() {
        post = post.copy(
            likesCount = if (post.likedByMe) post.likesCount -1 else post.likesCount +1,
            likedByMe = !post.likedByMe
        )
        data.value = post
    }

    override fun share() {
        post = post.copy(
            shareCount = post.shareCount +1
        )
        data.value = post
    }

}