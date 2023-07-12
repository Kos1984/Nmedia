package ru.netology.nmedia.utils

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
// описываем  как будем читать и записывать текст в Bundle
object StringProperty: ReadWriteProperty<Bundle, String?> {
    override fun getValue(thisRef: Bundle, property: KProperty<*>): String? {
        return thisRef.getString(property.name)
    }

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: String?) {
        thisRef.putString(property.name, value)
    }
}