package com.example.vitareminder // Asegúrate que este sea tu paquete

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula este archivo de código con su layout XML
        setContentView(R.layout.activity_main)

        // 1. Encontrar los botones en el layout por su ID
        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)

        // 2. Asignar un "listener" al botón de Ingresar
        loginButton.setOnClickListener {
            // Crea una intención (Intent) para abrir LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            // Inicia la nueva actividad
            startActivity(intent)
        }

        // 3. Asignar un "listener" al botón de Registrarse
        registerButton.setOnClickListener {
            // Crea una intención para abrir RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            // Inicia la nueva actividad
            startActivity(intent)
        }
    }
}
