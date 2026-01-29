package com.example.vitareminder.presenter

import com.example.vitareminder.contract.LoginContract
import com.google.firebase.auth.FirebaseAuth

class LoginActivityLogic(
    private var view: LoginContract.View?,
    private val auth: FirebaseAuth
) : LoginContract.ActivityLogic {

    override fun onLoginClicked(email: String, password: String) {
        val e = email.trim()
        val p = password.trim()

        // limpiar errores (la View decide cómo)
        view?.showEmailError(null)
        view?.showPasswordError(null)

        var isValid = true

        if (e.isEmpty()) {
            view?.showEmailError(view?.getErrorEmailVacio())
            isValid = false
        }

        if (p.isEmpty()) {
            view?.showPasswordError(view?.getErrorPasswordVacio())
            isValid = false
        }

        if (!isValid) return

        view?.showToast(view?.getMsjIniciandoSesion().orEmpty())

        auth.signInWithEmailAndPassword(e, p)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view?.navigateToDashboardAndClearTask()
                } else {
                    val msg = view?.getErrorAutenticacion(task.exception?.message).orEmpty()
                    view?.showToast(msg)
                }
            }
    }

    override fun detach() {
        view = null
    }
}
