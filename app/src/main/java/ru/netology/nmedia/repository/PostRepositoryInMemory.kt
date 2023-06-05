package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemory : PostRepository{
    private var posts = List(10){
        Post (id = it.toLong() + 1,
            author = "Нетология, Университет-профессий будущего",
            published = "22 мая в 18:36",
            content = "POst2",
            viewsCount = 1,
            shareCount = 1,
            likesCount = 1,
            likedByMe = false,)

    }

    private var data = MutableLiveData(posts)

    override fun getData(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
       posts =  posts.map { post ->
           if (post.id == id) {
               if (post.likedByMe){
                   post.copy(likedByMe = !post.likedByMe, likesCount = post.likesCount -1)
               }else post.copy(likedByMe = !post.likedByMe, likesCount = post.likesCount +1)
           }else post
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
}