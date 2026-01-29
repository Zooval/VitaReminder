package com.example.vitareminder.contract

interface MainContract {

    interface View {
        fun navigateToDashboardAndClearTask()
        fun navigateToLogin()
        fun navigateToRegister()
        fun showWelcomeLayout()
    }

    interface ActivityLogic {
        fun onStart()
        fun onLoginClicked()
        fun onRegisterClicked()
        fun detach()
    }
}
