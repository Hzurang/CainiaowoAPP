package com.example.common.ktx

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

//region 扩展函数
/**
 * Activity中使用setContentView的简化写法
 * [layout] 布局文件id
 * @return 返回一个binding对象的实例
 */
fun <T : ViewDataBinding> Activity.bindView(@LayoutRes layout: Int): T {
    return DataBindingUtil.setContentView(this, layout)
}

/**
 * Activity中使用setContentView的简化写法
 * [view] 布局文件根root
 * @return 返回一个binding对象的实例
 */
fun <T : ViewDataBinding> Activity.bindView(view: View): T? {
    return DataBindingUtil.bind<T>(view)

}

/**
 * 界面Activity的沉浸式状态栏，使得可以在状态栏里面显示部分需要的图片
 * 注意点，需要在setContentView之前调用该函数才生效
 */
fun Activity.immediateStatusBar() {
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

/**
 * 软键盘的隐藏
 * [view] 事件控件View
 */
fun Activity.dismissKeyBoard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}
// endregion


//region 扩展属性

/**
 *扩展LifecycleOwner属性，便于fragment之间使用LifecycleOwner一致性
 */
val ComponentActivity.viewLifeCycleOwner: LifecycleOwner
    get() = this

/**
 *Activity扩展字段便于，便于在fragment中使用livedata时参数一致性
 */
val Activity.context: Context
    get() = this
//endregion
