package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val viewModel: PostViewModel  by viewModels()
        val adapter = PostAdapter( object : PostListener{
            override fun onLike(post: Post) {
               viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

        }
        )

       viewModel.edited.observe(this){
           if (it.id == 0L){
               return@observe
           }
           activityMainBinding.group.visibility = View.VISIBLE
           activityMainBinding.content.requestFocus()
           activityMainBinding.content.setText(it.content)
           AndroidUtils.showTheKeyboardNow(activityMainBinding.content)


       }

        // а тут ли это писать ?
        activityMainBinding.saveView.setOnClickListener {
            with(activityMainBinding.content){
                val content = text.toString()
                if (content.isNullOrBlank()){
                    Toast.makeText(this@MainActivity,
                        R.string.content_must_not_be_empty,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(content)
                viewModel.save()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)

            }
        }
        //отмена редактирования поста
        activityMainBinding.cancelView.setOnClickListener {
            viewModel.clearEdit()
            with(activityMainBinding.content){
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                activityMainBinding.group.visibility = View.GONE

            }

        }



        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        activityMainBinding.list.adapter = adapter

    }
}

