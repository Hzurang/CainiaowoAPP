package com.example.study.ui.play

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.RecyclerView
import com.example.study.databinding.ItemPlayStudyBinding
import com.example.study.databinding.ItemTitleBinding
import com.example.study.ui.play.LessonSection.Companion.ITEM_TYPE_CONTENT
import com.example.study.ui.play.LessonSection.Companion.ITEM_TYPE_TITLE
import java.util.ArrayList

/**
 * title、content分类型
 */
class PlayAdapter(val block: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val lessonSections = ArrayList<LessonSection>()
    fun upDateList(lessonSections: ArrayList<LessonSection>) {
        this.lessonSections.clear()
        this.lessonSections.addAll(lessonSections)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {

        return lessonSections[position].viewType
    }

    override fun getItemCount() = lessonSections.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_TITLE) {
            return TitleVH.create(parent)
        } else {
            return ItemVH.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = lessonSections[position]
        if (holder is TitleVH) {
            holder.bind(section)
        } else if (holder is ItemVH) {
            holder.bind(section)
            holder.itemView.setOnClickListener {
                block(position)//传递给外界，相当于接口回调了
            }
        }
    }

    class TitleVH(val binding: ItemTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup) = TitleVH(
                ItemTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        fun bind(section: LessonSection) {
            binding.title = section.title
            binding.executePendingBindings()
        }
    }

    class ItemVH(val binding: ItemPlayStudyBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup) = ItemVH(
                ItemPlayStudyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        fun bind(section: LessonSection) {
            binding.info = section
            binding.executePendingBindings()
        }
    }


}

data class LessonSection(
    val viewType: Int,//类型，分组的标记，0 title 1 content item
    val title: String?,//章节的标题title
    val key: String? = null,//用于lesson 播放信息的key
    val tryIt: Boolean = false,//是否可试看
) {
    val isPlaying = ObservableBoolean(false)

    companion object {
        const val ITEM_TYPE_TITLE = 0
        const val ITEM_TYPE_CONTENT = 1
    }
}