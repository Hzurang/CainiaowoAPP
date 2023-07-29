package com.example.common.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.example.common.BuildConfig
import com.example.common.R
import com.example.common.webview.JsAndroidApi.JS_CALL_APP_KEY
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebView
import com.just.agentweb.DefaultWebClient
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.toolbar_web2.*

class WebViewActivity : AppCompatActivity() {
    private lateinit var mAgentWeb: AgentWeb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                ll_webview,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator(resources.getColor(R.color.colorAccent))
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .get()
        val url = intent.getStringExtra("url")
        //        mAgentWeb.urlLoader.loadUrl("file:///android_asset/test.html")
        mAgentWeb.urlLoader.loadUrl(url)
        //添加js调用native的函数
        mAgentWeb.jsInterfaceHolder.addJavaObject(JS_CALL_APP_KEY, JsAndroidApi)
        //开启webview的调试
        AgentWebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
        iv_back_web.setOnClickListener {
            // true表示AgentWeb处理了该事件
            if (!mAgentWeb.back()) {
                finish()
            }
        }
        iv_finish_web.setOnClickListener { finish() }

        iv_more_web.setOnClickListener { showPoPup(it) }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        mAgentWeb.getWebLifeCycle().onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.getWebLifeCycle().onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy()
        super.onDestroy()
    }
    private var mPopupMenu: PopupMenu? = null

    /**
     * 显示更多菜单
     *
     * @param view 菜单依附在该View下面
     */
    private fun showPoPup(view: View) {
        mPopupMenu = PopupMenu(this, view)
        mPopupMenu?.inflate(R.menu.toolbar_web)
        mPopupMenu?.setOnMenuItemClickListener(mOnMenuItemClickListener)
        mPopupMenu?.show()
    }

    /**
     * 菜单事件
     */
    private val mOnMenuItemClickListener =
        PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.refresh -> {
                    if (mAgentWeb != null) {
                        mAgentWeb.urlLoader.reload() // 刷新
                    }
                    true
                }

                R.id.default_browser -> {
                    if (mAgentWeb != null) {
                    }
                    true
                }
                else -> false
            }
        }

    companion object {
        fun openUrl(context: Context, url: String) {
            context.startActivity(Intent(context, WebViewActivity::class.java).also {
                it.putExtra("url", url)
            })
        }
    }
}