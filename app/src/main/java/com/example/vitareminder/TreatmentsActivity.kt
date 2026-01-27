package com.example.vitareminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TreatmentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_treatments)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnNuevoTratamiento: Button = findViewById(R.id.btnNuevoTratamiento)
        btnNuevoTratamiento.setOnClickListener {
            val intent = Intent(this, NuevoTratamientoActivity::class.java)
            startActivity(intent)
        }

        val btnVolver: Button = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish() // Cierra esta pantalla y vuelve al Dashboard
        }

    }
}