package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post()

        with(binding){
            authorTextView.text = post.author
            postDateView.text =  post.published
            postContentView.text = post.content
            likeCount.text = PostService.countToString(post.likesCount)
            shareCount.text = PostService.countToString(post.shareCount)
            viewsCount.text = PostService.countToString(post.viewsCount)



            if (post.likedByMe){
                likeImageView.setImageResource(R.drawable.baseline_favorite_red_24)
            }

            likeImageView.setOnClickListener{
                post.likedByMe = !post.likedByMe
                post.likesCount = if (post.likedByMe) post.likesCount +1 else post.likesCount -1
                likeCount.text = PostService.countToString(post.likesCount)
                likeImageView.setImageResource(if (post.likedByMe){
                    R.drawable.baseline_favorite_red_24
                } else R.drawable.baseline_favorite_border_24)
            }

            shareImageView.setOnClickListener {
                post.shareCount++
                shareCount.text = PostService.countToString(post.shareCount)
            }

        }


    }
}