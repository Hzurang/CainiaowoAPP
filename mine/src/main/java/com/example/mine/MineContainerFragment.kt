package com.example.mine

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.common.base.BaseFragment
import com.example.mine.databinding.FragmentContainerBinding

class MineContainerFragment : BaseFragment() {
    override fun bindView(view: View, savedInstanceState: Bundle?): ViewDataBinding? {
        return FragmentContainerBinding.bind(view)
    }

    override fun getLayoutRes() = R.layout.fragment_container
}