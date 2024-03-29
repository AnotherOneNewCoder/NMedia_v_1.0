package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository

import ru.netology.nmedia.repository.PostRepositorySQLiteImpl


private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = "",
    sharedByMe = false,
    viewedByMe = false,
    countLikes = 0,
    countShares = 0,
    countViews = 0,
    videoLink = ""

)





class PostViewModelSQLImpl(application: Application): AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao,
        application
    )

    val data = repository.getData()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun addDrafts(draft: String) = repository.addDraft(draft)
    fun clearDrafts() = repository.clearDrafts()
    fun showDradft() = repository.showDraft()






    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }
    fun cancel() {
        edited.value = empty
    }
    fun edit(post: Post) {
        edited.value =post
    }
    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

}