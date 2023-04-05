package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {
    private var idCounter: Long = 0L
    private var posts = listOf(
        Post(
            id = ++idCounter,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это второй пост и новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "23 мая в 14:52",
            likedByMe = false,
            sharedByMe = false,
            viewedByMe = false
        ), Post(
            id = ++idCounter,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            sharedByMe = false,
            viewedByMe = false
        )
    ).reversed()
    private val data = MutableLiveData(posts)

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
            posts = listOf(post.copy(id = ++idCounter)) + posts
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
//
//    override fun view() {
//        post = post.copy(
//            countViews = post.countViews + 1,
//            viewedByMe = true
//        )
//        data.value = post
//    }
    }