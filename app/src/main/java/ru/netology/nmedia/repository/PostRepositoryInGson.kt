package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryInGson(
    private val context: Context
): PostRepository {

    companion object {

        private const val FILE_NAME = "post.json"
    }

    var posts = emptyList<Post>()
        set(value) {
            field = value
            sync()
            data.value = posts
        }

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type



    private val data = MutableLiveData(posts)


    init {
        val file = context.filesDir.resolve(FILE_NAME)
        if (file.exists()) {
            // если файл есть - читаем
            context.openFileInput(FILE_NAME).bufferedReader().use {
                posts = gson.fromJson(it, type)
                data.value = posts
            }
        } else {
            // если нет, записываем пустой массив
            sync()
        }
    }




    override fun getData(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    countLikes = if (post.likedByMe) post.countLikes - 1 else post.countLikes + 1,
                    likedByMe = !post.likedByMe
                )
            } else {
                post
            }
        }
        data.value = posts
    }


    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    countShares = post.countShares + 1,
                    sharedByMe = true
                )
            } else
                post
        }

        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter {
            it.id != id
        }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(post.copy(id = (posts.firstOrNull()?.id ?: 0L) + 1)) + posts
            data.value = posts
            return
        }
        else {
            posts = posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
            data.value = posts
        }
    }
    private fun sync() {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }

    }

//    private fun readPosts(): List<Post> {
//        val file = context.filesDir.resolve(FILE_NAME)
//        return if (file.exists()) {
//        context.openFileInput(FILE_NAME).bufferedReader().use {
//            gson.fromJson(it, type)
//        }
//        } else {
//            emptyList()
//        }
//    }


}