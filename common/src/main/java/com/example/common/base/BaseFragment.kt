package com.example.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment {
    /**
     * 无参构造函数
     */
    constructor() : super()
    /**
     * 可以填入layout布局的构造函数，使用viewBinding的方便
     * [layout] layout布局文件的id
     */
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    //UI的viewDataBinding对象
    private var mBinding:ViewDataBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes(),container,false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding=bindView(view,savedInstanceState)
        mBinding?.lifecycleOwner=viewLifecycleOwner
        initConfig()
        initData()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }
    override fun onPause() {
        super.onPause()
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    /**
     * view初始化后的必要配置
     */
    open fun initConfig() {
    }
    /**
     * view初始化后的必要数据
     */
    open fun initData() {
    }
    abstract fun bindView(view:View,savedInstanceState: Bundle?):ViewDataBinding?

    @LayoutRes
    abstract fun getLayoutRes():Int


    override fun onDestroy() {
        super.onDestroy()
        mBinding?.unbind()
    }

    /**
     * 扩展liveData的observe函数
     */
    protected fun <T : Any?> LiveData<T>.observeKt(block: (T?) -> Unit) {
        this.observe(viewLifecycleOwner, Observer { data ->
            block(data)//另外一种写法
        })
    }

}