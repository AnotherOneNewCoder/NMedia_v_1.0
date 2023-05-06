package ru.netology.nmedia.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dao.PostDaoImpl

class AppDb private constructor(db: SQLiteDatabase) {
    val postDao: PostDao = PostDaoImpl(db)

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: AppDb(
                    buildDatabase(context, arrayOf(PostDaoImpl.DDL))
                ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, DDls: Array<String>) = DbHelper(
            context, 1, "app.db", DDls
        ).writableDatabase
    }
}
class DbHelper(context: Context, dbVersion: Int, dbName: String, private val DDls: Array<String>) :
        SQLiteOpenHelper(context, dbName, null, dbVersion) {

    override fun onCreate(db: SQLiteDatabase) {
        DDls.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}