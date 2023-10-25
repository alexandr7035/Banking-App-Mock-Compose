package by.alexandr7035.banking.ui.feature_webview

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.core.resources.UiText

@Composable
fun WebViewScreen(
    title: String,
    url: String,
    onBack: () -> Unit = {}
) {

    Scaffold(topBar = {
        SecondaryToolBar(
            onBack = onBack,
            title = UiText.DynamicString(title)
        )
    }) { pv ->

        val showProgress = rememberSaveable() {
            mutableStateOf(true)
        }

        val onToggleProgress: (Boolean) -> Unit = {
            showProgress.value = it
        }

        // Adding a WebView inside AndroidView
        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = object: WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            onToggleProgress(true)
                        }

                        override fun onPageCommitVisible(view: WebView?, url: String?) {
                            onToggleProgress(false)
                        }
                    }

                    loadUrl(url)
                }
            },
            update = {
                it.loadUrl(url)
            },
            modifier = Modifier.padding(pv)
        )

        if (showProgress.value) {
            FullscreenProgressBar(
                backgroundColor = Color.Transparent
            )
        }
    }
}

@Composable
@Preview
fun WebViewScreen_Preview() {
    ScreenPreview {
        WebViewScreen(
            title = "Terms and Conditions",
            url = ""
        )
    }
}