package by.alexandr7035.banking.ui.feature_wizard

import android.content.Context
import android.graphics.Paint
import androidx.annotation.IdRes
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import by.alexandr7035.banking.R

data class WizardPage(
    val title: String,
    val description: String,
    val imgRes: Int
) {
    companion object {
        fun getPages(context: Context): List<WizardPage> {
            return listOf(
                WizardPage(
                    title = context.getString(R.string.wizard_title_1),
                    description = context.getString(R.string.wizard_desc_1),
                    imgRes = R.drawable.img_wizard_1
                ),

                WizardPage(
                    title = context.getString(R.string.wizard_title_2),
                    description = context.getString(R.string.wizard_desc_2),
                    imgRes = R.drawable.img_wizard_2
                ),

                WizardPage(
                    title = context.getString(R.string.wizard_title_3),
                    description = context.getString(R.string.wizard_desc_3),
                    imgRes = R.drawable.img_wizard_3
                ),
            )
        }
    }
}
