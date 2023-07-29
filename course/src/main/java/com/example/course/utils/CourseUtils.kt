package com.example.course.utils

import com.example.course.net.CourseList

object CourseUtils {

    @JvmStatic
    fun parseStudentComment(info: CourseList.Data?): String {
        return "${info?.lessons_played_time} ${info?.comment_count}人评价"
    }

    @JvmStatic
    fun parseFree(info: CourseList.Data?): String {
        return if (info?.is_free == 1) "免费" else "￥${info?.now_price}"
    }
}