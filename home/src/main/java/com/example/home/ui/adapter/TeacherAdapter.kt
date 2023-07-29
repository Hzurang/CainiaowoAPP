package com.example.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.home.databinding.ItemJobClassBinding
import com.example.home.databinding.ItemTeacherBinding
import com.example.home.net.JobClassList
import com.example.home.net.PopTeacherList

class TeacherAdapter (val popTeacherList: PopTeacherList) : RecyclerView.Adapter<TeacherAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.create(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(popTeacherList[position])
    }

    override fun getItemCount(): Int {
        return popTeacherList.size
    }

    class VH(val binding: ItemTeacherBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): VH {
                val itemBinding =
                    ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return VH(itemBinding)
            }
        }
        fun bind(info: PopTeacherList.PopTeacherListItem) {
            binding.info = info
            itemView.setOnClickListener {
            }
            binding.executePendingBindings()
        }
    }
}