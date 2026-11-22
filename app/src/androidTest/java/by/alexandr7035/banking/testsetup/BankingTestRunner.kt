package by.alexandr7035.banking.testsetup

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class BankingTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context
    ): Application {
        return super.newApplication(
            cl,
            TestBankingApp::class.java.name,
            context
        )
    }
}