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

class AnadirCitaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anadir_cita)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // UI
        val doctorInputLayout: TextInputLayout = findViewById(R.id.doctorInputLayout)
        val dateInputLayout: TextInputLayout = findViewById(R.id.dateInputLayout)
        val timeInputLayout: TextInputLayout = findViewById(R.id.timeInputLayout)
        val continueButton: Button = findViewById(R.id.continueButton)

        continueButton.setOnClickListener {

            // Leer datos
            val doctor = doctorInputLayout.editText?.text.toString().trim()
            val fecha = dateInputLayout.editText?.text.toString().trim()
            val hora = timeInputLayout.editText?.text.toString().trim()

            // Validación
            if (doctor.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                if (doctor.isEmpty()) doctorInputLayout.error = "Campo requerido" else doctorInputLayout.error = null
                if (fecha.isEmpty()) dateInputLayout.error = "Campo requerido" else dateInputLayout.error = null
                if (hora.isEmpty()) timeInputLayout.error = "Campo requerido" else timeInputLayout.error = null
                return@setOnClickListener
            }

            // Crear objeto Cita
            val cita = Cita(doctor, fecha, hora)

            // Recuperar datos previos
            val medicamento = intent.getParcelableExtra<Medicamento>("EXTRA_MEDICAMENTO")
            val actividad = intent.getParcelableExtra<Actividad>("EXTRA_ACTIVIDAD")

            // Ir al Dashboard
            val dashboardIntent = Intent(this, DashboardActivity::class.java)
            dashboardIntent.putExtra("EXTRA_MEDICAMENTO", medicamento)
            dashboardIntent.putExtra("EXTRA_ACTIVIDAD", actividad)
            dashboardIntent.putExtra("EXTRA_CITA", cita)

            startActivity(dashboardIntent)
            finish()
        }
    }
}
