package com.example.study.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.common.base.BaseFragment
import com.example.study.R
import com.example.study.databinding.FragmentStudyBinding
import com.example.study.ui.play.PlayActivity
import com.example.service.repo.DbHelper
import kotlinx.android.synthetic.main.fragment_study.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudyFragment : BaseFragment() {
    private val viewModel: StudyViewModel by viewModel()

    //我的学习列表适配器
    val adapter = StudyPageAdapter {
        PlayActivity.openPlay(requireContext(), it)
    }

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding? {
        return FragmentStudyBinding.bind(view).apply {
            vm = viewModel
            adapter = this@StudyFragment.adapter
        }
    }

    override fun initConfig() {
        super.initConfig()
        DbHelper.getLiveUserInfo(requireContext()).observeKt {
            viewModel.obUserInfo.set(it)
            viewModel.getStudyData()
            if (it == null) {
                lifecycleScope.launchWhenCreated {
                    adapter.submitData(PagingData.empty())//清除列表
                }
            }

        }
        viewModel.apply {
            lifecycleScope.launchWhenCreated {
                viewModel.pagingData().observeKt {
                    it?.let {
                        adapter.submitData(lifecycle, it)
                    }
                }
            }
            liveBoughtList.observeKt {

            }
        }


    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_study
    }

}