package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post
//создаем вместо PostRepositoryInMemory
class PostRepositorySharePrefsImpl (
    context: Context
        ): PostRepository {

    private val gson = Gson() // вызываем конструктор Gson
    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)// метод получает файл с данными по "name" если его нет он его создает
    private val key = "posts"  // ключ для чтения из файла
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java)// описываем что нам должны вернуть из файла
    private var nextId = 0L
    private var posts = emptyList<Post>()
    private var data = MutableLiveData(posts)

    init { // init код который срабатывает при создании экземпляра класса и выводит список постов из файла
        prefs.getString(key, null).let {
            posts = gson.fromJson(it, typeToken) as List<Post>
            data.value = posts
        }

    }

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
        sync()
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shareCount = post.shareCount + 1)
            } else post
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
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
        sync()
    }

    private fun sync(){ // метод для записи изменений в фаил при изменениях в постах
        prefs.edit().apply{
            putString(key, gson.toJson(posts))
            apply()
        }
    }

}