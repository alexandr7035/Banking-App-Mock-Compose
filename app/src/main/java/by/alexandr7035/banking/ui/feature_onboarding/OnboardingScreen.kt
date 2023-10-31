package by.alexandr7035.banking.ui.feature_onboarding

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.DotsPagerIndicator
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.SecondaryButton
import by.alexandr7035.banking.ui.components.TextBtn
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.theme.Gray20
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel(),
    onGoToLogin: () -> Unit = {},
    onSignUp: () -> Unit = {},
) {
    OnboardingScreen_Ui(
        onGoToLogin = onGoToLogin,
        onGoToSignUp = onSignUp,
        onIntent = { viewModel.emitIntent(it) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen_Ui(
    onIntent: (intent: OnboardingIntent) -> Unit = {},
    onGoToLogin: () -> Unit = {},
    onGoToSignUp: () -> Unit = {},
) {

    BoxWithConstraints(
        Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .width(maxWidth)
                .height(maxHeight)
                .verticalScroll(rememberScrollState())
        ) {

            val pages = OnboardingPage.getPages(LocalContext.current)
            val pagerState = rememberPagerState(pageCount = { pages.size })

            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current

            Spacer(modifier = Modifier.weight(1f))

            HorizontalPager(
                modifier = Modifier
                    .wrapContentHeight(),
                state = pagerState,
            ) { pageIndex ->
                val page = pages[pageIndex]

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = page.imgRes),
                            contentDescription = null,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxWidth()
                        )
                    }

                    Text(
                        text = page.title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 56.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = page.description,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 56.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

            }

            DotsPagerIndicator(
                totalDots = 3,
                selectedIndex = pagerState.currentPage,
                selectedColor = MaterialTheme.colorScheme.primary,
                unSelectedColor = Gray20
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(12.dp))

            if (pagerState.currentPage != pagerState.pageCount - 1) {
                Box(Modifier.padding(horizontal = 24.dp)) {
                    PrimaryButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.next_step)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextBtn(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.pageCount - 1)
                        }
                    },
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(R.string.skip_this_step)
                )

                Spacer(modifier = Modifier.height(32.dp))
            } else {
                LaunchedEffect(Unit) {
                    onIntent(OnboardingIntent.CompleteOnboarding)
                }

                Box(Modifier.padding(horizontal = 24.dp)) {
                    PrimaryButton(
                        onClick = {
                            onGoToSignUp()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.create_account)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Box(Modifier.padding(horizontal = 24.dp)) {
                    SecondaryButton(
                        onClick = {
                            onGoToLogin()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.login_now)
                    )
                }

                Spacer(Modifier.height(40.dp))
            }

        }
    }

}

@Composable
@Preview(device = Devices.PIXEL_2)
fun WizardScreen_Preview() {
    ScreenPreview {
        OnboardingScreen_Ui()
    }
}

@Composable
@Preview(
    device = Devices.NEXUS_5,
    fontScale = 1.25f
)
fun WizardScreen_Small_Preview() {
    ScreenPreview {
        OnboardingScreen_Ui()
    }
}