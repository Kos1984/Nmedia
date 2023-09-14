package ru.netology.nmedia.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post


class PostDraftRepository(context: Context) { // класс для сохранения и чтения черновика нового поста  в SharedPreferences

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private val key = "draft"

    //private val typeToken = TypeToken.getParameterized(List::class.java, String::class.java).type

    fun readDraft(): String? {
        var temp:String?
        temp = prefs.getString(key, "")
        return temp
    }

    fun writeDraft(text : String) {
        prefs.edit().apply {
            putString(key, text).apply()
        }
    }

    fun clearDraft()= prefs.edit().putString(key, "null").apply()
}



