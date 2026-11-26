package by.alexandr7035.banking

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import by.alexandr7035.banking.LoginTest.LoginTestData.CREATE_PIN_TITLE
import by.alexandr7035.banking.LoginTest.LoginTestData.SCREEN_TIMEOUT_MS
import by.alexandr7035.banking.LoginTest.LoginTestData.VALID_EMAIL
import by.alexandr7035.banking.LoginTest.LoginTestData.VALID_PASSWORD
import by.alexandr7035.banking.pages.LoginPage
import by.alexandr7035.banking.testsetup.AppLoadingIdlingResource
import by.alexandr7035.banking.ui.app_host.AppViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.ext.android.getViewModel

@OptIn(ExperimentalTestApi::class)
class LoginTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var appLoadingIdlingResource: AppLoadingIdlingResource

    val loginPage = LoginPage(composeTestRule)

    @Before
    fun setUp() {
        val appViewModel = composeTestRule.activity.getViewModel<AppViewModel>()
        appLoadingIdlingResource = AppLoadingIdlingResource(appViewModel)
        composeTestRule.registerIdlingResource(appLoadingIdlingResource)
    }

    @After
    fun tearDown() {
        composeTestRule.unregisterIdlingResource(appLoadingIdlingResource)
    }

    // LG-001
    @Test
    fun login_with_valid_credentials_opens_create_pin_screen() {
        // Given: onboarding skipped
        composeTestRule.onNodeWithTag("onboarding_skip").performClick()
        composeTestRule.onNodeWithTag("onboarding_login_now").performClick()

        // When: user logs in with valid credentials
        loginPage.enterEmail(VALID_EMAIL)
        loginPage.enterPassword(VALID_PASSWORD)
        loginPage.clickOnSubmitButton()

        // Then: Create PIN screen is displayed
        composeTestRule.waitUntilAtLeastOneExists(
            hasText(CREATE_PIN_TITLE),
            timeoutMillis = SCREEN_TIMEOUT_MS
        )
        composeTestRule.onNodeWithText(CREATE_PIN_TITLE).assertIsDisplayed()
    }

    private object LoginTestData {
        const val VALID_EMAIL = "example@mail.com"
        const val VALID_PASSWORD = "1234567Ab"
        const val CREATE_PIN_TITLE = "Create PIN"
        const val SCREEN_TIMEOUT_MS = 5_000L
    }

}
