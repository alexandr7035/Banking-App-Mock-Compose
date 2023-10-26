package by.alexandr7035.banking.ui.app_host

sealed class AppIntent {
    object EnterApp: AppIntent()
    object TryPostUnlock: AppIntent()
}
