package com.example.course.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.course.databinding.ItemCourseBinding
import com.example.course.databinding.ItemFooterCourseBinding

class CourseLoadAdapter(private val adapter: CoursePageAdapter) :
    LoadStateAdapter<CourseLoadAdapter.FooterVH>() {


    override fun onBindViewHolder(holder: FooterVH, loadState: LoadState) {
        holder.bind(loadState, adapter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterVH {
        val binding = ItemFooterCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FooterVH(binding)
    }

    class FooterVH(val binding: ItemFooterCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, adapter: CoursePageAdapter) {
            when(loadState){
                is LoadState.Error -> {
                    binding.pbFooterCourse.visibility = View.GONE
                    binding.tvFooterCourse.visibility = View.VISIBLE
                    binding.tvFooterCourse.text = "Load Failed, Tap Retry"
                    binding.tvFooterCourse.setOnClickListener {
                        adapter.retry()
                    }
                }
                is LoadState.Loading -> {
                    binding.pbFooterCourse.visibility = View.VISIBLE
                    binding.tvFooterCourse.visibility = View.VISIBLE
                    binding.tvFooterCourse.text = "Loading~~"
                }
                is LoadState.NotLoading -> {
                    binding.pbFooterCourse.visibility = View.GONE
                    binding.tvFooterCourse.visibility = View.GONE
                }
            }
        }
    }
}