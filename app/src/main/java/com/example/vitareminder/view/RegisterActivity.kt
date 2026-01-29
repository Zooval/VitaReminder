package com.example.vitareminder.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vitareminder.R
import com.example.vitareminder.contract.RegisterContract
import com.example.vitareminder.presenter.RegisterActivityLogic
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var logic: RegisterContract.ActivityLogic

    private lateinit var usernameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var confirmPasswordInputLayout: TextInputLayout
    private lateinit var registerButton: Button
    private lateinit var loginTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        logic = RegisterActivityLogic(this, auth, db)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        usernameInputLayout = findViewById(R.id.usernameInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout)
        registerButton = findViewById(R.id.registerButton)
        loginTextView = findViewById(R.id.loginTextView)

        loginTextView.setOnClickListener { logic.onLoginTextClicked() }

        registerButton.setOnClickListener {
            logic.onRegisterClicked(
                username = usernameInputLayout.editText?.text?.toString().orEmpty(),
                email = emailInputLayout.editText?.text?.toString().orEmpty(),
                password = passwordInputLayout.editText?.text?.toString().orEmpty(),
                confirmPassword = confirmPasswordInputLayout.editText?.text?.toString().orEmpty()
            )
        }
    }

    override fun clearErrors() {
        usernameInputLayout.error = null
        emailInputLayout.error = null
        passwordInputLayout.error = null
        confirmPasswordInputLayout.error = null
    }

    override fun showUsernameError(error: String?) { usernameInputLayout.error = error }
    override fun showEmailError(error: String?) { emailInputLayout.error = error }
    override fun showPasswordError(error: String?) { passwordInputLayout.error = error }
    override fun showConfirmPasswordError(error: String?) { confirmPasswordInputLayout.error = error }

    override fun showToastShort(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showToastLong(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun navigateBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun navigateToDashboardAndClearTask() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun getErrorUsernameVacio(): String = getString(R.string.error_username_vacio)
    override fun getErrorEmailObligatorio(): String = getString(R.string.error_email_obligatorio)
    override fun getErrorEmailInvalido(): String = getString(R.string.error_email_invalido)
    override fun getErrorPasswordCorto(): String = getString(R.string.error_password_corto)
    override fun getErrorPasswordsNoCoinciden(): String = getString(R.string.error_passwords_no_coinciden)

    override fun getMsjRegistrando(): String = getString(R.string.msj_registrando)

    override fun getErrorRegistro(detail: String?): String {
        return getString(R.string.error_registro, detail)
    }

    override fun onDestroy() {
        logic.detach()
        super.onDestroy()
    }
}