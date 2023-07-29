package com.example.common.widget

import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class BnvMediator(val bnvMain: BottomNavigationView,
                  val vp2: ViewPager2,
                  val config:((bnv:BottomNavigationView,vp2:ViewPager2)->Unit)?=null) {
    val map = mutableMapOf<Int, Int>()

    init {
        bnvMain.menu.forEachIndexed { index, item ->
            map[item.itemId] = index
        }
    }

    fun attach() {
        config?.invoke(bnvMain,vp2)
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bnvMain.selectedItemId = bnvMain.menu[position].itemId
            }
        })
        bnvMain.setOnNavigationItemSelectedListener { item ->
            vp2.currentItem = map[item.itemId] ?: error("没有对应${item.title}的ViewPager2的元素")
            true
        }
    }
}