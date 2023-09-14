package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
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
        // задаем единую вьюмодель поста
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)

        var edit = true

        // callback для перехвата системной стрелки и свайпа назад
        val callback = requireActivity().onBackPressedDispatcher.addCallback() {
            val text = binding.content.text.toString()

            if (text.isNotBlank() && !edit) {
                viewModel.postDraft.writeDraft(text)

            }
            findNavController().navigateUp()
        }
        // вытыскиваем текст из бандла
        arguments?.textArg?.let {
            binding.content.setText(it)
        }
        // пара if  для определения это новый пост или редактирование имеющегося поста
        if (binding.content.text.isBlank()) edit = false

        if (viewModel.postDraft.readDraft() != "null" && !edit) {
            binding.content.setText(viewModel.postDraft.readDraft())
            viewModel.postDraft.clearDraft()

        }

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

    companion object {
        var Bundle.textArg by StringProperty // добавляем функцию расширение в бандл
    }
}