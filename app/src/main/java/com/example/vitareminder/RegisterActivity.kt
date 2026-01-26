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
                usernameInputLayout.error = "Ingresa un nombre de usuario"
                isValid = false
            }

            if (email.isEmpty()) {
                emailInputLayout.error = "El email es obligatorio"
                isValid = false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailInputLayout.error = "Formato de email inválido"
                isValid = false
            }

            if (password.length < 6) {
                passwordInputLayout.error = "Mínimo 6 caracteres"
                isValid = false
            }

            if (password != confirmPassword) {
                confirmPasswordInputLayout.error = "Las contraseñas no coinciden"
                isValid = false
            }

            if (isValid) {
                registrarUsuario(email, password, username)
            }
        }
    }

    private fun registrarUsuario(email: String, pass: String, username: String) {
        // Mostrar un Toast o ProgressDialog (opcional) para indicar carga
        Toast.makeText(this, "Registrando...", Toast.LENGTH_SHORT).show()

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    
                    // Actualizar el perfil del usuario con el nombre de usuario
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                    }
                    
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                        // Guardar datos adicionales en Firestore (opcional pero recomendado)
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
                                    // Aunque falle Firestore, el usuario ya se creó en Auth
                                    irADashboard()
                                }
                        }
                    }

                } else {
                    // Si el registro falla, mostrar el error
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
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
