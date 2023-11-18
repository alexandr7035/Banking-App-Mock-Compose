package by.alexandr7035.banking.ui.feature_help

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.components.search.SearchField
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun HelpScreen(
    onBack: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
    ) {
        SecondaryToolBar(
            onBack = onBack,
            title = UiText.StringResource(R.string.help_center)
        )

        val searchQuery = rememberSaveable() {
            mutableStateOf<String>("")
        }

        SearchField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                ),
            query = searchQuery.value,
            onQueryChanged = {
                searchQuery.value = it
            }
        )

        val helpItems = listOf(
            HelpItem(
                title = "How to1",
                text = "How to Looong looong text",
            ),
            HelpItem(
                title = "How to2",
                text = "How to Looong looong text",
            ),
            HelpItem(
                title = "How to3",
                text = "How to Looong looong text",
            )
        )

        val expandedItem = remember {
            mutableStateOf<HelpItem?>(null)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            val filtered = if (searchQuery.value.isNotBlank()) {
                helpItems.filter {
                    val text = it.text.uppercase()
                    val title = it.title.uppercase()
                    val query = searchQuery.value.uppercase()
                    text.contains(query) || title.contains(query)
                }
            }
            else {
                helpItems
            }

            items(filtered) {
                HelpItemCard(
                    item = it,
                    expanded = expandedItem.value == it
                ) {
                    expandedItem.value = if (expandedItem.value == it) null else it
                }
            }
        }
    }
}

@Composable
private fun HelpItemCard(
    item: HelpItem,
    expanded: Boolean,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .animateContentSize()
            .clickable {
                onClick()
            }
            .padding(
                top = 24.dp,
                start = 24.dp,
                end = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val titleColor = if (expanded) {
                Color(0xFF333333)
            } else {
                Color(0xFF666666)
            }

            Text(
                text = item.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = animateColorAsState(targetValue = titleColor).value,
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                tint = Color(0xFF666666)
            )
        }

        if (expanded) {
            Text(
                text = item.text,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 26.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF999999),
                )
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFD9D8DC)
        )
    }
}

@Composable
@Preview
fun HelpScreen_Preview() {
    ScreenPreview {
        HelpScreen()
    }
}