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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.PrimaryTextField
import by.alexandr7035.banking.ui.core.ScreenPreview
import by.alexandr7035.banking.ui.feature_cards.components.CardNumberField
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun AddCardScreen(
    onBack: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            ToolBar(
                onBack = onBack
            )
        },
    ) { pv ->
//        BoxWithConstraints(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(
//                    top = pv.calculateTopPadding(),
//                    bottom = pv.calculateBottomPadding()
//                )
//        ) {

        pv

        Column(
            modifier = Modifier
                .fillMaxSize()
//                    .height(maxHeight)
//                    .width(maxWidth)
//                    .verticalScroll(rememberScrollState())
                .padding(
                    top = pv.calculateTopPadding() + 16.dp,
                    bottom = pv.calculateBottomPadding() + 16.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(Modifier.weight(1f)) {
                AddCardFormUi()
            }

            Spacer(Modifier.height(16.dp))

            PrimaryButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth().imePadding(),
                text = stringResource(R.string.save_card)
            )
        }
    }
//    }
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
                onClick = { onBack.invoke() },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_nav_back),
                    contentDescription = null
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}


@Composable
private fun AddCardFormUi() {
        Column(
                Modifier
                    .verticalScroll(rememberScrollState())
        ) {
            CardNumberField(
                onPostValue = {},
                type = KeyboardType.Number
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FormField(
                    title = "Expired Date",
                    onPostValue = {},
                    modifier = Modifier.weight(1f)
                )

                FormField(
                    title = "CVC/CVV",
                    onPostValue = {},
                    modifier = Modifier.weight(1f),
                    type = KeyboardType.Number
                )
            }

            FormField(
                title = "Cardholder Name",
                onPostValue = {},
                capitalize = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Billing Address",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                )
            )

            FormField(
                title = "Address Line 1",
                onPostValue = {},
            )

            FormField(
                title = "Address Line 2",
                onPostValue = {},
            )
        }
}

@Composable
private fun FormField(
    title: String,
    onPostValue: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    type: KeyboardType = KeyboardType.Text,
    capitalize: Boolean = false
) {

    val value = rememberSaveable {
        mutableStateOf("")
    }

    Column(modifier = modifier) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF020614),
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        PrimaryTextField(
            value = value.value,
            onValueChange = {
                if (capitalize) {
                    value.value = it.toUpperCase(Locale.current)
                } else {
                    value.value = it
                }

                onPostValue.invoke(it)
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = type,
            )
        )
    }


}


@Composable
@Preview(device = Devices.NEXUS_5)
fun AddCardScreen_Preview() {
    ScreenPreview {
        AddCardScreen()
    }
}