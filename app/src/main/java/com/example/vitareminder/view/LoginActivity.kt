package com.example.vitareminder.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vitareminder.R
import com.example.vitareminder.contract.LoginContract
import com.example.vitareminder.presenter.LoginActivityLogic
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var auth: FirebaseAuth
    private lateinit var logic: LoginContract.ActivityLogic

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        logic = LoginActivityLogic(this, auth)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            logic.onLoginClicked(
                email = emailInputLayout.editText?.text?.toString().orEmpty(),
                password = passwordInputLayout.editText?.text?.toString().orEmpty()
            )
        }
    }

    override fun showEmailError(error: String?) {
        emailInputLayout.error = error
    }

    override fun showPasswordError(error: String?) {
        passwordInputLayout.error = error
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun navigateToDashboardAndClearTask() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun getErrorEmailVacio(): String = getString(R.string.error_email_vacio)
    override fun getErrorPasswordVacio(): String = getString(R.string.error_password_vacio)
    override fun getMsjIniciandoSesion(): String = getString(R.string.msj_iniciando_sesion)

    override fun getErrorAutenticacion(detail: String?): String {
        return getString(R.string.error_autenticacion, detail)
    }

    override fun onDestroy() {
        logic.detach()
        super.onDestroy()
    }
}