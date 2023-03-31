package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

import ru.netology.nmedia.databinding.ActivityMainBinding

import ru.netology.nmedia.service.Convert
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {

                ivAvatar?.setImageResource(R.drawable.ic_launcher_netology_foreground)

                tvpublished.text = post.published
                tvContent.text = post.content
                tvAuthor.text = post.author
                tvAmountLikes.text = Convert.toConvert(post.countLikes)
                tvAmountReposts.text = Convert.toConvert(post.countShares)
                tvAmountViews.text = Convert.toConvert(post.countViews)

                if (post.likedByMe) ivLikes.setImageResource(R.drawable.liked) else ivLikes.setImageResource(R.drawable.likes)
                if (post.sharedByMe) ivReposts.setImageResource(R.drawable.reposted) else ivReposts.setImageResource(R.drawable.repost)
                if (post.viewedByMe) ivViews.setImageResource(R.drawable.veiwedalready) else ivViews.setImageResource(R.drawable.veiwed)


            }
        }

        binding.ivLikes.setOnClickListener {
            viewModel.like()
        }
        binding.ivReposts.setOnClickListener {
            viewModel.share()
        }
        binding.ivViews.setOnClickListener {

            viewModel.view()
        }





    }
}