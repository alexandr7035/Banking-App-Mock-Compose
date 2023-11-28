package by.alexandr7035.banking.ui.feature_qr_codes.scan_qr

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import by.alexandr7035.banking.ui.core.extensions.vibrate
import by.alexandr7035.banking.ui.core.resources.UiText
import com.journeyapps.barcodescanner.DecoratedBarcodeView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanQrDialog(
    onDismiss: () -> Unit,
    onScanResultContent: @Composable (
        qrCode: String,
        omRetryScan: () -> Unit
    ) -> Unit,
    qrExplanation: UiText
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val dialogState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = dialogState,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        val scannedQr = remember {
            mutableStateOf<String?>(null)
        }

        when (val qr = scannedQr.value) {
            null -> {
                Box(Modifier.fillMaxSize()) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            // Creates view
                            DecoratedBarcodeView(context).apply {
                                statusView.isVisible = false
                                viewFinder.setLaserVisibility(false)
                                viewFinder.setMaskColor(
                                    Color(0xCC000000).toArgb()
                                )

//                        resume()
                                val lifecycleObserver = object : DefaultLifecycleObserver {
                                    override fun onResume(owner: LifecycleOwner) {
                                        super.onResume(owner)
                                        resume()
                                    }

                                    override fun onPause(owner: LifecycleOwner) {
                                        super.onPause(owner)
                                        pause()
                                    }
                                }
                                lifecycle.addObserver(lifecycleObserver)

                                decodeSingle { scanResult ->
                                    this.pause()
                                    context.vibrate()
                                    scannedQr.value = scanResult.text
                                }
                            }
                        },
                        update = { view -> },
                    )

                    Text(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(
                                vertical = 16.dp,
                                horizontal = 24.dp,
                            ),
                        text = qrExplanation.asString(),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                }
            }

            else -> {
                onScanResultContent(qr) {
                    scannedQr.value = null
                }
            }
        }
    }
}
