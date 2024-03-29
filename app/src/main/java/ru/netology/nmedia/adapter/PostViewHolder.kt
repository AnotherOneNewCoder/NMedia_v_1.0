package ru.netology.nmedia.adapter


import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

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
            if (!post.videoLink.isEmpty()) {
                link.text = post.videoLink
                groupEditor.visibility = View.GONE
                videoLink.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoLink))
                    startActivity(it.context,intent,null)
                }
                playVideo.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoLink))
                    startActivity(it.context,intent,null)


                }

            } else {
                groupEditor.visibility = View.GONE
            }

            root.setOnClickListener{
                listener.onDetails(post)
            }
            ivLikes.setOnClickListener {
                listener.onLike(post)

            }
            ivReposts.setOnClickListener {
                listener.onShare(post)
            }
            ivViews.setOnClickListener {
                Toast.makeText(it.context, "Future Impl", Toast.LENGTH_LONG).show()
            }

            ibMenu.setOnClickListener {
                val popmenu = PopupMenu(it.context, it)
                popmenu.apply {
                    inflate(R.menu.post_options)
                    setOnMenuItemClickListener { itemMenu->
                        ibMenu.isChecked= true
                        when(itemMenu.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> {

                                false
                            }
                        }
                    }


                }.show()
                popmenu.setOnDismissListener {
                    ibMenu.isChecked = false
                }

            }




        }
    }


}