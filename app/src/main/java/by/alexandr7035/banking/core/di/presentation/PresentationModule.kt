package by.alexandr7035.banking.core.di.presentation

import by.alexandr7035.banking.ui.app_host.AppViewModel
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.AddCardViewModel
import by.alexandr7035.banking.ui.feature_cards.screen_card_details.CardDetailsViewModel
import by.alexandr7035.banking.ui.feature_cards.screen_card_list.CardListViewModel
import by.alexandr7035.banking.ui.feature_home.HomeViewModel
import by.alexandr7035.banking.ui.feature_login.LoginViewModel
import by.alexandr7035.banking.ui.feature_onboarding.OnboardingViewModel
import by.alexandr7035.banking.ui.feature_profile.ProfileViewModel
import by.alexandr7035.banking.ui.feature_savings.SavingsViewModel
import by.alexandr7035.banking.ui.feature_savings.screen_saving_details.SavingDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        OnboardingViewModel(
            passOnboardingUseCase = get()
        )
    }

    viewModel {
        LoginViewModel(
            loginWithEmailUseCase = get(),
            validateEmailUseCase = get(),
            validatePasswordUseCase = get()
        )
    }
    viewModel {
        AppViewModel(
            checkIfLoggedInUseCase = get(),
            checkIfPassedOnboardingUseCase = get()
        )
    }
    viewModel {
        ProfileViewModel(
            getCompactProfileUseCase = get(),
            logoutUseCase = get()
        )
    }
    viewModel {
        HomeViewModel(
            getHomeCardsUseCase = get(),
            getHomeSavingsUseCase = get(),
            getCompactProfileUseCase = get()
        )
    }
    viewModel { CardListViewModel(getAllCardsUseCase = get()) }
    viewModel {
        CardDetailsViewModel(
            getCardByNumberUseCase = get(),
            deleteCardByNumberUseCase = get()
        )
    }

    viewModel {
        AddCardViewModel(
            validateCardNumberUseCase = get(),
            validateCvvCodeUseCase = get(),
            validateCardExpirationUseCase = get(),
            validateCardHolderUseCase = get(),
            validateBillingAddressUseCase = get(),
            addCardUseCase = get()
        )
    }

    viewModel {
        SavingsViewModel(
            getAllSavingsUseCase = get()
        )
    }

    viewModel {
        SavingDetailsViewModel(
            getSavingByIdUseCase = get(),
            getCardByNumberUseCase = get()
        )
    }
}