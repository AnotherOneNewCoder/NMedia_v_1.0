package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.stringArg
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener


import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post


//import ru.netology.nmedia.viewmodel.PostViewModelGson
import ru.netology.nmedia.viewmodel.PostViewModelSQLImpl

class FeedFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val activityMainBinding = FragmentFeedBinding.inflate(layoutInflater)
        val viewModel: PostViewModelSQLImpl by viewModels(
            ownerProducer = ::requireParentFragment
        )
//        val viewModel: PostViewModel by viewModels()





        val adapter = PostAdapter(
            object : PostListener {
                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                    val text = post.content
                    findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply {
                        stringArg = text
                    }
                    )
                }

                override fun onLike(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.shareById(post.id)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.content)
                        type = "text/plain"
                    }
                    val startIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(startIntent)
                }

                override fun onDetails(post: Post) {
                    findNavController().navigate(R.id.action_feedFragment_to_detailsPostFragment,
                    Bundle().apply {
                        stringArg = post.id.toString()
                    })
                }

            }
        )



        activityMainBinding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        activityMainBinding.add?.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return activityMainBinding.root
    }


}