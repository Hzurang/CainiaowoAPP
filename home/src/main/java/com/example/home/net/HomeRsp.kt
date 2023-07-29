package com.example.home.net

import androidx.annotation.Keep

class HomeList : ArrayList<HomeList.HomeListItem>() {
    /*
    * {
      "created_time": "2019-08-01 15:11:08",
      "data_url": "/allocation/component/list", // 无用，调用模块组件列表接口
      "id": 5, // 模块ID
      "is_show_more": 0, // 是否显示更多按钮
      "layout": 0, // 布局方式，0：单行布局 1：多行布局（可由客户端自己决定布局方式）
      "more_redirect_url": null, // 跳转更多的地址
      "scroll": 0, // 是否滚动 0不滚动  1单行滚动 2双行滚动
      "sub_title": null, // 子标题
      "title": "首页banner", // 模块标题
      "type": 3 // 模块类型： (1 老师) (2 课程) (3 banner) (4 ad) (5 班级) (6 合作伙伴) (7 内容块) (8 图标) (9 学员故事)
    }
    * */
    @Keep
    data class HomeListItem(
        val created_time: String?,
        val data_url: String?,
        val id: Int,
        val is_show_more: Int,
        val layout: Int,
        val more_redirect_url: String?,
        val scroll: Int,
        val sub_title: String?,
        val title: String?,
        val type: Int
    )
}

class BannerList : ArrayList<BannerList.BannerListItem>() {
    @Keep
    data class BannerListItem(
        val client_url: Any?,
        val created_time: String?,
        val id: Int,
        val img_url: String?,
        val name: Any?,
        val order_num: Int,
        val page_show: Int,
        val redirect_url: String?,
        val state: Int,
        val type: String?
    )
}

/*
* id 1 就业班；2、新上好课；3、限时免费；4、Android精选；5、Ai精选；6、大数据精选；7、10w学员推荐；8、人气讲师；9、黄埔军校
 */

//就业班
class JobClassList() : ArrayList<JobClassList.JobClassListItem>() {
    @Keep
    data class JobClassListItem(
        val apply_deadline_time: String?,
        val course: Course?,
        val created_time: String?,
        val current_price: Double,
        val graduate_time: String?,
        val id: Int,
        val is_apply_stop: Int,
        val learning_mode: Int,
        val lessons_count: Int,
        val lessons_time: Int,
        val number: Int,
        val original_price: Double,
        val start_class_time: String?,
        val status: Int,
        val student_count: Int,
        val student_limit: Int,
        val study_expiry_day: Int,
        val teacher_ids: String?,
        val title: String?
    ) {
        @Keep
        data class Course(
            val h5site: String?,
            val id: Int,
            val img_url: String?,
            val name: String?,
            val website: String?
        )
    }

}

//新上好课
class NewClassList : ArrayList<HomeCourseItem>()
class LimitFreeList : ArrayList<HomeCourseItem>()
class AndroidSelection : ArrayList<HomeCourseItem>()
class AISelection : ArrayList<HomeCourseItem>()
class BDList : ArrayList<HomeCourseItem>()
class Suggest10w : ArrayList<HomeCourseItem>()

@Keep
data class HomeCourseItem(
    val brief: String?,
    val comment_count: Int,
    val cost_price: Double,
    val expiry_day: Int,
    val finished_lessons_count: Int,
    val first_category: FirstCategory?,
    val id: Int,
    val img_url: String?,
    val is_free: Int,
    val is_live: Int,
    val is_pt: Boolean,
    val is_share_card: Boolean,
    val lessons_count: Int,
    val lessons_played_time: Int,
    val name: String?,
    val now_price: Double
) {
    @Keep
    data class FirstCategory(
        val code: String?,
        val id: Int,
        val title: String?
    )
}


//人气讲师
class PopTeacherList : ArrayList<PopTeacherList.PopTeacherListItem>() {
    @Keep
    data class PopTeacherListItem(
        val brief: String?,
        val company: String?,
        val id: Int,
        val job_title: String?,
        val logo_url: String?,
        val teacher_course: List<TeacherCourse>?,
        val teacher_name: String?
    ) {
        @Keep
        data class TeacherCourse(
            val cost_price: Double,
            val id: Int,
            val img_url: String?,
            val lessons_played_time: Int,
            val comment_count: Int,
            val name: String?,
            val now_price: Double,
            val score: Int
        )
    }
}