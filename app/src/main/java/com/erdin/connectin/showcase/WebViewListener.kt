package com.erdin.connectin.showcase

interface WebViewListener {
    fun onPageStarted()
    fun onPageFinished()
    fun onShouldOverrideUrl(redirectUrl: String)
    fun onProgressChanged(progress: Int)
}