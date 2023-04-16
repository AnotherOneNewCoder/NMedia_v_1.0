package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener

import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post


import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostContract = registerForActivityResult(NewPostActivity.Contract) { result->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }


        val adapter = PostAdapter(
            object : PostListener {
                override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                    newPostContract.launch(post.content)
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

            }
        )

//        viewModel.edited.observe(this) {
//            if (it.id == 0L) {
//                return@observe
//            }
//            with(activityMainBinding) {
//                gropEditor.visibility = View.VISIBLE
//                tvEditedPost.text = it.author
//                tvEditedPostId.text = it.id.toString()
//                ivCancel.setOnClickListener {
//                    with(activityMainBinding.etContent) {
//                        viewModel.cancel()
//
//                        setText("")
//                        clearFocus()
//                        AndroidUtils.hideKeyboard(this)
//                        with(activityMainBinding) {
//                            gropEditor.visibility = View.GONE
//                        }
//
//                    }
//                }
//            }
//            activityMainBinding.etContent.requestFocus()
//            activityMainBinding.etContent.setText(it.content)
//        }

//        activityMainBinding.ivSave.setOnClickListener {
//            with(activityMainBinding.etContent) {
//                val content = text.toString()
//                if (content.isNullOrBlank()) {
//                    Toast.makeText(this@MainActivity, R.string.cont_error, Toast.LENGTH_SHORT)
//                        .show()
//                    return@setOnClickListener
//                }
//                viewModel.changeContent(content)
//                viewModel.save()
//
//
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//                activityMainBinding.gropEditor.visibility = View.GONE
//            }
//        }




        activityMainBinding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        activityMainBinding.add?.setOnClickListener {
            newPostContract.launch("")
        }


    }
}