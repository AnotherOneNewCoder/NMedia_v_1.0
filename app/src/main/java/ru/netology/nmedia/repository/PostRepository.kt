package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getData(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)

    fun removeById(id: Long)

    fun save(post: Post)
    fun getDataDraft(): LiveData<List<String>>
    fun addDraft(draft: String)
    fun clearDrafts()
    fun showDraft() : String




//    fun view()
}