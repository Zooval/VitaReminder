package com.example.vitareminder.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.vitareminder.view.LoginActivity
import com.example.vitareminder.R
import com.example.vitareminder.view.RegisterActivity
import com.example.vitareminder.contract.MainContract
import com.example.vitareminder.presenter.MainActivityLogic
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var auth: FirebaseAuth
    private lateinit var logic: MainContract.ActivityLogic

    private var loginButton: Button? = null
    private var registerButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        logic = MainActivityLogic(this, auth)

        // Delegamos la decisión al logic
        logic.onStart()
    }

    override fun showWelcomeLayout() {
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        loginButton?.setOnClickListener { logic.onLoginClicked() }
        registerButton?.setOnClickListener { logic.onRegisterClicked() }
    }

    override fun navigateToDashboardAndClearTask() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun onDestroy() {
        logic.detach()
        super.onDestroy()
    }
}