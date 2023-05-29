package ru.netology.nmedia.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory

class PostViewModel : ViewModel() {
    private var repository: PostRepository = PostRepositoryInMemory()
    val data: LiveData<Post> = repository.getData()
    fun like() = repository.like()
    fun share() = repository.share()
}