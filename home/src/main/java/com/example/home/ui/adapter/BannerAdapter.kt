package com.example.home.ui.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.common.webview.WebViewActivity
import com.example.home.net.BannerList
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

class BannerAdapter(val bannerList: BannerList) :
    BannerImageAdapter<BannerList.BannerListItem>(bannerList) {
    override fun onBindView(
        holder: BannerImageHolder?,
        data: BannerList.BannerListItem?,
        position: Int,
        size: Int
    ) {
        holder ?: return

        Glide.with(holder.itemView)
            .load(data?.img_url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            WebViewActivity.openUrl(it.context, data?.redirect_url ?: "https://m.cniao5.com/")
        }

    }
}