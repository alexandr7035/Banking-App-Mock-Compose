![GitHub top language](https://img.shields.io/github/languages/top/alexandr7035/Banking-App-Mock-Compose?color=%237f52ff&style=for-the-badge)



<br>
<p align="center"> 
   <img height="150" src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/eb81869d-818c-4039-8861-6513b0b62dbb"/> 
</p>

<h1 align="center"> 
   Banking-App-Mock-Compose
</h1>

* [About the app](#about-the-app)
   * [Stack](#stack)
   * [App usage](#app-usage)
   * [Implemented features](#implemented-features)
* [Technical details](#technical-details)
   * [App layers](#app-layers)
      * [UI layer](#ui-layer)
      * [Domain layer](#domain-layer)
      * [Data layer](#data-layer)
   * [Feature details](#feature-details)
      * [App lock](#app-lock)
      * [Permissions](#permissions)


# About the app
**Banking-App-Mock-Compose** is a mock android app written with **Jetpack Compose**

The primary purpose of the app is to develop a medium-size (~20K LoC) app using Compose instead of Android Views and enjoy all Compose pitfalls :)

The app is built with a **data <- domain <- ui** architecture typical for real applications with only difference that there is no remote data source. Thus, all the banking info is mocked on `data` layer. However, some data like Cards and Transactions is cached locally for persistence.

<p align="left">
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/5b60ccfd-c1a5-4716-a18b-d70c434f6ea1" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/bbb1c8ad-e1cd-4958-9da1-b4c32c88f67a" width="20%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/7b25851e-68ef-416e-bbdc-d37e67156f43" width="20%"/>
</p>

**Design reference**: https://www.figma.com/community/file/1106055934725589367/Finance-Mobile-App-UI-KIT

// APP DEMO VIDEO

## Stack
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


## App usage
Working credentials for the app:
> Login - `example@mail.com`  
> Password - `1234567Ab`  
> OTP - `1111`

use other credentials if you want to trigger an error
  
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

# Technical details

The app uses Clean Architecture layers and MVI for presentation layer.
<p>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/e7af2b8f-bd9c-421a-95b6-eae73ccb355d" width="75%"/>
</p>

## App layers

<details>
<summary><strong>UI layer</strong></summary>

### UI layer
The app contains single Root screen.
The screen servers for several purposeы:
- Show loading screen when app state prepared on start.
- Prepare and pass conditional navigation to NavHost.
- Setup [CompositionLocal](https://developer.android.com/jetpack/compose/compositionlocal) for NavHost.

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

// Then in ViewModel
when (intent) {
    is Load -> { loadDataFromServer() }
    is SomeClick -> { handleSomeClick() }
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

    // The EventEffect is a LaunchedEffect that will be executed, when the event is in its triggered state. 
    // When the event action was executed the effect calls the onConsumed callback to force ViewMode to set the event field to be consumed.
    EventEffect(
        event = state.someEvent,
        onConsumed = viewModel::onconsumeSomeEvent
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

    fun emitIntent(intent: ScreenIntent) {
        when (intent) {
            is ScreenIntent.Load {
                viewModelScope.launch {
                    // This extension wraps data returned by use case into
                    // OperationResult class which encapsulates errors
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

            fun onConsumeSomeEvent() {
                _state.update {
                    it.copy(someEvent = consumed)
                }
            }
        }
    }
}

```
</details>

  
<details>
<summary><strong>Domain layer</strong></summary>

### Domain layer

A use case
```kotlin
class GetHomeCardsUseCase(private val cardsRepository: CardsRepository) {
    // A use case contains only one public execute() method with a single responsibility
    suspend fun execute(): List<PaymentCard> {
        val allCards = cardsRepository.getCards()

        val (primary, other) = allCards.partition { it.isPrimary }

        val sortedPrimary = primary.sortedByDescending { it.addedDate }
        val sortedOther = other.sortedByDescending { it.addedDate }

        return (sortedPrimary + sortedOther).take(DISPLAYED_COUNT)
    }

    companion object {
        private const val DISPLAYED_COUNT = 3
    }
}
```

OperationResult
```kotlin
sealed class OperationResult<out T> {
    data class Success<out T>(val data: T) : OperationResult<T>()

    data class Failure(val error: AppError) : OperationResult<Nothing>()

    fun isSuccess(): Boolean {
        return when (this) {
            is Success -> true
            is Failure -> false
        }
    }

    companion object {
        inline fun <R> runWrapped(block: () -> R): OperationResult<R> {
            return try {
                val res = block()
                Success(res)
            } catch (e: Exception) {
                when (e) {
                    is AppError -> Failure(e)
                    else -> Failure(AppError(ErrorType.fromThrowable(e)))
                }
            }
        }
    }
}

enum class ErrorType {
    USER_NOT_FOUND,
    WRONG_PASSWORD,
    CARD_NOT_FOUND,
    INSUFFICIENT_CARD_BALANCE,
    UNKNOWN_ERROR,;

    companion object {
        fun fromThrowable(e: Throwable): ErrorType {
            // Here may be additional mapping depending on exception type
            return when (e) {
                is AppError -> e.errorType
                else -> UNKNOWN_ERROR
            }
        }
    }
}

```

// Then we can use AppError in repositories:
```kotlin
// Throw AppError in repository 
// It will be later handled in business logic or ui
override suspend fun getCardById(id: String): PaymentCard = withContext(coroutineDispatcher) {
    // Load card from DB or throw error
    val card = cardsDao.getCardByNumber(id) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
    return@withContext mapCachedCardToDomain(card)
}
```

// Or detect AppError directly from exception
```kotlin
// Example of usage in ViewModel's CoroutineExceptionHandler
private val errorHandler = CoroutineExceptionHandler { _, throwable ->
    reduceError(ErrorType.fromThrowable(throwable))
}

viewModelScope.launch(errorHandler) {
    // Can safely call usecases here without wrapping in OperationResult
}

```

AppError (throw in repo or ErrorInterceptor)
then errors can be thrown directly in repos
or mapped from exception depenin on type

ErrorType
asuitexterror

for money - MoneyAmount

</details>
  
<details>
  <summary><strong>Data layer</strong></summary>

### Data layer 
data
mock repos with delays

Transaction handling
TransactionWorkManager

</details>  
  
## Feature details

<details>
  <summary><strong>App lock</strong></summary>

### App lock
PIN
Biometrics
When it asked to create applock

</details>  
  

<details>
  <summary><strong>Permissions</strong></summary>

### Permissions
Pemission helper composition local

</details>  
  
<br>

# USED MATERIALS



