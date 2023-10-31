package by.alexandr7035.banking.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleGridView(
    modifier: Modifier = Modifier,
    columns: Int,
    countOfItems: Int,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable() (index: Int) -> Unit,
) {
    val columnAndRowItems = (0 until countOfItems).chunked(columns)

    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement
    ) {
        columnAndRowItems.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = horizontalArrangement
            ) {
                rowItems.forEach { index ->
                    Box(modifier = Modifier.weight(1f)) {
                        content(index)
                    }
                }
            }
        }
    }
}