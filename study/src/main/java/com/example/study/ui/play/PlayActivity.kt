package com.example.study.ui.play

import android.content.Context
import android.content.Intent
import cn.jzvd.Jzvd
import com.blankj.utilcode.util.ToastUtils
import com.example.common.base.BaseActivity
import com.example.study.R
import com.example.study.databinding.ActivityPlayBinding
import com.example.study.net.HasCoursePermission
import com.example.study.net.StudiedRsp
import com.example.study.ui.play.LessonSection.Companion.ITEM_TYPE_CONTENT
import com.example.study.ui.play.LessonSection.Companion.ITEM_TYPE_TITLE
import kotlinx.android.synthetic.main.activity_play.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayActivity : BaseActivity<ActivityPlayBinding>() {

    private val model: PlayViewModel by viewModel()
    private val lessonSections = arrayListOf<LessonSection>()
    private val adapter = PlayAdapter { position ->
        lessonSections.forEachIndexed { index, lessonSection ->
            lessonSection.isPlaying.set(index == position)
        }
        lessonSections[position].key?.let {
            model.getPlayInfo(it)
        }
    }

    companion object {
        private const val INTENT_KEY_COURSE_INFO = "course_info"
        fun openPlay(context: Context, course: StudiedRsp.Data) {
            context.startActivity(Intent(context, PlayActivity::class.java).apply {
                putExtra(INTENT_KEY_COURSE_INFO, course)
            })
        }

    }

    override fun getLayoutRes() = R.layout.activity_play

    override fun initView() {
        super.initView()


    }

    override fun initConfig() {
        super.initConfig()
    }

    override fun initData() {
        super.initData()

        val course = intent.getParcelableExtra<StudiedRsp.Data>(INTENT_KEY_COURSE_INFO)
            ?: return ToastUtils.showShort("课程Id为空")
        mBinding.info = course
        mBinding.adapter = adapter

        model.hasPermission(course.id)
        model.livePermissionResult.observeKt {
            if (it?.is_true == HasCoursePermission.HAS_COURSE_PERMISSION) {
                model.getChapters(course.id)
            } else {
                ToastUtils.showShort("您没有课程权限")
            }
        }
        model.liveChapterList.observeKt {
            lessonSections.clear()
            it?.forEach { charpterItem ->
                lessonSections.add(
                    LessonSection(
                        ITEM_TYPE_TITLE,
                        "第${charpterItem.bsort}章 ${charpterItem.title}"
                    )
                )
                charpterItem.lessons?.forEach { lession ->
                    lessonSections.add(
                        LessonSection(
                            ITEM_TYPE_CONTENT,
                            "${charpterItem.bsort}-${lession.bsort} ${lession.name}",
                            lession.key,
                            lession.is_free == 1
                        )
                    )
                }
            }
            adapter.upDateList(lessonSections)

        }
        model.livePlayCourse.observeKt { info ->
            val vUrl = "https:${info?.play_urls?.hls?.urls?.hd}"
            val title = info?.last_play_info?.title
            jz_video_study.setUp(vUrl, title)
            jz_video_study.startVideo()
        }

    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}