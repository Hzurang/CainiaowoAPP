package com.example.mine.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.base.BaseFragment
import com.example.common.webview.WebViewActivity
import com.example.mine.R
import com.example.mine.databinding.FragmentMineBinding
import com.example.mine.net.UserInfoRsp
import com.example.common.network.config.SP_KEY_USER_TOKEN
import com.example.common.utils.CniaoSpUtils
import com.example.service.repo.DbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class MineFragment : BaseFragment() {

    private val viewModel: MineViewModel by viewModel()

    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding? {
        return FragmentMineBinding.bind(view).apply {
            vm = viewModel
            btnLogoutMine.setOnClickListener {
                CniaoSpUtils.remove(SP_KEY_USER_TOKEN)
                DbHelper.deleteUserInfo(requireContext())
                ARouter.getInstance().build("/login/login").navigation()
            }
            tvUserNameMine.setOnClickListener {
                ivUserIconMine.callOnClick()
            }
            ivUserIconMine.setOnClickListener {
                val info = viewModel.userInfo.value
                if (info == null) {
                    ARouter.getInstance().build("/login/login").navigation()
                } else {
                    val action =
                        MineFragmentDirections.actionMineFragmentToUserInfoFragment(info)
                    findNavController().navigate(action)
                }
            }
            tvOrdersMine.setOnClickListener {
                WebViewActivity.openUrl(requireContext(), "https://m.cniao5.com/user/order")
            }
            tvCouponMine.setOnClickListener {
                WebViewActivity.openUrl(requireContext(), "https://m.cniao5.com/user/coupon")
            }
            isvStudyCardMine.setOnClickListener {
                WebViewActivity.openUrl(requireContext(), "https://m.cniao5.com/sharecard")
            }
            isvShareSaleMine.setOnClickListener {
                WebViewActivity.openUrl(requireContext(), "https://m.cniao5.com/distribution")
            }
            isvGroupShoppingMine.setOnClickListener {
                WebViewActivity.openUrl(requireContext(), "https://m.cniao5.com/user/pintuan")
            }
            isvLikedCourseMine.setOnClickListener {
                WebViewActivity.openUrl(requireContext(), "https://m.cniao5.com/user/favorites")
            }
            isvFeedbackMine.setOnClickListener {
                WebViewActivity.openUrl(requireContext(), "https://cniao555.mikecrm.com/ktbB0ht")
            }
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine
    }

    override fun initConfig() {
        super.initConfig()
        DbHelper.getLiveUserInfo(requireContext()).observeKt {
            //用户信息变化时应该立即去请求用户信息
            viewModel.getUserInfo(it?.token)
        }
    }
}