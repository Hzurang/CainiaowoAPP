package com.example.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home.databinding.ItemHomeBinding
import com.example.home.net.*
import com.example.home.ui.HomeFragment

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeAdapterVH>() {
    private val homeList = ArrayList<HomeFragment.HomeData>()

    class HomeAdapterVH(val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): HomeAdapterVH {
                val mBinding =
                    ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeAdapterVH(mBinding)
            }
        }

        fun bind(homeData: HomeFragment.HomeData) {
            binding.title = homeData.title
            setAdapter(homeData)
            binding.executePendingBindings()
        }

        private fun setAdapter(item: HomeFragment.HomeData) {

            binding.rvItemHome.adapter = when (item.type) {
                HomeFragment.TYPE_JOB_CLASS -> {
                    binding.rvItemHome.layoutManager = GridLayoutManager(itemView.context, 2)
                    JobClassAdapter(item.obj as JobClassList)
                }
                HomeFragment.TYPE_NEW_CLASS -> {
                    binding.rvItemHome.layoutManager =
                        LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                    HomeCourseAdapter(item.obj as NewClassList)
                }
                HomeFragment.TYPE_LIMIT_FREE -> {
                    binding.rvItemHome.layoutManager =
                        LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                    HomeCourseAdapter(item.obj as LimitFreeList)
                }
                HomeFragment.TYPE_ANDROID -> {
                    binding.rvItemHome.layoutManager =
                        LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                    HomeCourseAdapter(item.obj as AndroidSelection)
                }
                HomeFragment.TYPE_AI -> {
                    binding.rvItemHome.layoutManager =
                        LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                    HomeCourseAdapter(item.obj as AISelection)
                }
                HomeFragment.TYPE_BD -> {
                    binding.rvItemHome.layoutManager =
                        LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                    HomeCourseAdapter(item.obj as BDList)
                }
                HomeFragment.TYPE_10W -> {
                    binding.rvItemHome.layoutManager =
                        LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                    HomeCourseAdapter(item.obj as Suggest10w)
                }
                HomeFragment.TYPE_TEACHER -> {
                    binding.rvItemHome.layoutManager =
                        LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
                    TeacherAdapter(item.obj as PopTeacherList)
                }
                else -> error("error type ${item.type}")
            }
        }


    }

    fun updateList(homeList: ArrayList<HomeFragment.HomeData>) {
        this.homeList.clear()
        this.homeList.addAll(homeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterVH {

        return HomeAdapterVH.create(parent)
    }

    override fun onBindViewHolder(holder: HomeAdapterVH, position: Int) {
        holder.bind(homeList[position])
    }

    override fun getItemCount(): Int {
        return homeList.size
    }
}