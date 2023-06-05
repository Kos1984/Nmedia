package ru.netology.nmedia.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostService
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit,

): ViewHolder(binding.root) {
    fun bind (post: Post){
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

            binding.likeImageView.setOnClickListener {
                onLikeClicked(post)

            }
            binding.shareImageView.setOnClickListener {
                onShareClicked(post)
            }
            binding
        }

    }
}