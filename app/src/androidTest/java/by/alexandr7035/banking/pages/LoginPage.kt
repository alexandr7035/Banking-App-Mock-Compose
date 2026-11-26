package by.alexandr7035.banking.pages

import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement

class LoginPage(private val composeTestRule: ComposeTestRule) {

    fun enterEmail(email: String) {
        composeTestRule
            .onNode(hasSetTextAction() and hasAnyAncestor(hasTestTag("login_email")))
            .performTextReplacement(email)
    }

    fun enterPassword(password: String) {
        composeTestRule
            .onNode(hasSetTextAction() and hasAnyAncestor(hasTestTag("login_password")))
            .performTextReplacement(password)
    }

    fun clickOnSubmitButton() {
        composeTestRule.onNodeWithTag("login_submit").performClick()
    }
}
