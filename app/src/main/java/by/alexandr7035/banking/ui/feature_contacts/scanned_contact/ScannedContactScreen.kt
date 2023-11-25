package by.alexandr7035.banking.ui.feature_contacts.scanned_contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.app_host.host_utils.LocalScopedSnackbarState
import by.alexandr7035.banking.ui.components.DotsProgressIndicator
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.TextBtn
import by.alexandr7035.banking.ui.components.debug.debugPlaceholder
import by.alexandr7035.banking.ui.components.error.ErrorFullScreen
import by.alexandr7035.banking.ui.components.snackbar.SnackBarMode
import by.alexandr7035.banking.ui.core.effects.EnterScreenEffect
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import de.palm.composestateevents.NavigationEventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScannedContactScreen(
    viewModel: ScannedContactViewModel = koinViewModel(),
    onRetryScan: () -> Unit,
    qrCode: String,
    onBack: () -> Unit
) {
    val snackBarState = LocalScopedSnackbarState.current
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle().value

    ScannedContactScreen_Ui(
        state = state,
        onAddContact = {
            viewModel.emitIntent(ScannedContactIntent.AddContact)
        },
        onRetryLoadContact = {
            viewModel.emitIntent(ScannedContactIntent.LoadContact(qrCode = qrCode))
        },
        onRetryScan = onRetryScan
    )

    EnterScreenEffect {
        viewModel.emitIntent(ScannedContactIntent.LoadContact(qrCode = qrCode))
    }

    NavigationEventEffect(
        event = state.addContactResEvent,
        onConsumed = viewModel::consumeContactAddedEvent
    ) {
        when (it) {
            is OperationResult.Failure -> {
                snackBarState.show(it.error.errorType.asUiTextError().asString(context), SnackBarMode.Negative)
            }
            is OperationResult.Success -> {
                snackBarState.show(context, R.string.contact_added, SnackBarMode.Positive)
            }
        }
        onBack()
    }
}

@Composable
fun ScannedContactScreen_Ui(
    state: ScannedContactScreenState,
    onRetryScan: () -> Unit = {},
    onRetryLoadContact: () -> Unit = {},
    onAddContact: () -> Unit = {},
) {
    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.add_a_contact), style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF262626),
                    textAlign = TextAlign.Center,
                ), modifier = Modifier.padding(
                    vertical = 16.dp,
                )
            )

            when {
                state.isContactLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        DotsProgressIndicator()
                    }
                }

                state.contact != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.weight(1f))

                        val imageReq = ImageRequest.Builder(LocalContext.current).data(state.contact.profilePictureUrl)
                            .decoderFactory(SvgDecoder.Factory()).crossfade(true).build()

                        AsyncImage(
                            model = imageReq,
                            placeholder = debugPlaceholder(R.drawable.ic_profile_filled),
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFBCC3FF)),
                            contentDescription = null,
                        )

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = state.contact.name, style = TextStyle(
                                fontSize = 22.sp,
                                lineHeight = 20.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF333333),
                            ), overflow = TextOverflow.Ellipsis, maxLines = 1
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = state.contact.cardNumber, style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF100D40),
                            ), overflow = TextOverflow.Ellipsis, maxLines = 1
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.add_contact_explanation), style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF999999),
                                textAlign = TextAlign.Center,
                            )
                        )

                        Spacer(Modifier.weight(1f))

                        PrimaryButton(
                            onClick = onAddContact, modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.add_a_contact)
                        )

                        Spacer(Modifier.height(8.dp))

                        TextBtn(
                            onClick = onRetryScan, modifier = Modifier.wrapContentSize(), text = stringResource(R.string.scan_again)
                        )

                        Spacer(Modifier.weight(1f))
                    }
                }

                state.error != null -> {
                    ErrorFullScreen(
                        error = state.error,
                        onRetry = onRetryLoadContact
                    )
                }
            }
        }
        if (state.isLoading) {
            FullscreenProgressBar()
        }
    }
}

@Preview
@Composable
fun ScannedContactScreen_Preview() {
    ScreenPreview {
        ScannedContactScreen_Ui(
            state = ScannedContactScreenState(
                isContactLoading = false, contact = ContactUi.mock(), isLoading = true
            )
        )
    }
}

@Preview
@Composable
fun ScannedContactScreen_Loading_Preview() {
    ScreenPreview {
        ScannedContactScreen_Ui(
            state = ScannedContactScreenState(
                isContactLoading = true
            )
        )
    }
}