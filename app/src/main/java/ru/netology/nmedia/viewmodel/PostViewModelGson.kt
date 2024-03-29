package ru.netology.nmedia.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import ru.netology.nmedia.dto.Post
//import ru.netology.nmedia.repository.PostRepository
//import ru.netology.nmedia.repository.PostRepositoryInGson
//
//class PostViewModelGson(application: Application): AndroidViewModel(application) {
//
//    private val emptyPost = Post(
//        id = 0,
//        author = "Me",
//        content = "",
//        published = "Now",
//        likedByMe = false,
//        sharedByMe = false,
//        viewedByMe = false,
//        countLikes = 0,
//        countShares = 0,
//        countViews = 0
//    )
//
//    private val repository: PostRepository = PostRepositoryInGson(application)
//
//    val data: LiveData<List<Post>> = repository.getData()
//    fun likeById(id: Long) = repository.likeById(id)
//    fun shareById(id: Long) = repository.shareById(id)
//    fun removeById(id: Long) = repository.removeById(id)
//
//
//
//
//    val edited = MutableLiveData(emptyPost)
//
//    fun save() {
//        edited.value?.let {
//            repository.save(it)
//        }
//        edited.value = emptyPost
//    }
//    fun cancel() {
//        edited.value = emptyPost
//    }
//    fun edit(post: Post) {
//        edited.value =post
//    }
//    fun changeContent(content: String) {
//        edited.value?.let { post->
//            if (content != post.content)
//                edited.value = post.copy(content = content)
//        }
//    }
//
//}