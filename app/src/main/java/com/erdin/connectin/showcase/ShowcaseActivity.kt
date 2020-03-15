package com.erdin.connectin.showcase

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.erdin.connectin.R
import com.erdin.connectin.databinding.ActivityShowcaseBinding

class ShowcaseActivity: AppCompatActivity(), WebViewListener {

    private lateinit var binding: ActivityShowcaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_showcase
        )

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val getUrl = intent.getStringExtra("showcase_eng")
        Log.d("URL ", getUrl)
        val webVw = binding.webView
        webVw.loadUrl(getUrl)
        webVw.settings.javaScriptEnabled = true
        webVw.settings.javaScriptCanOpenWindowsAutomatically = true
        webVw.settings.domStorageEnabled = true
        webVw.webChromeClient =
            WebListChromeClient(
                this
            )
        webVw.webViewClient =
            WebListWebViewClient(
                this
            )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPageStarted() {
        binding.progressBar.visibility = View.VISIBLE
        Toast.makeText(this, "Page Started", Toast.LENGTH_SHORT).show()
    }

    override fun onPageFinished() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onShouldOverrideUrl(redirectUrl: String) {
        Toast.makeText(this, "redirect url: ${redirectUrl}", Toast.LENGTH_SHORT).show()

    }

    override fun onProgressChanged(progress: Int) {
        binding.progressBar.progress = progress

    }


    class WebListChromeClient(private val listener: WebViewListener): WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            listener.onProgressChanged(newProgress)
            super.onProgressChanged(view, newProgress)
        }
    }

    class WebListWebViewClient(private val listener: WebViewListener): WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            listener.onPageStarted()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            listener.onPageFinished()
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            listener.onShouldOverrideUrl(url.orEmpty())
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                listener.onShouldOverrideUrl(request?.url?.toString().orEmpty())
            }
            return super.shouldOverrideUrlLoading(view, request)
        }
    }
}