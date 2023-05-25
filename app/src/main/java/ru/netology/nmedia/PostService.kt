package ru.netology.nmedia

object PostService{

    fun countToString (count: Long) : String {
        val result = when (count) {
            in 1..999 ->   count.toString()
            in 1000 .. 9_999 -> "%.1f".format(count / 1000.0) + "K"
            in 10_000 .. 999_999-> "%.0f".format(count / 1000.0) + "K"
            in 1_000_000 .. Long.MAX_VALUE -> "%.1f".format(count.toDouble()/ 1_000_000.0)+ "M"
            else -> "wow"
        }
        return result
    }
}