package by.alexandr7035.banking.ui.app_host

sealed class AppIntent {
    object EnterApp: AppIntent()
    object TryPostUnlock: AppIntent()

    // Use separate intent as navigation impossible
    // (when app lock is "global" screen  outside nav host)
    object AppLockLogout: AppIntent()
}
