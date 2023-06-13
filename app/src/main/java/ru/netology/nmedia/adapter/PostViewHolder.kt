package ru.netology.nmedia.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostService
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener,


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

            likeImageView.setOnClickListener {
                listener.onLike(post)

            }
            shareImageView.setOnClickListener {
                listener.onShare(post)
            }

            moreVertView.setOnClickListener {
                // создаем выпадающее меню
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_options)
                    setOnMenuItemClickListener { item ->
                        when(item.itemId){
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> false
                        }

                    }
                }
                    .show()
            }
        }

    }
}