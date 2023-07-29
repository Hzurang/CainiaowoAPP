package com.example.home.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.webview.WebViewActivity
import com.example.home.databinding.ItemHomeCourseBinding
import com.example.home.databinding.ItemJobClassBinding
import com.example.home.net.HomeCourseItem
import com.example.home.net.JobClassList
import com.example.home.net.NewClassList

class HomeCourseAdapter(private val mList: List<HomeCourseItem>) :
    RecyclerView.Adapter<HomeCourseAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.create(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

    class VH(val binding: ItemHomeCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): VH {
                val itemBinding =
                    ItemHomeCourseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return VH(itemBinding)
            }
        }

        fun bind(info: HomeCourseItem) {
            binding.info = info
            binding.tvOldPriceItemCourse.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG
            itemView.setOnClickListener { v ->
                WebViewActivity.openUrl(v.context, "https://m.cniao5.com/course/${info.id}")
            }
            binding.executePendingBindings()
        }

    }

}