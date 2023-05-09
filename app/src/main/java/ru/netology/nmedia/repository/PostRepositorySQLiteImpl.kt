package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity


class PostRepositorySQLiteImpl(
    private val dao: PostDao,
    private val context: Context
) : PostRepository {

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

    override fun getData() = dao.getAll().map { list->
        list.map { it.toDto() }
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareByID(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
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