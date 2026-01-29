package com.example.vitareminder.contract

interface LoginContract {

    interface View {
        fun showEmailError(error: String?)
        fun showPasswordError(error: String?)

        fun showToast(message: String)

        fun navigateToDashboardAndClearTask()

        fun getErrorEmailVacio(): String
        fun getErrorPasswordVacio(): String
        fun getMsjIniciandoSesion(): String
        fun getErrorAutenticacion(detail: String?): String
    }

    interface ActivityLogic {
        fun onLoginClicked(email: String, password: String)
        fun detach()
    }
}
