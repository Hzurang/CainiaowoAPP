package com.example.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.common.webview.WebViewActivity
import com.example.home.databinding.ItemJobClassBinding
import com.example.home.net.JobClassList

class JobClassAdapter(val jobClassList: JobClassList) : RecyclerView.Adapter<JobClassAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.create(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(jobClassList[position])
    }

    override fun getItemCount(): Int {
        return jobClassList.size
    }

    class VH(val binding: ItemJobClassBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): VH {
                val itemBinding =
                    ItemJobClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return VH(itemBinding)
            }
        }

        fun bind(info: JobClassList.JobClassListItem) {
            binding.url = info.course?.img_url
            itemView.setOnClickListener {
                WebViewActivity.openUrl(it.context, info.course?.h5site ?: "https://m.cniao5.com")
            }
            binding.executePendingBindings()
        }
    }
}