package com.example.vitareminder.presenter

import android.util.Patterns
import com.example.vitareminder.contract.RegisterContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivityLogic(
    private var view: RegisterContract.View?,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : RegisterContract.ActivityLogic {

    override fun onLoginTextClicked() {
        view?.navigateBack()
    }

    override fun onRegisterClicked(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val u = username.trim()
        val e = email.trim()
        val p = password.trim()
        val cp = confirmPassword.trim()

        view?.clearErrors()

        var isValid = true

        if (u.isEmpty()) {
            view?.showUsernameError(view?.getErrorUsernameVacio())
            isValid = false
        }

        if (e.isEmpty()) {
            view?.showEmailError(view?.getErrorEmailObligatorio())
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
            view?.showEmailError(view?.getErrorEmailInvalido())
            isValid = false
        }

        if (p.length < 6) {
            view?.showPasswordError(view?.getErrorPasswordCorto())
            isValid = false
        }

        if (p != cp) {
            view?.showConfirmPasswordError(view?.getErrorPasswordsNoCoinciden())
            isValid = false
        }

        if (!isValid) return

        view?.showToastShort(view?.getMsjRegistrando().orEmpty())

        auth.createUserWithEmailAndPassword(e, p)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    view?.showToastLong(view?.getErrorRegistro(task.exception?.message).orEmpty())
                    return@addOnCompleteListener
                }

                val user = auth.currentUser
                if (user == null) {
                    view?.navigateToDashboardAndClearTask()
                    return@addOnCompleteListener
                }

                val profileUpdates = userProfileChangeRequest { displayName = u }

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener {
                        val userData = hashMapOf(
                            "username" to u,
                            "email" to e,
                            "createdAt" to System.currentTimeMillis()
                        )

                        db.collection("users").document(user.uid).set(userData)
                            .addOnSuccessListener { view?.navigateToDashboardAndClearTask() }
                            .addOnFailureListener { view?.navigateToDashboardAndClearTask() }
                    }
            }
    }

    override fun detach() {
        view = null
    }
}
