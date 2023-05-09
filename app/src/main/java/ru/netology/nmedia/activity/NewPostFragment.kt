package ru.netology.nmedia.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback

import androidx.fragment.app.Fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController


import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.service.StringArg

import ru.netology.nmedia.viewmodel.PostViewModelSQLImpl

class NewPostFragment : Fragment() {
    companion object {
        var Bundle.stringArg: String? by StringArg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater)
        val viewModel: PostViewModelSQLImpl by viewModels(
            ownerProducer = ::requireParentFragment
        )


        var isNewPost = false
        if (arguments?.stringArg.isNullOrEmpty()) {
            isNewPost = true
        }

        if (isNewPost) {


            binding.etContent.setText(viewModel.showDradft())
            viewModel.clearDrafts()
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        viewModel.addDrafts(binding.etContent.text.toString())
                        Toast.makeText(context, "New Post", Toast.LENGTH_LONG).show()
                        findNavController().navigateUp()
                    }

                }
            )
        }


        arguments?.stringArg
            ?.let(binding.etContent::setText)
        binding.etContent.requestFocus()
        binding.ok.setOnClickListener {

            val text = binding.etContent.text.toString()
            if (!text.isBlank()) {
                viewModel.changeContent(text)
                viewModel.save()
            }

            findNavController().navigateUp()
        }

        return binding.root
    }


}