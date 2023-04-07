package ru.netology.nmedia.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.service.Convert



class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener,

) : ViewHolder(binding.root) {
    fun bind(post: Post){
        with(binding) {

            ivAvatar.setImageResource(R.drawable.ic_launcher_netology_foreground)

            tvpublished.text = post.published
            tvContent.text = post.content
            tvAuthor.text = post.author
            tvAmountLikes.text = Convert.toConvert(post.countLikes)
            tvAmountReposts.text = Convert.toConvert(post.countShares)
            tvAmountViews.text = Convert.toConvert(post.countViews)

            ivLikes.setImageResource(if (post.likedByMe) R.drawable.liked else R.drawable.likes)
            ivReposts.setImageResource(if (post.sharedByMe) R.drawable.reposted else R.drawable.repost)
//                    ivViews.setImageResource(if (post.viewedByMe) R.drawable.veiwedalready else R.drawable.veiwed)
            ivLikes.setOnClickListener {
                listener.onLike(post)

            }
            ivReposts.setOnClickListener {
                listener.onShare(post)
            }
            ibMenu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_options)
                    setOnMenuItemClickListener { itemMenu->
                        when(itemMenu.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

        }
    }
}