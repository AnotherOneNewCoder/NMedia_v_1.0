package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding

import ru.netology.nmedia.service.Convert
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { posts ->
            activityMainBinding.container.removeAllViews()
            val views = posts.map { post ->
                val cardPostBinding =
                    CardPostBinding.inflate(layoutInflater, activityMainBinding.root, false)
                with(cardPostBinding) {

                    ivAvatar.setImageResource(R.drawable.ic_launcher_netology_foreground)

                    tvpublished.text = post.published
                    tvContent.text = post.content
                    tvAuthor.text = post.author
                    tvAmountLikes.text = Convert.toConvert(post.countLikes)
                    tvAmountReposts.text = Convert.toConvert(post.countShares)
                    tvAmountViews.text = Convert.toConvert(post.countViews)

                    ivLikes.setImageResource(if (post.likedByMe) R.drawable.liked else R.drawable.likes)
//                    ivReposts.setImageResource(if (post.sharedByMe) R.drawable.reposted else R.drawable.repost)
//                    ivViews.setImageResource(if (post.viewedByMe) R.drawable.veiwedalready else R.drawable.veiwed)
                    ivLikes.setOnClickListener {
                        viewModel.likeById(post.id)
                    }

                }
                cardPostBinding
            }
            views.forEach {
                activityMainBinding.container.addView(it.root)
            }
        }


//        binding.ivLikes.setOnClickListener {
//            viewModel.like()
//        }
//        binding.ivReposts.setOnClickListener {
//            viewModel.share()
//        }
//        binding.ivViews.setOnClickListener {
//
//            viewModel.view()
//        }


    }
}