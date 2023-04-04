package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory: PostRepository {

    private var post = listOf(
        Post(
        id = 2,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это второй пост и новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "23 мая в 14:52",
        likedByMe = false,
        sharedByMe = false,
        viewedByMe = false
    ),Post (
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        likedByMe = false,
        sharedByMe = false,
        viewedByMe = false
    )
    )
    private val data = MutableLiveData(post)

    override fun getData(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        post = post.map { post ->
            if (post.id == id) {
                post.copy(
                    countLikes = if (post.likedByMe) post.countLikes - 1 else post.countLikes + 1,
                    likedByMe = !post.likedByMe
                )
            } else {
                post
            }
        }
        data.value = post
    }


    override fun shareById(id: Long) {
        post = post.map {post ->
            if (post.id == id){
                post.copy(
                    countShares = post.countShares + 1,
                    sharedByMe = true
                )
            } else
                post
        }

        data.value = post
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