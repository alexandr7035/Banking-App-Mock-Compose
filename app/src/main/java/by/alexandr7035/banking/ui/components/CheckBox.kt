package by.alexandr7035.banking.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomCheckBox(
    checked: Boolean,
    onValueChange: ((Boolean) -> Unit)?,
) {
    // TODO design
    Checkbox(
        checked = checked,
        onCheckedChange = onValueChange,
    )
}

@Composable
@Preview
fun Checkbox_Preview() {
    ScreenPreview {
        Column(Modifier.fillMaxSize()) {
            CustomCheckBox(
                checked = true,
                onValueChange = {}
            )

            CustomCheckBox(
                checked = false,
                onValueChange = {}
            )
        }

    }
}