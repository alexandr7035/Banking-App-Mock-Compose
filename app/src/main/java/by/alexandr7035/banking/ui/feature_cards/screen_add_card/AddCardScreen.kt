package by.alexandr7035.banking.ui.feature_cards.screen_add_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.PrimaryTextField
import by.alexandr7035.banking.ui.components.ReadonlyTextField
import by.alexandr7035.banking.ui.components.dialogs.DatePickerDialog
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.extensions.showToast
import by.alexandr7035.banking.ui.feature_cards.components.CardNumberField
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddCardScreen(
    viewModel: AddCardViewModel = koinViewModel(), onBack: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    AddCardScreen_Ui(onBack = onBack, state = state, onIntent = { viewModel.emitIntent(it) })

    LaunchedEffect(Unit) {
        viewModel.emitIntent(AddCardIntent.EnterScreen)
    }

    val ctx = LocalContext.current

    EventEffect(
        event = state.cardSavedEvent,
        onConsumed = viewModel::consumeSaveCardEvent,
        action = { isCardAdded ->
            if (isCardAdded) {
                ctx.showToast("Card has been added!")
                onBack.invoke()
            } else {
                ctx.showToast("Failed to save card")
            }
        }
    )
}

@Composable
fun AddCardScreen_Ui(
    onBack: () -> Unit = {}, state: AddCardState, onIntent: (intent: AddCardIntent) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            ToolBar(
                onBack = onBack
            )
        },
    ) { pv ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = pv.calculateTopPadding() + 16.dp, bottom = pv.calculateBottomPadding() + 16.dp, start = 24.dp, end = 24.dp
                )
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }, verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(Modifier.weight(1f)) {
                Column(
                    Modifier.verticalScroll(rememberScrollState())
                ) {
                    CardNumberField(
                        title = "Card Number",
                        cardNumber = state.formFields.cardNumber,
                        onPostValue = {
                            onIntent.invoke(AddCardIntent.StringFieldChanged(AddCardFieldType.CARD_NUMBER, it))
                        },
                        type = KeyboardType.Number,
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                        // FIXME field
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = "Expired Date", style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = primaryFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF020614),
                                ), modifier = Modifier.padding(top = 8.dp)
                            )

                            Spacer(Modifier.height(16.dp))

                            ReadonlyTextField(
                                value = state.formFields.expirationDate.value,
                                onValueChange = {},
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    onIntent.invoke(AddCardIntent.ToggleDatePicker(isShown = true))
                                },
                                error = state.formFields.expirationDate.error
                            )
                        }

                        FormField(
                            title = "CVC/CVV",
                            onValueChange = {
                                onIntent.invoke(AddCardIntent.StringFieldChanged(AddCardFieldType.CVV_CODE, it))
                            },
                            uiField = state.formFields.cvvCode,
                            modifier = Modifier.weight(1f),
                            type = KeyboardType.Number
                        )
                    }

                    FormField(
                        title = "Cardholder Name",
                        onValueChange = {
                            onIntent.invoke(AddCardIntent.StringFieldChanged(AddCardFieldType.CARD_HOLDER, it))
                        },
                        uiField = state.formFields.cardHolder,
                        capitalize = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Billing Address", style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333333),
                        )
                    )

                    FormField(
                        title = "Address Line 1",
                        onValueChange = {
                            onIntent.invoke(AddCardIntent.StringFieldChanged(AddCardFieldType.ADDRESS_LINE_1, it))
                        },
                        uiField = state.formFields.addressFirstLine,
                    )

                    FormField(
                        title = "Address Line 2",
                        onValueChange = {
                            onIntent.invoke(AddCardIntent.StringFieldChanged(AddCardFieldType.ADDRESS_LINE_2, it))
                        },
                        uiField = state.formFields.addressSecondLine,
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            PrimaryButton(
                onClick = {
                    onIntent.invoke(AddCardIntent.SaveCard)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                text = stringResource(R.string.save_card)
            )
        }


    }

    if (state.isLoading) {
        FullscreenProgressBar()
    }

    if (state.showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { selectedMills ->
                onIntent.invoke(AddCardIntent.ExpirationPickerSet(selectedMills))
                onIntent.invoke(AddCardIntent.ToggleDatePicker(false))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolBar(onBack: () -> Unit) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.add_a_card),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF262626),
                    textAlign = TextAlign.Center,
                ),
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBack.invoke() }, modifier = Modifier.padding(start = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_nav_back), contentDescription = null
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}


@Composable
private fun FormField(
    title: String,
    // TODO rename
    uiField: UiField,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    type: KeyboardType = KeyboardType.Text,
    capitalize: Boolean = false
) {

    Column(modifier = modifier) {
        Text(
            text = title, style = TextStyle(
                fontSize = 12.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF020614),
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        PrimaryTextField(
            value = if (capitalize) {
                uiField.value.toUpperCase(Locale.current)
            } else {
                uiField.value
            },
            onValueChange = {
                onValueChange.invoke(it)
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = type,
            ),
            error = uiField.error
        )
    }
}


@Composable
@Preview(device = Devices.NEXUS_5)
fun AddCardScreen_Preview() {
    ScreenPreview {
        AddCardScreen_Ui(
            state = AddCardState.mock()
        )
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
fun AddCardScreen_Loading_Preview() {
    ScreenPreview {
        AddCardScreen_Ui(
            state = AddCardState.mock(isLoading = true)
        )
    }
}


@Composable
@Preview(device = Devices.NEXUS_5)
fun AddCardScreen_Datepicker_Preview() {
    ScreenPreview {
        AddCardScreen_Ui(
            state = AddCardState.mock(showDatePicker = true)
        )
    }
}