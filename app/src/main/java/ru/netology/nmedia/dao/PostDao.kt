package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

@Dao // Помечает класс как объект доступа к данным.
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC") // запрашиваем все посты
    fun getAll(): LiveData<List<PostEntity>>

    @Insert // добавление элемента в базу
    fun insert (post: PostEntity)

    @Query("UPDATE PostEntity SET content =:content WHERE id = :id")
    fun updateContentById(id: Long, content: String)
    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else updateContentById(post.id, post.content)
    @Query("""
           UPDATE PostEntity SET
               likesCount = likesCount + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = :id;
        """)
    fun likeById(id: Long)
    @Query("UPDATE PostEntity SET ShareCount = ShareCount + 1 WHERE id = :id")
    fun shareById(id: Long)
    @Query("DELETE FROM PostEntity WHERE id = :id ")
    fun removeById(id: Long)
}