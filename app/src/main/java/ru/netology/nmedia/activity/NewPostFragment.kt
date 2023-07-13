package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.utils.StringProperty
import ru.netology.nmedia.viewModel.PostViewModel

class NewPostFragment : Fragment() { // теперь наследуемся от fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)
        arguments?.textArg?.let{
            binding.content.setText(it) // правильно ли я сделал ?
        }
        // задаем единую вьюмодель поста
        val viewModel: PostViewModel by viewModels( ownerProducer = ::requireParentFragment)

        binding.OkButton.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isNotBlank()) {
                viewModel.changeContent(text)
                viewModel.save()
            }

            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object{
        var Bundle.textArg by StringProperty // добавляем функцию расширение
    }
}