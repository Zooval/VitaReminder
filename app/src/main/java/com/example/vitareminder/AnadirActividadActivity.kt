package com.example.vitareminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class AnadirActividadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anadir_actividad)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔗 Referencias UI
        val nameInputLayout: TextInputLayout = findViewById(R.id.activityNameInputLayout)
        val durationInputLayout: TextInputLayout = findViewById(R.id.activityDurationInputLayout)
        val frequencyInputLayout: TextInputLayout = findViewById(R.id.activityFrequencyInputLayout)
        val continueButton: Button = findViewById(R.id.continueButton)

        continueButton.setOnClickListener {

            // Leer datos
            val nombre = nameInputLayout.editText?.text.toString().trim()
            val duracion = durationInputLayout.editText?.text.toString().trim()
            val frecuencia = frequencyInputLayout.editText?.text.toString().trim()

            // Validación
            if (nombre.isEmpty() || duracion.isEmpty() || frecuencia.isEmpty()) {
                if (nombre.isEmpty()) nameInputLayout.error = "Campo requerido" else nameInputLayout.error = null
                if (duracion.isEmpty()) durationInputLayout.error = "Campo requerido" else durationInputLayout.error = null
                if (frecuencia.isEmpty()) frequencyInputLayout.error = "Campo requerido" else frequencyInputLayout.error = null
                return@setOnClickListener
            }

            // Crear objeto Actividad
            val actividad = Actividad(nombre, duracion, frecuencia)

            // Recuperar datos previos
            val categories = intent.getStringArrayListExtra("CATEGORIES") ?: arrayListOf()
            categories.remove("ACTIVIDAD")

            val medicamento = intent.getParcelableExtra<Medicamento>("EXTRA_MEDICAMENTO")

            // Decidir siguiente pantalla
            val nextIntent = when {
                categories.contains("CITA") ->
                    Intent(this, AnadirCitaActivity::class.java)

                else ->
                    Intent(this, DashboardActivity::class.java)
            }

            // Pasar datos
            nextIntent.putExtra("EXTRA_MEDICAMENTO", medicamento)
            nextIntent.putExtra("EXTRA_ACTIVIDAD", actividad)
            nextIntent.putStringArrayListExtra("CATEGORIES", categories)

            // Navegar
            startActivity(nextIntent)
            finish()
        }
    }
}
