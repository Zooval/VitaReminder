package com.example.vitareminder

import android.content.Intent // Asegúrate de que esta importación esté presente
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- INICIO DE LA LÓGICA DE VALIDACIÓN ---

        // 1. Obtener referencias a los componentes del layout
        val emailInputLayout: TextInputLayout = findViewById(R.id.emailInputLayout)
        val passwordInputLayout: TextInputLayout = findViewById(R.id.passwordInputLayout)
        val loginButton: Button = findViewById(R.id.loginButton)

        // 2. Configurar el listener para el botón de Ingresar
        loginButton.setOnClickListener {
            // Limpiamos errores previos al hacer clic
            emailInputLayout.error = null
            passwordInputLayout.error = null

            // Extraemos el texto de los campos de entrada
            val email = emailInputLayout.editText?.text.toString()
            val password = passwordInputLayout.editText?.text.toString()

            var isValid = true

            // 3. Validar el campo de email
            if (email.isEmpty()) {
                emailInputLayout.error = "El email no puede estar vacío"
                isValid = false
            } else if (!email.contains("@")) {
                emailInputLayout.error = "Email no válido"
                isValid = false
            }

            // 4. Validar el campo de contraseña
            if (password.isEmpty()) {
                passwordInputLayout.error = "La contraseña no puede estar vacía"
                isValid = false
            } else if (password.length < 6) {
                passwordInputLayout.error = "La contraseña debe tener al menos 6 caracteres"
                isValid = false
            }

            // 5. Si todo es válido, navegar a la pantalla de Nuevo Tratamiento
            if (isValid) {
                // Creamos la intención para ir a la siguiente pantalla
                val intent = Intent(this, NuevoTratamientoActivity::class.java)
                startActivity(intent)

                // Opcional pero recomendado: Cierra LoginActivity para que el usuario no pueda
                // volver a ella con el botón de "atrás" desde la pantalla principal.
                finish()
            }
        }
    }
}
