package by.alexandr7035.banking.ui.feature_onboarding

import android.content.Context
import by.alexandr7035.banking.R

data class OnboardingPage(
    val title: String,
    val description: String,
    val imgRes: Int
) {
    companion object {
        fun getPages(context: Context): List<OnboardingPage> {
            return listOf(
                OnboardingPage(
                    title = context.getString(R.string.wizard_title_1),
                    description = context.getString(R.string.wizard_desc_1),
                    imgRes = R.drawable.img_wizard_1
                ),

                OnboardingPage(
                    title = context.getString(R.string.wizard_title_2),
                    description = context.getString(R.string.wizard_desc_2),
                    imgRes = R.drawable.img_wizard_2
                ),

                OnboardingPage(
                    title = context.getString(R.string.wizard_title_3),
                    description = context.getString(R.string.wizard_desc_3),
                    imgRes = R.drawable.img_wizard_3
                ),
            )
        }
    }
}
