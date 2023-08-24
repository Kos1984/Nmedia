package ru.netology.nmedia.viewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostDraftRepository
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl



class PostViewModel(
    application: Application // добавили параметр
) : AndroidViewModel(application) { // изменили наследование было ViewModel

    private val empty = Post(
        id = 0,
        author = "",
        published = "",
        content = "",
        viewsCount = 0,
        shareCount = 0,
        likesCount = 0,
        likedByMe = false,
        videoUrl = null
    )
    private var repository: PostRepository = PostRepositorySQLiteImpl(AppDb.getInstance(application).postDao)
    var postDraft = PostDraftRepository(application) // пробрасываем в new post fragment
    val data: LiveData<List<Post>> = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    val edited = MutableLiveData(empty)

    fun save(){
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty

    }

    fun edit(post: Post){
        edited.value = post
    }

    fun changeContent(content: String){
        edited.value?.let { post ->
            if (content != post.content){
            edited.value = post.copy(author = "Me", published = "now",  content = content)
            }
        }

    }
}