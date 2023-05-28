package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel  by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                authorTextView.text = post.author
                postDateView.text = post.published
                postContentView.text = post.content
                likeCount.text = PostService.countToString(post.likesCount)
                shareCount.text = PostService.countToString(post.shareCount)
                viewsCount.text = PostService.countToString(post.viewsCount)
                if (post.likedByMe){
                    likeImageView.setImageResource(R.drawable.baseline_favorite_red_24)
                } else likeImageView.setImageResource(R.drawable.baseline_favorite_border_24)
                likeCount.text = PostService.countToString(post.likesCount)
                shareCount.text = PostService.countToString(post.shareCount)
            }
        }
        binding.likeImageView.setOnClickListener {
            viewModel.like()
        }
        binding.shareImageView.setOnClickListener {
            viewModel.share()
        }
    }


}