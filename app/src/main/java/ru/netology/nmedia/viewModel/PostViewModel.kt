package ru.netology.nmedia.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory

class PostViewModel : ViewModel() {

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
    private var repository: PostRepository = PostRepositoryInMemory()
    val data: LiveData<List<Post>> = repository.getData()
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