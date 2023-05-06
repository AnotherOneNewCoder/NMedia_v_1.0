package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post


class PostRepositorySQLiteImpl(
    private val dao: PostDao,
    private val context: Context
) : PostRepository {

    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    // для черновика
    companion object {

        private const val FILE_NAME = "drafts.json"
    }
    var drafts = emptyList<String>()
        set(value) {
            field = value
            sync()
            dataJson.value = drafts
        }
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, String::class.java).type
    private val dataJson = MutableLiveData(drafts)





    init {
        posts = dao.getAll()
        data.value = posts
        // для черновика
        val file = context.filesDir.resolve(FILE_NAME)
        if (file.exists()) {
            // если файл есть - читаем
            context.openFileInput(FILE_NAME).bufferedReader().use {
                drafts = gson.fromJson(it, type)
                dataJson.value = drafts
            }
        } else {
            // если нет, записываем пустой массив
            sync()
        }



    }

    override fun getData(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                countLikes = if (it.likedByMe) it.countLikes -1 else it.countLikes +1
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        dao.shareByID(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                sharedByMe = true,
                countShares = it.countShares + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        posts = posts.filter {it.id != id}
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }

    override fun getDataDraft(): LiveData<List<String>> =dataJson

    override fun addDraft(draft: String) {
        drafts = drafts.plus(draft)
        dataJson.value = drafts
    }

    override fun showDraft() : String {
        if (!drafts.isEmpty()) {
            return drafts.last()
        } else
            return ""
    }

    override fun clearDrafts() {
        drafts = emptyList()
        dataJson.value = drafts
    }

    private fun sync() {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(drafts))
        }

    }








}