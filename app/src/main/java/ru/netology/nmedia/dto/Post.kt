package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var countLikes: Int = 999,
    var countShares: Int = 10999,
    var countViews: Int = 9999999,

)
