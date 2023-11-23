# Banking-App-Mock-Compose

## Overview

**Banking-App-Mock-Compose** is a mock android app written with **Jetpack Compose**

The primary purpose of the app is to develop a medium-size (~20K LoC) app using Compose instead of Android Views and enjoy all Compose pitfalls :)

The app is build with a typical data <- domain <- ui architecture with only ....
that there is no remote datasource and all the banking data is mocked.
However, some data like Cards and Transactions is cached for persistance.

**Design reference**: https://www.figma.com/community/file/1106055934725589367/Finance-Mobile-App-UI-KIT

// 3-4 IMAGES
// APP DEMO VIDEO

**Stack**
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [compose-state-events](https://github.com/leonard-palm/compose-state-events) by Leonard Palm
- [Koin](https://insert-koin.io/docs/quickstart/android) DI
- [Ksprefs](https://github.com/cioccarellia/ksprefs)
- [Jetpack Navigation](https://developer.android.com/jetpack/compose/navigation) 🫠
- [Jetpack Security](https://developer.android.com/jetpack/androidx/releases/security) and [Biometrics](https://developer.android.com/jetpack/androidx/releases/biometric)
- [Jetpack Paging for Compose](https://developer.android.com/jetpack/androidx/releases/paging)
- [PermissionX](https://github.com/guolindev/PermissionX) plus own helpers
- [Splashscreen API](https://developer.android.com/develop/ui/views/launch/splash-screen)
- [Coil](https://github.com/coil-kt/coil)
- [Zxing (QR codes)](https://github.com/journeyapps/zxing-android-embedded)
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)

## Gallery
1. Onboarding, Sign in and user profile
<p align="left">
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/f6436798-c655-45fd-940f-909108f0cd8f" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/9af32fed-6bdd-421a-bbad-b0a6b30c9384" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/5b60ccfd-c1a5-4716-a18b-d70c434f6ea1" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/498d3dcc-a79f-4575-8bf5-bf678777683a" width="20%"/>
</p>

2. Cards and Savings
<p align="left">
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/aacaeac3-24f7-491d-a69f-604d06d8de5a" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/bbb1c8ad-e1cd-4958-9da1-b4c32c88f67a" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/edee3a09-940a-4c18-b8c6-8ceec1c017fb" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/7b25851e-68ef-416e-bbdc-d37e67156f43" width="20%"/>
</p>

## App usage
Login - `example@mail.com`  
Password - `1234567Ab`  
OTP - `1111`  
  
Scan QR - any qr code  
  
Info about mocked fields  

## Implemented features
- [ ] App core:
    - [X] Splash screen
    - [X] Navigation
    - [X] Operation handling core (OperationResult class)
    - [X] Popup messages (snackbar)
    - [X] Error UI
    - [X] App lock (PIN)
    - [X] App lock (biometrics)
    - [ ] Data enrcryption
- [X] Onboarding slider
- [X] Sign up with email
- [ ] Sign up with phone number
- [ ] Restore password
- [X] OTP verification
- [ ] Home screen:
    - [X] Cards
    - [X] Savings
    - [X] Profile
    - [X] Balance observable
- [ ] Cards
    - [X] Card list
    - [X] Create card
    - [X] Delete carrd
    - [X] Default card
    - [ ] Card Statistics
- [X] Savings
    - [X] Savings list
    - [X] Saving details
- [ ] Account operations
    - [X] Send money
    - [X] Top up
    - [ ] Request money
    - [ ] Pay
    - [X] Transaction manager
    - [X] Transaction history
    - [X] Transaction contacts
 - [ ] Profile
    - [X] Profile info
    - [X] QR scanner
    - [X] My QR
    - [ ] Edit profile
- [ ] Account and security section
- [X] Help section
- [ ] Notifications
- [X] Logout
- [X] Terms and Conditions (WebView)

## Implementation details

### UI layer
The app contains single Root screen.
The screen servers for seveal purpose:
- Show loading screen when app state prepared
- Prepare and pass conditional navigation to NavHost
- Setup CompositionLocal's for NavHost

```kotlin

// Root State
sealed class AppState {
    object Loading: AppState()

    data class Ready(
        val conditionalNavigation: ConditionalNavigation,
        val requireUnlock: Boolean = false
    ): AppState()

    data class InitFailure(val error: UiText): AppState()
}

// Root screen
@Composable
fun AppContainerScreen(viewModel: AppViewModel = koinViewModel()) {

    // Init jetpack navigation
    val navController = rememberNavController()
    
    // Preparations for CompositionLocal
    val snackBarHostState = remember { SnackbarHostState() }
    val hostCoroutineScope = rememberCoroutineScope()
    val permissionHelper =  koinInject<PermissionHelper>()

    val state = viewModel.appState.collectAsStateWithLifecycle().value

    when (state) {
        is AppState.Loading -> {
            AppLoadingScreen()
        }

        is AppState.Ready -> {
            CompositionLocalProvider(
                // "Global" snackBar that survives navigation
                LocalScopedSnackbarState provides ScopedSnackBarState(
                    value = snackBarHostState,
                    coroutineScope = hostCoroutineScope
                ),
                // Permissions utils to check and request runtime permissions
                LocalPermissionHelper provides permissionHelper
            ) {

                if (state.requireUnlock) {
                    // Show app lock screen with PIN / biometrics
                    LockScreen(...)
                }
                else {
                    // Nav host with all screens of the app
                    AppNavHost(
                        navController = navController,
                        // Pass conditional navigation
                        // Here we may redirect to login screen
                        conditionalNavigation = state.conditionalNavigation,
                        ...
                    )
                }
            }
        }

        // App init failed
        // E.g. we'are checking access token validity and receive some error
        is AppState.InitFailure -> {
            ErrorFullScreen(
                error = state.error,
                onRetry = { viewModel.emitIntent(AppIntent.EnterApp) }
            )
        }
    }
}

```

A typical screen is handled with 4 components:
- **State** - an object with screen data and user input
- **Intent** - an action triggered from screen (e.g. data load request or button click)
- **Screen Composable** - screen's UI
- **ViewModel** - to make it all work together

Screen state is represented with data or sealed class:
```kotlin
// State
data class ScreenState(
    val isLoading: Boolean = true,
    val error: UiText? = null,
    val displayedText: String? = null,
    // One-off event
    val someEvent: StateEvent = consumed
)
```

An intent is typically a sealed class to easilly handle it with exhaustive `when` condition:
```kotlin

// Intent
sealed class ScreenIntent {
    object Load: ScreenIntent()
    object SomeClick: ScreenIntent()
    ...
}

// Then in ViewModel:
when (intent) {
    is Load -> { ... }
    is SomeClick -> { ...}
}

```

A screen composable typically contains a nested ScreenUi composable to make it work with `@Preview`:
```kotlin
// Screen
@Composable
fun Screen(
    // Inect ViewModel
    viewModel: CardListViewModel = koinViewModel(),
    // Navigation callbacks
    onBack: () -> Unit,
    onAddCard: () -> Unit,
) {
    // Get composition local
    val permissionHelper = LocalPermisionHelper.current
    val context = LocalContext.current
    ...

    // Collect the state from ViewModel
    val state = viewModel.state.collectAsStateWithLifecycle().value

    // Show ui
    ScreenUi(state = state)

    // Init data load with some effect
    LaunchedEffect(Unit) {
        viewModel.emitIntent(ScreenIntent.Load)
    }

    // Consume events if any
    EventEffect(
        event = state.someEvent,
        ...
    ) {
        // Do action, e.g. show a snackbar notification
    }
}

```

ViewModel gets data from one or several usecases and reduces it to state for ui.
More complex cases may require **Flows** for multiple values and **Deferred** async/await for parallel requess.
```kotlin

class ScreenViewModel: ViewModel(
    private val someUseCase: SomeUseCase
) {
    private val _state = MutableStateFlow(ScreenState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: SomeIntent) {
        when (intent) {
            is ScreenIntent.Load {
                viewModelScope.launch {
                    // This extension wraps data returned by use case into
                    // OperationResult class also handling errors
                    val res = OperationResult.runWrapped { 
                        someUsecase.execute()
                    }
                    
                    when (res) {
                        is OperationResult.Success -> {
                            // res.data is some domain model which may be mapped to ui
                            val resUi =  SomeUiModel.mapFromDomain(res.data)
                            reduceSuccess(resUi)

                            // Additionally, we may trigger some event on condition:
                            _state.update {
                                it.copy(someEvent = triggered)
                            }
                        }
                        is OperationResult.Failure -> {
                            // ErrorType is a domain error which should be mapped to error text
                            reduceError(res.error.errorType.asUiTextError())
                        }
                    }
                }
            }
            
            // Here screen state is updated
            fun reduceError(...) {}
            fun reduceSuccess(...) {}
        }
    }
}

```

### Domain layer
use case
OperationResult
AppError (throw in repo or ErrorInterceptor)
ErrorType
asuitexterror

for money - MoneyAmount

### Data layer 
data
mock repos with delays

Transaction handling
TransactionWorkManager

### App lock
PIN
Biometrics
When it asked to create applock

### Permissions
Pemission helper composition local


# USED MATERIALS



