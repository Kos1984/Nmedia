package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.viewModel.PostViewModel


class FeedFragment() : Fragment() { // сменили MainActivity на FeedFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val activityMainBinding = FragmentFeedBinding.inflate(layoutInflater, container, false)

        //view models теперь наследуется от fragmentKtx
        //ownerProducer = ::requireParentFragment объеденяет вьюмодели
        val viewModel: PostViewModel  by viewModels( ownerProducer = ::requireParentFragment)



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

        val newPostContract = registerForActivityResult(NewPostFragment.Contract) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()

        }

        activityMainBinding.addPostButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment) // вместо запуска контракта пользуемся переходом по фрагментам
        }

        viewModel.data.observe(viewLifecycleOwner) { posts -> // сменили владельца
            adapter.submitList(posts)
        }

        activityMainBinding.list.adapter = adapter

        return activityMainBinding.root
    }



}

