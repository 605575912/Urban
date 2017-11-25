package com.lzx.h.module.common.web

import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import com.lzx.h.module.common.R
import com.lzx.h.module.common.databinding.ActivityWebviewBinding
import com.squareup.lib.activity.BaseActivity

/**
 * Created by liangzhenxiong on 2017/11/25.
 */
class WebActivity : BaseActivity() {


    companion object {
        fun StartActivity(activity: Activity, url: String) {
            var intent = Intent(activity, (WebActivity::class.java))
            intent.putExtra("url", url)
            activity.startActivity(intent)
        }
    }

    var viewBinding: ActivityWebviewBinding? = null
    override fun setFromLayoutID(): Int {
        return R.layout.activity_webview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = viewDataBinding as ActivityWebviewBinding
        initWebView(viewBinding?.webviewWvWebview!!)
        var url = intent.getStringExtra("url")
        viewBinding?.webviewWvWebview?.loadUrl(url)
    }

    var mProgressFinishOnce: Boolean = false
    var currenturl: String? = null
    fun initWebView(webview: WebView) {

        val webSettings = webview.settings
        //        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 不需要缓存
        webSettings?.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings?.javaScriptEnabled = true// 允许js交互
        webSettings?.javaScriptCanOpenWindowsAutomatically = true

        webSettings?.useWideViewPort = true
        //webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//有些手机可能存在兼容性，页面排版错乱

        webSettings?.displayZoomControls = false
        webSettings?.builtInZoomControls = true // 设置显示缩放按钮
        webSettings?.setSupportZoom(true) // 支持缩放

        webSettings?.loadWithOverviewMode = true

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                var url = url
                if (!this@WebActivity.shouldOverrideUrlLoading(view, url)) {
                    if (url.isNullOrEmpty()) {
                        return false
                    }
                    url = url?.trim { it <= ' ' }
                    if (isSchemeUrl(url)) {
                        val uri = Uri.parse(url)
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        } catch (e: Exception) {

                        }

                    } else {
                        //在当前WebView跳转
                        //FIXME:某些链接在goBack时会一直跳转回到最后的连接,暂时用这个判断解决
                        val item = webview.copyBackForwardList()?.getCurrentItem()
                        if (item == null || url != item.originalUrl) {
                            if (url == currenturl && currenturl != null) {
                                return false
                            }
                            view?.loadUrl(url)
                            currenturl = url
                        }
                    }
                }
                return true
            }

            override fun onReceivedError(view: WebView, errorCode: Int,
                                         description: String, failingUrl: String) {
                //加载错误页面
                //                view.loadUrl(UrlPathHelper.getErrorUrl());
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                viewBinding?.webviewPbProgress?.visibility = View.VISIBLE
                viewBinding?.webviewPbProgress?.progress = 10
                mProgressFinishOnce = false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!mProgressFinishOnce) {
                    viewBinding?.webviewPbProgress?.visibility = View.GONE
                    mProgressFinishOnce = true
                }
            }
        }

        var mChromeCline = WebChromeClientX() as WebChromeClient
        webview.webChromeClient = mChromeCline
    }

    inner class WebChromeClientX : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (viewBinding?.webviewPbProgress?.getVisibility() == View.VISIBLE) {
                if (newProgress < 11) {
                    viewBinding?.webviewPbProgress?.setProgress(10)
                } else {
                    setProgressChangeAnimation(viewBinding?.webviewPbProgress, newProgress)
                }
            }
            super.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            viewBinding?.title?.setText(title)
//            viewBinding.
//            if (mTitle == null) {
//                //                setTitle(title);
//            }
        }

        override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            result.cancel()
            return true
        }

        /**
         * @param view
         * @param url
         * @param message      调用的方法名
         * @param defaultValue 参数（如果是只有一个参数，就直接传字符串""，如果两个或以上就用json：｛"title":"","url":""｝）
         * @param result
         * @return
         */
        override fun onJsPrompt(view: WebView, url: String, message: String, defaultValue: String, result: JsPromptResult): Boolean {
            parseMessage(message, defaultValue)
            result.cancel()
            return true
        }


        private fun setProgressChangeAnimation(progressBar: ProgressBar?, toProgress: Int) {
            if (progressBar == null) {
                return

            }
            if (progressBar?.progress!! > toProgress) {
                return
            }

            val progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", progressBar?.progress!!, toProgress)
            progressAnimator.interpolator = AccelerateDecelerateInterpolator()
            progressAnimator.duration = 500
            if (toProgress == 100) {
                progressAnimator.addListener(object : AnimatorListenerAdapter() {
                })
            }
            progressAnimator.start()
        }

    }

    /**
     * 解析网页传过来的数据
     *
     * @param message      调用的方法名
     * @param defaultValue 如果一个参数的话是string类型，两个参数及以上就用json格式
     */
    protected fun parseMessage(message: String, defaultValue: String) {

    }

    fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }

    private fun isSchemeUrl(url: String?): Boolean {
        return !(url!!.startsWith("http://") || url!!.startsWith("https://"))
    }
}