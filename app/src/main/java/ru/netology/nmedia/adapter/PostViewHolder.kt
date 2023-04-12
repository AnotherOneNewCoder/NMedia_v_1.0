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

            tvAmountViews.text = Convert.toConvert(post.countViews)

            ivLikes.isChecked = post.likedByMe
            ivLikes.text = Convert.toConvert(post.countLikes)
            ivReposts.isChecked = post.sharedByMe
            ivReposts.text = Convert.toConvert(post.countShares)
            ibMenu.isChecked= false

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
                        ibMenu.isChecked = true
                        when(itemMenu.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                ibMenu.isChecked= false
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                ibMenu.isChecked= false
                                true
                            }
                            else -> {
                                ibMenu.isChecked= false
                                false
                            }
                        }
                    }


                }.show()

            }
            ibMenu.isChecked= false



        }
    }
}