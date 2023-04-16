package ru.netology.nmedia.dto

data class Post(
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

)
