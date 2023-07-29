package com.example.study.net

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

//用户学习情况
@Keep
data class StudyInfoRsp(
    val rank: Int,              //学习总排行榜
    val today_study_time: Int,  //今日学习时长
    val total_study_time: Int   //总学习时长(分钟)
)

//已经学习的课程数据
@Keep
data class StudiedRsp(
    val datas: List<Data>?,
    val page: Int,
    val size: Int,
    val total: Int,
    val total_page: Int
) {
    @Parcelize
    @Keep
    data class Data(
        val brief: String?,
        val comment_count: Int,
        val cost_price: Double,
        val course: Course?,
        val course_type: Int,
        val current_price: Double,
        val get_method: Int,
        val first_category: FirstCategory?,
        val id: Int,
        val left_expriy_days: Int,  //剩余天数
        var img_url: String?,       //接口返回的，部分残缺了http://host,
        val is_distribution: Boolean,
        val is_free: Int,
        val is_live: Int,
        val is_pt: Boolean,
        val lessons_count: String?,
        val lessons_played_time: Int,
        val name: String?,
        val now_price: Double,
        val number: Int,
        val original_price: Double,
        val progress: Double,
        val title: String?
    ) : Parcelable {
        @Parcelize
        @Keep
        data class Course(
            val h5site: String?,
            val id: Int,
            val img_url: String?,
            val name: String?,
            val website: String?
        ) : Parcelable

        @Parcelize
        @Keep
        data class FirstCategory(
            val code: String?,
            val id: Int,
            val title: String?
        ) : Parcelable
    }
}

//已经购买的课程，班级 信息

@Keep
data class BoughtRsp(
    val datas: List<Data>?,
    val page: Int,
    val size: Int,
    val total: Int,
    val total_page: Int
) {
    @Keep
    data class Data(
        val cancel_time: String?,
        val course: Course?,
        val created_time: String?,
        val get_method: Int,
        val id: Int,
        val is_expired: Boolean,
        val left_expriy_days: Int,//剩余天数？
        val order_time: String?,
        val product_id: Int,
        val product_type: Int
    ) {
        @Keep
        data class Course(
            val brief: String?,
            val comment_count: Int,
            val cost_price: Double,
            val course: Course?,
            val first_category: FirstCategory?,
            val id: Int,
            val img_url: String?,
            val is_distribution: Boolean,
            val is_free: Int,
            val is_live: Int,
            val is_pt: Boolean,
            val lessons_played_time: Int,
            val name: String?,
            val now_price: Double,
            val number: Int,
            val progress: Double,
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

            @Keep
            data class FirstCategory(
                val code: String?,
                val id: Int,
                val title: String?
            )
        }
    }
}

//endregion

//region play class


@Keep
data class HasCoursePermission(
    val cancel_time: String,
    val days: Double,
    val get_method: Int,
    val is_true: Int, //1 有 0 无
    val type: String,
) {
    companion object {
        const val HAS_COURSE_PERMISSION = 1
        const val NO_COURSE_PERMISSION = 0
    }
}


//课程的课时章节
class ChapterListRsp : ArrayList<ChapterItem>()

@Keep
data class ChapterItem(
    val bsort: Int,
    val class_id: Int,
    val id: Int,
    val lessons: List<Lesson>?,
    val title: String?
) {
    @Keep
    data class Lesson(
        val bsort: Int,
        val is_free: Int,
        val is_live: Int,
        val key: String?,
        val lesson_id: Int,
        val live_begin_time: String?,
        val live_end_time: String?,
        val live_plan_begin_time: String?,
        val live_plan_end_time: String?,
        val live_status: Int,
        val name: String?,
        val state: Int,
        val video_duration: String?,
        val video_info_duration: Int
    )
}


//课程播放相关，通过key查询返回
@Keep
data class PlayCourseRsp(
    val is_live: Int,
    val last_play_info: LastPlayInfo?,
    val play_urls: PlayUrls?,
    val video_info_id: Int
) {
    @Keep
    data class LastPlayInfo(
        val key: String?,
        val position: Int,
        val title: String?
    )

    @Keep
    data class PlayUrls(
        val flv: Flv?,
        val hls: Hls?,
        val mp4: Mp4?,
        val origin: String?,
        val rtmp: Rtmp?
    ) {
        @Keep
        data class Flv(
            val is_encryption: Int,
            val urls: Urls?
        ) {
            @Keep
            data class Urls(
                val hd: String?,
                val sd: String?,
                val shd: String?
            )
        }

        @Keep
        data class Hls(
            val is_encryption: Int,
            val urls: Urls?
        ) {
            @Keep
            data class Urls(
                val hd: String?,
                val sd: String?,
                val shd: String?
            )
        }

        @Keep
        data class Mp4(
            val is_encryption: Int,
            val urls: Urls?
        ) {
            @Keep
            data class Urls(
                val hd: String?,
                val sd: String?,
                val shd: String?
            )
        }

        @Keep
        data class Rtmp(
            val is_encryption: Int,
            val urls: Urls?
        ) {
            @Keep
            data class Urls(
                val hd: String?,
                val sd: String?,
                val shd: String?
            )
        }
    }
}