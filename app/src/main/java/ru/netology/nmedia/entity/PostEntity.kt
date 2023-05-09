package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val sharedByMe: Boolean = false,
    val viewedByMe: Boolean = false,
    val countLikes: Int = 999,
    val countShares: Int = 10999,
    val countViews: Int = 9999999,
    val videoLink: String = ""
) {
    fun toDto() = Post(id, author, content, published, likedByMe, sharedByMe, viewedByMe, countLikes, countShares, countViews, videoLink)

    companion object {
        fun fromDto(post: Post) = with(post) {PostEntity(id, author, content, published, likedByMe, sharedByMe, viewedByMe, countLikes, countShares, countViews, videoLink)}

    }
}