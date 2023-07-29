package com.example.home.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.example.common.base.BaseFragment
import com.example.home.R
import com.example.home.databinding.FragmentHomeBinding
import com.example.home.net.*
import com.example.home.ui.adapter.BannerAdapter
import com.example.home.ui.adapter.HomeAdapter
import com.example.common.network.model.DataResult
import com.example.service.network.*
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.onFailure


class HomeFragment : BaseFragment() {
    private val adapter: HomeAdapter = HomeAdapter()

    private val model: HomeViewModel by viewModel()

    private val bannerList = BannerList()

    private val bannerAdapter by lazy { BannerAdapter(bannerList) }

    private val homeList = arrayListOf<HomeData>()

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding? {
        return FragmentHomeBinding.bind(view).apply {
            adapter = this@HomeFragment.adapter
        }
    }

    override fun getLayoutRes() = R.layout.fragment_home

    override fun initConfig() {
        super.initConfig()
        banner_home.addBannerLifecycleObserver(viewLifecycleOwner) //添加生命周期观察者
            .setAdapter(bannerAdapter).indicator = CircleIndicator(requireContext())
    }

    override fun initData() {
        super.initData()
        model.getBanner()
        model.getHomeList()

        model.liveBanner.observeKt { datas ->
            datas ?: return@observeKt
            bannerList.clear()
            bannerList.addAll(datas)
            bannerAdapter.notifyDataSetChanged()

        }
        val scope = CoroutineScope(SupervisorJob())
        model.liveHomeList.observeKt { homedatas ->
            homedatas ?: return@observeKt

            lifecycleScope.launchWhenCreated {
                homedatas.map { item ->
                    Triple(
                        item.id,
                        item.title,
                        scope.async { model.getModuleDatas(item.id) }.await()
                    )
                }?.asFlow()?.collect {
                    parseData(it.first, it.second, it.third)
                }
                adapter.updateList(homeList)
            }

        }
    }

    private fun parseData(typeId: Int, title: String?, data: DataResult<BaseResponse>) {
        data.onSuccess {
            when (typeId) {
                TYPE_JOB_CLASS -> {
                    onBizzOK<JobClassList> { code, data, message ->
                        homeList.add(HomeData(typeId, "就业班", data))
                    }
                }
                TYPE_NEW_CLASS -> {
                    onBizzOK<NewClassList> { code, data, message ->
                        homeList.add(HomeData(typeId, "新上好课", data))
                    }
                }
                TYPE_LIMIT_FREE -> {
                    onBizzOK<LimitFreeList> { code, data, message ->
                        homeList.add(HomeData(typeId, "限时免费", data))
                    }
                }
                TYPE_ANDROID -> {
                    onBizzOK<AndroidSelection> { code, data, message ->
                        homeList.add(HomeData(typeId, "Android精选", data))
                    }
                }
                TYPE_AI -> {
                    onBizzOK<AISelection> { code, data, message ->
                        homeList.add(HomeData(typeId, "AI精选", data))
                    }
                }
                TYPE_BD -> {
                    onBizzOK<BDList> { code, data, message ->
                        homeList.add(HomeData(typeId, "大数据精选", data))
                    }
                }
                TYPE_10W -> {
                    onBizzOK<Suggest10w> { code, data, message ->
                        homeList.add(HomeData(typeId, "10w学员推荐", data))
                    }
                }
                TYPE_TEACHER -> {
                    onBizzOK<PopTeacherList> { code, data, message ->
                        homeList.add(HomeData(typeId, "人气讲师", data))
                    }
                }
                else -> {
                }
            }

            onBizzError { code, message ->
                LogUtils.e("模块数据 bizError $code $message")

            }
        }.onFailure {
            LogUtils.e("模块数据 Failure")
        }


    }

    companion object {

        internal const val TYPE_JOB_CLASS = 1
        internal const val TYPE_NEW_CLASS = 2
        internal const val TYPE_LIMIT_FREE = 3
        internal const val TYPE_ANDROID = 4
        internal const val TYPE_AI = 5
        internal const val TYPE_BD = 6
        internal const val TYPE_10W = 7
        internal const val TYPE_TEACHER = 8

    }

    data class HomeData(
        val type: Int,
        val title: String?,
        val obj: Any?
    )

}