package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.R.drawable.reposted
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.service.Convert

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding) {

            ivAvatar?.setImageResource(R.drawable.ic_launcher_netology_foreground)

            tvpublished?.text = post.published
            tvContent?.text = post.content
            tvAuthor?.text = post.author
            tvAmountLikes?.text = Convert.toConvert(post.countLikes)
            tvAmountReposts?.text = Convert.toConvert(post.countShares)
            tvAmountViews?.text = Convert.toConvert(post.countViews)

            if (post.likedByMe) {
                ivLikes?.setImageResource(R.drawable.likes)
            }

            ivLikes?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                post.countLikes = if (post.likedByMe) post.countLikes + 1 else post.countLikes -1
                tvAmountLikes?.text = Convert.toConvert(post.countLikes)
                ivLikes.setImageResource(
                    if (post.likedByMe) R.drawable.liked else R.drawable.likes
                )
            }
            ivReposts?.setOnClickListener {
                post.countShares++
                tvAmountReposts?.text = Convert.toConvert(post.countShares)
                ivReposts.setImageResource(reposted)
            }
            ivViews?.setOnClickListener {
                post.countViews++
                tvAmountViews?.text = Convert.toConvert(post.countViews)
                ivViews?.setImageResource(R.drawable.veiwedalready)
            }

        }



    }
}