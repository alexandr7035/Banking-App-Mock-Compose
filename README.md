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
   * [Feature details](#feature-details)
* [Used materials](#used-materials)

# About the app
<details>
<summary><strong>ℹ️ 2025 updates</strong></summary>
<br>
A small technical update making app have fresh dependencies and build on recent Android Studio:  

- Bump dependencies
- Migrate gradle from Groovy to `*.ktx`
- Add version catalog
- Fix broken/incompatible Compose code after updates
- **TODO**: update `targetSdk` to **35** after finding appropriate UI solution for statusbar/navbar styling: https://issuetracker.google.com/issues/364591621?pli=1
</details>

**Banking-App-Mock-Compose** is a mock android app written with **Jetpack Compose**

The primary purpose of the app is to develop a medium-size (~20K LoC) app using [Jetpack Compose](https://developer.android.com/jetpack/compose) instead of Android Views and enjoy all Compose pitfalls :)

The app is built with a **data -> domain <- ui** architecture typical for real applications with only difference that there is no remote data source. Thus, all the banking info is mocked on `data` layer. However, some data like Cards and Transactions is cached locally for persistence.

<p align="left">
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/0f2973f9-f809-47e6-a32b-0ba344c5534e" width="15%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/d3816fa8-1dc9-4813-a719-a084d64f1420" width="15%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/aa4d128f-7424-400a-a10d-ac0a7934807f" width="15%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/9bd22682-a422-48ce-9bc9-dc005bd32129" width="15%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/946ae794-9568-4601-bbaa-b6179f1395d9" width="15%"/>
</p>

**Demo video**:  

https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/66766faa-ba83-424e-8fc5-17243365a9a8

**Design reference**: https://www.figma.com/community/file/1106055934725589367/Finance-Mobile-App-UI-KIT by [Aparna Tati](https://www.figma.com/@6c9aa553_8682_4)

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
**Working credentials for the app**:

> Login: `example@mail.com`  
> Password: `1234567Ab`  
> OTP: `1111`  

Use other credentials if you want to trigger an error.
  
**If app asks to scan QR**:
<p>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/c35d4e0e-4092-4e84-9b90-e0ea843aad7d" width="200"/>
</p>
(or use QR generated in Profile)


## Implementation plan
- [ ] App core:
    - [X] Splash screen
    - [X] Navigation
    - [X] Operation handling core (OperationResult class)
    - [X] Popup messages (snackbar)
    - [X] Error UI
    - [X] App lock (PIN)
    - [X] App lock (biometrics)
    - [ ] Data encryption
    - [X] Permissions management
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
    - [X] Delete card
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
- [X] App Settings
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
The app contains a root `AppContainerScreen` screen which servers for several purposes:
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
- **Screen @Composable** - screen's UI
- **ViewModel** - to manage UI's data in a lifecycle-aware way

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

An `Intent` is typically a sealed class to easilly handle it with exhaustive `when` condition:
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
    // Inject ViewModel
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
Building blocks:
- Use cases
- Domain models
- Domain error handling
- Repository interfaces

A use case is a class for specific app feature. By using use cases the presentation layer remains unaware of the data source details. 
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

A domain `OperationResult` model is used to represent result of a operation that may either succeed or finish with business logic error.
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

// Domain error types
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

`AppError` is an exception that can be used on all layers of the application:
```kotlin
data class AppError(val errorType: ErrorType): Exception()

// We can throw AppError in repositories
// It will be later handled in business logic or ui
override suspend fun getCardById(id: String): PaymentCard = withContext(coroutineDispatcher) {
    // Load card from DB or throw error
    val card = cardsDao.getCardByNumber(id) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
    return@withContext mapCachedCardToDomain(card)
}

// Or detect AppError directly from exception
// Example of usage in ViewModel's CoroutineExceptionHandler
private val errorHandler = CoroutineExceptionHandler { _, throwable ->
    reduceError(ErrorType.fromThrowable(throwable))
}

viewModelScope.launch(errorHandler) {
    // Can safely call usecases here without wrapping in OperationResult
}
```

Domain layer also includes models for core app entities like Cards, Savings and balances.
This is an example of domain model for money representation
```kotlin
// Wrapper class for money representation
// Used to encapsulation of chosen base types (Double, BigDecimal and so on)
// And to handle additional properties like currencies
data class MoneyAmount(
    val value: Float,
    val currency: BalanceCurrency = BalanceCurrency.USD,
) {
    operator fun plus(other: MoneyAmount): MoneyAmount {
        return this.copy(value = this.value + other.value)
    }

    operator fun minus(other: MoneyAmount): MoneyAmount {
        return this.copy(value = this.value - other.value)
    }

    operator fun compareTo(other: MoneyAmount): Int {
        return this.value.compareTo(other.value)
    }
}
```  

</details>
  
<details>
  <summary><strong>Data layer</strong></summary>

### Data layer
Building blocks:
- Repository implemetations
- Cache models and DAOs
- Workers

This is an example of a mock repository implementation for transaction caching and execution:
```kotlin
class TransactionRepositoryMock(
    private val workManager: WorkManager,
    private val transactionDao: TransactionDao,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val contactsRepository: ContactsRepository
) : TransactionRepository {

    // Cache new transaction and start a Worker for it
    override suspend fun submitTransaction(payload: TransactionRowPayload) {
        val raw = TransactionEntity(
            type = payload.type,
            value = payload.amount,
            linkedContactId = payload.contactId,
            createdDate = System.currentTimeMillis(),
            recentStatus = TransactionStatus.PENDING,
            updatedStatusDate = System.currentTimeMillis(),
            cardId = payload.cardId
        )
        val savedId = transactionDao.addTransaction(raw)

        val data = Data.Builder()
            .putLong(TransactionWorker.TRANSACTION_ID_KEY, savedId)
            .build()

        val workRequest =
            OneTimeWorkRequestBuilder<TransactionWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresBatteryNotLow(false)
                        .build()
                )
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setInputData(data)
                .build()

        workManager.enqueue(workRequest)
    }

    // Load transactions from cache with paging
    override suspend fun getTransactions(filterByType: TransactionType?): Flow<PagingData<Transaction>> {
        val contacts = contactsRepository.getContacts()

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_MAX_SIZE,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                TransactionSource(
                    filterByType = filterByType,
                    transactionDao = transactionDao
                )
            }
        ).flow.map {
            it.map { cachedTx ->
                mapTransactionFromCache(cachedTx, contacts)
            }
        }
    }

    // Get transaction status observer
    override fun getTransactionStatusFlow(transactionId: Long): Flow<TransactionStatus> {
        return flow {
            // Emit last cached status
            while (true) {
                val tx = transactionDao.getTransaction(transactionId) ?: throw AppError(ErrorType.TRANSACTION_NOT_FOUND)
                emit(tx.recentStatus)

                delay(MOCK_TRANSACTION_STATUS_CHECK_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    private fun mapTransactionFromCache(entity: TransactionEntity, contacts: List<Contact>) : Transaction {
        return Transaction(
            id = entity.id,
            type = entity.type,
            value = entity.value,
            recentStatus = entity.recentStatus,
            linkedContact = when (entity.type) {
                TransactionType.TOP_UP -> null
                else -> entity.linkedContactId?.let { id -> contacts.find { contact -> contact.id == id } }
            },
            createdDate = entity.createdDate,
            updatedStatusDate = entity.updatedStatusDate
        )
    }

    companion object {
        private const val PAGE_MAX_SIZE = 5
        private const val PREFETCH_DISTANCE = 1
        private const val MOCK_TRANSACTION_STATUS_CHECK_DELAY = 5000L
    }
}
```

</details>  
  
## Feature details

<details>
  <summary><strong>App lock</strong></summary>

### App lock
**Used material**: https://habr.com/ru/companies/redmadrobot/articles/475112/

PIN lock:
- use Password-Based Key Derivation Function to generate `serect` from PIN and `salt`
- use `EncryptedSharedPreferences` to store secrect.
So that PIN not stored in raw way.

```kotlin
private fun savePin(pin: String) {
    val salt = CryptoUtils.generateSalt()

    val secretKey = CryptoUtils.generatePbkdf2Key(
        passphraseOrPin = pin.toCharArray(),
        salt = salt
    )

    val encodedPinData = Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
    val encodedSalt = Base64.encodeToString(salt, Base64.DEFAULT)

    securedPreferences.edit()
        .putString(PIN_KEY, encodedPinData)
        .putString(PIN_SALT_KEY, encodedSalt)
        .apply()
}

private fun isPinValid(pin: String): Boolean {
    val storedSalt = securedPreferences.getString(PIN_SALT_KEY, null)
    val decodedSalt = Base64.decode(storedSalt, Base64.DEFAULT)

    val storedPinData = securedPreferences.getString(PIN_KEY, null)
    val decodedPinData = Base64.decode(storedPinData, Base64.DEFAULT)

    val enteredPinData = CryptoUtils.generatePbkdf2Key(pin.toCharArray(), decodedSalt)

    return decodedPinData contentEquals enteredPinData.encoded
}
```

Biometrics:
- use `androidx.biometric` to authenticate

⚠️ Drawbacks of current implemetation:
- `CryptoObject` from biometrics auth result is not used for PIN generation or data encryption. Biometric auth relies solely on `onAuthenticationSucceeded(...)` callback.  
- No data encryption provied (except EncryptedSharedPreferences).  

This implementation is very basic and not appropriate for a real banking app.
In this app it just used to showcase lock screen feature.

</details>  
  

<details>
  <summary><strong>Permissions</strong></summary>

### Runtime permissions
App uses `PermissionHelper` class with `PermissionX` library and some custom logic under the hood.

```kotlin
val LocalPermissionHelper = compositionLocalOf<PermissionHelper> { error("No PermissionHelper provided") }

// use in Composable
val permissionHelper = LocalPermissionHelper.current
val permission = android.Manifest.permission.CAMERA

when (permissionHelper.checkIfPermissionGranted(context, permission)) {
    CheckPermissionResult.SHOULD_ASK_PERMISSION -> {
        permissionHelper.askForPermission(context, permission) { res ->
            when (res) {
                AskPermissionResult.GRANTED -> { ... }
                AskPermissionResult.REJECTED -> { ... }
            }
        }
    }

    CheckPermissionResult.SHOULD_REDIRECT_TO_SETTINGS -> { ... }
    CheckPermissionResult.PERMISSION_ALREADY_GRANTED -> { ... }
}
```

</details>  
  
<br>

# Used materials
1. [Authenticate me. If you can…](https://habr.com/ru/companies/redmadrobot/articles/475112/)
2. [Android: Simple MVI implementation with Jetpack Compose](https://medium.com/@VolodymyrSch/android-simple-mvi-implementation-with-jetpack-compose-5ee5d6fc4908)
3. [Animations in Jetpack Compose with examples](https://blog.canopas.com/animations-in-jetpack-compose-with-examples-48307ba9dff1)
4. [Clickable SpannableText in Jetpack Compose](https://medium.com/@shmehdi01/clickable-spannabletext-in-jetpack-compose-c24514c34f27)
5. [Custom UI Component - Canvas and Jetpack Compose](https://youtu.be/XuZvHKwD_iM)
6. [Exception handling in Kotlin Coroutines](https://ganeshajdivekar.medium.com/exception-handling-in-kotlin-coroutines-b08c095ddea0)
7. [Field Validations using Jetpack Compose and MVVM](https://medium.com/@karthik.dusk/field-validations-using-jetpack-compose-and-mvvm-8c4ea947b35d)
8. [Firebase Analytics + Jetpack Compose](https://medium.com/mobilepeople/firebase-analytics-jetpack-compose-6bd5ee30fc6f)
9. [Formatting credit card number input in Jetpack compose Android](https://dev.to/benyam7/formatting-credit-card-number-input-in-jetpack-compose-android-2nal)
10. [How to Render Text as a QR code in Jetpack Compose](https://dev.to/devniiaddy/qr-code-with-jetpack-compose-47e)
11. [How to Validate Fields Using Jetpack Compose in Android](https://medium.com/@rzmeneghelo/how-to-validate-fields-using-jetpack-compose-in-android-43be70597e82)
12. [Input Validation With Clean Architecture In Jetpack Compose](https://medium.com/@mohammadjoumani/input-validation-with-clean-architecture-in-jetpack-compose-4225e2e86397)
13. [Jetpack Compose OTP input field](https://proandroiddev.com/jetpack-compose-otp-input-field-bcfa22c85e5f)
14. [Kotlin Coroutines patterns & anti-patterns](https://proandroiddev.com/kotlin-coroutines-patterns-anti-patterns-f9d12984c68e#69e0)
15. [Navigate back with result with Jetpack Compose](https://medium.com/@desilio/navigate-back-with-result-with-jetpack-compose-e91e6a6847c9)
16. [Paging With Clean Architecture In Jetpack Compose](https://medium.com/@mohammadjoumani/paging-with-clean-architecture-in-jetpack-compose-775fbf589256)
17. [Parallel API calls using Coroutines, having different return types](https://kashif-mehmood-km.medium.com/parallel-api-calls-using-coroutines-having-different-return-types-4d269a1b88d4)

