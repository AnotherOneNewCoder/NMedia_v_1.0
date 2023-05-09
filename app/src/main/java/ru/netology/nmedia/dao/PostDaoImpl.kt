package ru.netology.nmedia.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dto.Post
import android.database.Cursor

class PostDaoImpl(
    private val db: SQLiteDatabase
): PostDao {

    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIEWED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_COUNT_LIKES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_COUNT_SHARES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_COUNT_VIEWS} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIDEO_LINK} TEXT NOT NULL
        );  
        """.trimIndent()
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        const val COLUMN_SHARED_BY_ME = "sharedByMe"
        const val COLUMN_VIEWED_BY_ME = "viewedByMe"
        const val COLUMN_COUNT_LIKES = "countLikes"
        const val COLUMN_COUNT_SHARES = "countShares"
        const val COLUMN_COUNT_VIEWS = "countViews"
        const val COLUMN_VIDEO_LINK = "videoLink"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_LIKED_BY_ME,
            COLUMN_SHARED_BY_ME,
            COLUMN_VIEWED_BY_ME,
            COLUMN_COUNT_LIKES,
            COLUMN_COUNT_SHARES,
            COLUMN_COUNT_VIEWS,
            COLUMN_VIDEO_LINK
        )
    }


    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }


    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) !=0,
                sharedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARED_BY_ME)) !=0,
                viewedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VIEWED_BY_ME)) !=0,
                countLikes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_COUNT_LIKES)),
                countShares = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_COUNT_SHARES)),
                countViews = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_COUNT_VIEWS)),
                videoLink = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO_LINK))
            )
        }
    }



    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostColumns.COLUMN_AUTHOR, "Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, "Now")
            put(PostColumns.COLUMN_VIDEO_LINK, "future link")
        }
        val id = if (post.id != 0L) {
            db.update(
                PostColumns.TABLE,
                values,
                "${PostColumns.COLUMN_ID} = ?",
                arrayOf(post.id.toString())
            )
            post.id
        } else {
            db.insert(PostColumns.TABLE, null, values)
        }
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               countLikes = countLikes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun shareByID(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               countShares = countShares + 1
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun viewByID(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               countViews = countViews + 1
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }
}