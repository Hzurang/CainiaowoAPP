package com.example.study.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.study.net.StudiedRsp

class StudyPageAdapter(val block: (StudiedRsp.Data) -> Unit) :
    PagingDataAdapter<StudiedRsp.Data, StudiedVH>(differCallback) {

    override fun onBindViewHolder(holder: StudiedVH, position: Int) {
        getItem(position)?.let {data->
            holder.bind(data)
            holder.itemView.setOnClickListener {
                block(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudiedVH.createVH(parent)

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<StudiedRsp.Data>() {
            override fun areItemsTheSame(
                oldItem: StudiedRsp.Data,
                newItem: StudiedRsp.Data
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: StudiedRsp.Data,
                newItem: StudiedRsp.Data
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}