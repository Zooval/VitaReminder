package com.example.vitareminder.contract

interface RegisterContract {

    interface View {
        fun clearErrors()

        fun showUsernameError(error: String?)
        fun showEmailError(error: String?)
        fun showPasswordError(error: String?)
        fun showConfirmPasswordError(error: String?)

        fun showToastShort(message: String)
        fun showToastLong(message: String)

        fun navigateBack()
        fun navigateToDashboardAndClearTask()

        fun getErrorUsernameVacio(): String
        fun getErrorEmailObligatorio(): String
        fun getErrorEmailInvalido(): String
        fun getErrorPasswordCorto(): String
        fun getErrorPasswordsNoCoinciden(): String

        fun getMsjRegistrando(): String
        fun getErrorRegistro(detail: String?): String
    }

    interface ActivityLogic {
        fun onLoginTextClicked()
        fun onRegisterClicked(
            username: String,
            email: String,
            password: String,
            confirmPassword: String
        )
        fun detach()
    }
}
