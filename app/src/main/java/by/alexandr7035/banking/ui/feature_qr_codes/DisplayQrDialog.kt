package by.alexandr7035.banking.ui.feature_qr_codes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.features.qr_codes.model.QrPurpose
import by.alexandr7035.banking.ui.components.DotsProgressIndicator
import by.alexandr7035.banking.ui.components.error.ErrorFullScreen
import by.alexandr7035.banking.ui.components.qr.QrCodeCard
import by.alexandr7035.banking.ui.core.effects.EnterScreenEffect
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import org.koin.androidx.compose.koinViewModel

private val QR_SIZE_FIXED = 240.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowQrDialog(
    viewModel: DisplayQrViewModel = koinViewModel(),
    qrPurpose: QrPurpose,
    onDismiss: () -> Unit = {},
    qrLabel: UiText? = null
) {
    val dialogState = rememberModalBottomSheetState()

    val title = when (qrPurpose) {
        QrPurpose.PROFILE_CONNECTION -> UiText.StringResource(R.string.my_qr)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = dialogState,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = title.asString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF262626),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.padding(56.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DotsProgressIndicator()
                    }
                }

                state.qrString != null -> {
                    QrCodeCard(
                        qr = state.qrString,
                        label = qrLabel,
                        modifier = Modifier.width(QR_SIZE_FIXED)
                    )
                }

                state.error != null -> {
                    ErrorFullScreen(
                        error = state.error,
                        onRetry = {
                            viewModel.emitIntent(DisplayQrIntent.GenerateQr(qrPurpose))
                        },
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                    )

                    LaunchedEffect(Unit) {
                        dialogState.expand()
                    }
                }
            }
        }
    }

    EnterScreenEffect {
        viewModel.emitIntent(DisplayQrIntent.GenerateQr(qrPurpose))
    }
}