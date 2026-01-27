package com.example.vitareminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Referencias a la UI
        val usernameInputLayout: TextInputLayout = findViewById(R.id.usernameInputLayout)
        val emailInputLayout: TextInputLayout = findViewById(R.id.emailInputLayout)
        val passwordInputLayout: TextInputLayout = findViewById(R.id.passwordInputLayout)
        val confirmPasswordInputLayout: TextInputLayout = findViewById(R.id.confirmPasswordInputLayout)
        val registerButton: Button = findViewById(R.id.registerButton)
        val loginTextView: TextView = findViewById(R.id.loginTextView)

        // 2. Acción para ir al Login
        loginTextView.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 3. Lógica del botón de registro
        registerButton.setOnClickListener {
            val username = usernameInputLayout.editText?.text.toString().trim()
            val email = emailInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()
            val confirmPassword = confirmPasswordInputLayout.editText?.text.toString().trim()

            // Resetear errores
            usernameInputLayout.error = null
            emailInputLayout.error = null
            passwordInputLayout.error = null
            confirmPasswordInputLayout.error = null

            // Validaciones básicas
            var isValid = true

            if (username.isEmpty()) {
                usernameInputLayout.error = getString(R.string.error_username_vacio)

                isValid = false
            }

            if (email.isEmpty()) {
                emailInputLayout.error = getString(R.string.error_email_obligatorio)
                isValid = false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInputLayout.error = getString(R.string.error_email_invalido)
                isValid = false
            }

            if (password.length < 6) {
                passwordInputLayout.error = getString(R.string.error_password_corto)
                isValid = false
            }

            if (password != confirmPassword) {
                confirmPasswordInputLayout.error = getString(R.string.error_passwords_no_coinciden)
                isValid = false
            }

            if (isValid) {
                registrarUsuario(email, password, username)
            }
        }
    }

    private fun registrarUsuario(email: String, pass: String, username: String) {
        Toast.makeText(this, getString(R.string.msj_registrando), Toast.LENGTH_SHORT).show()

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                    }
                    
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                        val userData = hashMapOf(
                            "username" to username,
                            "email" to email,
                            "createdAt" to System.currentTimeMillis()
                        )
                        
                        user.uid.let { uid ->
                            db.collection("users").document(uid).set(userData)
                                .addOnSuccessListener {
                                    irADashboard()
                                }
                                .addOnFailureListener {
                                    irADashboard()
                                }
                        }
                    }

                } else {
                    val errorMsg = getString(R.string.error_registro, task.exception?.message)
                    Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun irADashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
