package ru.netology.nmedia.activity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.viewModel.PostViewModel

class PostFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // указаваем из какого layout брать данные для отрисовки
        val binding = FragmentPostBinding.inflate(layoutInflater, container, false)
        val postId = arguments?.getLong("id")
        val id = postId
        val viewModel: PostViewModel by viewModels( ownerProducer = ::requireParentFragment)
        val posts = viewModel.data
        val post = posts.value?.find { it.id == postId }





        val holder = PostViewHolder(binding.postWatch, object : PostListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)

            }

            override fun onShare(post: Post) {
               viewModel.shareById(post.id)
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
                viewModel.removeById(postId!!)
                findNavController().navigateUp()
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_postFragment_to_newPostFragment,
                    Bundle().apply {
                        textArg = post.content
                    }
                )
            }

            override fun onWatch(post: Post) {
                val intentVideo = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
                startActivity(intentVideo)
            }

            override fun onWatchPost(post: Post) {

            }

        }
        )


        // подписываемся на изменения
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId }
            holder.bind(post!!)
        }


        return binding.root




    }


}