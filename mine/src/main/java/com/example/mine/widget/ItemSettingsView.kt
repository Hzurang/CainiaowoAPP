package com.example.mine.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.annotation.Keep
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.example.mine.BR
import com.example.mine.R
import com.example.mine.databinding.VItemSettingsBinding

class ItemSettingsView @JvmOverloads constructor(//JvmOverloads会重载每个参数
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var itemBean = ItemSettingsBean()
    var info = ObservableField<ItemSettingsBean>(itemBean)

    init {
        //1、绑定view
        VItemSettingsBinding.inflate(LayoutInflater.from(context), this, true).apply {
            info = this@ItemSettingsView.info
        }
        setBackgroundColor(Color.WHITE)
        // region  2、读取配置属性
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemSettingsView).apply {
            //icon 设置
            itemBean.iconRes =
                getResourceId(R.styleable.ItemSettingsView_icon, R.drawable.ic_gift_card)
            val iconRGB = getColor(R.styleable.ItemSettingsView_iconColor, 0)
            itemBean.iconColor = iconRGB
            //title设置
            itemBean.title = getString(R.styleable.ItemSettingsView_title) ?: ""
            val titleRGB = getColor(
                R.styleable.ItemSettingsView_titleColor,
                resources.getColor(R.color.colorPrimaryText)
            )
            itemBean.titleColor = titleRGB
            //desc设置
            itemBean.desc = getString(R.styleable.ItemSettingsView_desc) ?: ""
            val descRGB = getColor(R.styleable.ItemSettingsView_descColor, 0)
            itemBean.descColor = descRGB
            //arrow设置
            itemBean.arrowRes =
                getResourceId(R.styleable.ItemSettingsView_arrow, R.drawable.ic_right)
            val arrowRGB = getColor(
                R.styleable.ItemSettingsView_arrowColor,
                resources.getColor(R.color.colorSecondaryText)
            )
            itemBean.arrowColor = arrowRGB
        }
        // 回收 recycle
        attributes.recycle()
        // endregion
    }
    //region 设置资源

    /**
     * 设置整个item的对象info
     */
    fun setInfo(info: ItemSettingsBean) {
        itemBean = info
        this.info.set(info)
    }

    /**
     * 设置title
     */
    fun setTitle(title: String) {
        itemBean.title = title
        this.info.notifyChange()
    }

    /**
     * 设置内容描述
     */
    fun setDesc(desc: String) {
        itemBean.desc = desc
        this.info.notifyChange()
    }

    /**
     * 设置icon图标
     */
    fun setIcon(iconRes: Any) {
        itemBean.iconRes = iconRes
        this.info.notifyChange()
    }

    /**
     * 设置icon图标
     */
    fun setArrow(arrowRes: Any) {
        itemBean.arrowRes = arrowRes
        this.info.notifyChange()
    }

    //region 点击事件
    fun onClickIcon(listener: OnClickListener) {
        itemBean.iconListener = listener
        this.info.notifyChange()
    }

    fun onClickTitle(listener: OnClickListener) {
        itemBean.titleListener = listener
        this.info.notifyChange()
    }

    fun onClickDesc(listener: OnClickListener) {
        itemBean.desListener = listener
        this.info.notifyChange()
    }

    fun onClickArrow(listener: OnClickListener) {
        itemBean.arrowListener = listener
        this.info.notifyChange()
    }
    //endregion

    //region 设置颜色
    /**
     * 设置标题title颜色
     */
    fun setIconColor(colorRes: Int) {
        itemBean.iconColor = colorRes
        this.info.notifyChange()
    }

    /**
     * 设置标题title颜色
     */
    fun setTitleColor(colorRes: Int) {
        itemBean.titleColor = colorRes
        this.info.notifyChange()
    }

    /**
     * 设置标题title颜色
     */
    fun setDescColor(colorRes: Int) {
        itemBean.descColor = colorRes
        this.info.notifyChange()
    }

    /**
     * 设置标题title颜色
     */
    fun setArrowColor(colorRes: Int) {
        itemBean.arrowColor = colorRes
        this.info.notifyChange()
    }

    //endregion
    //当item有点击时间时，子view点击事件使失效
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return hasOnClickListeners()
    }

    //3、定义绑定的实体类
    @Keep
    data class ItemSettingsBean(
        var iconRes: Any = R.drawable.ic_gift_card,
        var title: String = "",
        var desc: String = "",
        var titleColor: Int = R.color.colorPrimaryText,
        var descColor: Int = R.color.colorSecondaryText,
        var iconColor: Int = 0,
        var arrowColor: Int = 0,
        var arrowRes: Any = R.drawable.ic_right
    ) {
        //itemview的子view点击事件
        var iconListener: OnClickListener? = null
        var titleListener: OnClickListener? = null
        var desListener: OnClickListener? = null
        var arrowListener: OnClickListener? = null
    }

}