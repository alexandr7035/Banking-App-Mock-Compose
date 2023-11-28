package by.alexandr7035.banking

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import by.alexandr7035.banking.ui.app_host.AppContainerScreen
import by.alexandr7035.banking.ui.theme.BankingAppTheme

// AppCompatActivity extends FragmentActivity which extends ComponentActivity
// Need FragmentActivity at least for biometrics
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BankingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContainerScreen()
                }
            }
        }
    }
}