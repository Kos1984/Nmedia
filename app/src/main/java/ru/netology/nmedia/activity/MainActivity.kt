package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val viewModel: PostViewModel  by viewModels()

        val newPostContract = registerForActivityResult(NewPostActivity.Contract) {result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()

        }

        val editPostContract = registerForActivityResult(EditPostActivity.ContractEdit) {result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()

        }
        val adapter = PostAdapter( object : PostListener{
            override fun onLike(post: Post) {
               viewModel.likeById(post.id)
            }

            override fun onWatch(post: Post) {
                val intentVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
                startActivity(intentVideo)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
                //создаем интент и настраиваем отправку текста из поста
                val intentText = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                //меняем внешний вид интента на более продвинутый.
                val startIntent = Intent.createChooser(intentText, getString(R.string.share))
                startActivity(startIntent)

            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                editPostContract.launch(post.content)

            }
        }
        )

        activityMainBinding.addPostButton.setOnClickListener {
            newPostContract.launch()
        }

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        activityMainBinding.list.adapter = adapter

    }
}

