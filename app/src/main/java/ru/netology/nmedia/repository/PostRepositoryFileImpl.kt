package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post

//создаем вместо PostRepositoryInMemory
class PostRepositoryFileImpl(
    private val context: Context
) : PostRepository {

    private val gson = Gson() // вызываем конструктор Gson
    private val filename = "posts.json"  // ключ для чтения из файла
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type// описываем что нам должны вернуть из файла
    private var nextId = 0L
    private var posts = emptyList<Post>()
        set(value) {  // сеттер вызывается каждый раз когда меняем posts
            field = value
            sync() // вызываем запись на диск
        }
    private var data = MutableLiveData(posts)

    init { // init код который срабатывает при создании экземпляра класса и выводит список постов из файла
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, typeToken)
                nextId = posts.maxOfOrNull { it.id }
                    ?: 0 // Получаем id последнего поста что бы счетчик nextId в ОЗУ работал корректно
                data.value = posts
            }
        } else {
            sync()
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
        if (post.id == 0L) {
            posts = listOf(post.copy(id = ++nextId)) + posts
            data.value = posts
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts

    }

    private fun sync() { // метод для записи изменений в фаил при изменениях в постах
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}

