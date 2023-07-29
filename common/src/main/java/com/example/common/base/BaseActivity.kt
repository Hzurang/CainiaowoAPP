package com.example.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.example.common.ktx.bindView
import com.example.common.ktx.viewLifeCycleOwner

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity {
    /**
     * 无参构造函数
     */
    constructor() : super()

    /**
     * 可以填入layout布局的构造函数，使用viewBinding的方便
     * [layout] layout布局文件的id
     */
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    //第二种方式初始化databinding
    protected lateinit var mBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = bindView<T>(getLayoutRes()).also {
            it.lifecycleOwner = viewLifeCycleOwner
        }
        initView()
        initConfig()
        initData()
    }


    /**
     * 获取布局文件id
     * @return Int
     */
    abstract fun getLayoutRes(): Int

    /**
     * 必要的view初始化
     */
    open fun initView() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initView")
    }

    /**
     * view初始化后的必要配置
     */
    open fun initConfig() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * view初始化后的必要数据
     */
    open fun initData() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initData")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }

    /**
     * 扩展liveData的observe函数
     */
    protected inline fun <T : Any?> LiveData<T>.observeKt(crossinline block: (T?) -> Unit) {
        this.observe(viewLifeCycleOwner, Observer { data ->
            //block.invoke(data)
            block(data)
        })
    }

}