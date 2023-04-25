package ru.netology.nmedia.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.service.StringArg
import ru.netology.nmedia.viewmodel.PostViewModelGson

class NewPostFragment : Fragment() {
    companion object{
        var Bundle.stringArg: String? by StringArg
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater)
        val viewModel: PostViewModelGson by viewModels(
            ownerProducer = ::requireParentFragment
        )
        arguments?.stringArg
            ?.let(binding.etContent::setText)
        binding.etContent.requestFocus()
        binding.ok.setOnClickListener {

            val text = binding.etContent.text.toString()
            if (!text.isBlank()){
                viewModel.changeContent(text)
                viewModel.save()
            }

            findNavController().navigateUp()
        }

        return binding.root
    }



//    object Contract : ActivityResultContract<String, String?>() {
//        override fun createIntent(context: Context, input: String) =
//            Intent(context, NewPostFragment::class.java).putExtra(Intent.EXTRA_TEXT, input)
//
//
//        override fun parseResult(resultCode: Int, intent: Intent?)= intent?.getStringExtra(Intent.EXTRA_TEXT)
//
//    }
}