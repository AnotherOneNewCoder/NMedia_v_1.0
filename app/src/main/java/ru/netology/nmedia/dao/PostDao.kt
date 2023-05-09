package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    @Query(
        """
           UPDATE PostEntity SET
               countLikes = countLikes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = :id
        """
    )
    fun likeById(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               countShares = countShares + 1,
               sharedByMe = 1
               
           WHERE id = :id
        """
    )
    fun shareByID(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               countViews = countViews + 1,
               viewedByMe = 1
           WHERE id = :id
        """
    )
    fun viewByID(id: Long)


    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)
}