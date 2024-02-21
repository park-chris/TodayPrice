package com.crystal.todayprice.ui

import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityLoginBinding
import com.crystal.todayprice.databinding.ActivityWebViewBinding

class WebViewActivity: BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.VERTICAL) {

    private lateinit var binding: ActivityWebViewBinding
    private var url = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        url = intent.getStringExtra(WEB_VIEW_URL) ?: ""

        Log.e("TestLog", "url: $url")

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)

    }

    companion object {
        const val WEB_VIEW_URL = "webViewUrl"
    }

}
