package com.example.vitareminder.presenter

import com.example.vitareminder.contract.MainContract
import com.google.firebase.auth.FirebaseAuth

class MainActivityLogic(
    private var view: MainContract.View?,
    private val auth: FirebaseAuth
) : MainContract.ActivityLogic {

    override fun onStart() {
        if (auth.currentUser != null) {
            view?.navigateToDashboardAndClearTask()
        } else {
            view?.showWelcomeLayout()
        }
    }

    override fun onLoginClicked() {
        view?.navigateToLogin()
    }

    override fun onRegisterClicked() {
        view?.navigateToRegister()
    }

    override fun detach() {
        view = null
    }
}
