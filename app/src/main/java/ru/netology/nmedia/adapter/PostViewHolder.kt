package ru.netology.nmedia.adapter

import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostService
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
//класс отвечает за разметку поста
class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener,


): ViewHolder(binding.root) {
    fun bind (post: Post){
        with(binding) {
            authorTextView.text = post.author
            postDateView.text = post.published
            postContentView.text = post.content
            viewsCount.text = PostService.countToString(post.viewsCount)
            likeImageView.isChecked = post.likedByMe
            likeImageView.text = PostService.countToString(post.likesCount)
            shareImageView.text = PostService.countToString(post.shareCount)

            root.setOnClickListener {
                listener.onWatchPost(post)

            }


            VideoView.setOnClickListener {
                listener.onWatch(post)
            }

            likeImageView.setOnClickListener {
                listener.onLike(post)

            }
            shareImageView.setOnClickListener {
                listener.onShare(post)

            }

            if (post.videoUrl != null) {
                postVideoGroup.visibility = View.VISIBLE
            } else{
                postVideoGroup.visibility = View.GONE
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