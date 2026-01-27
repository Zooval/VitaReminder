package com.example.vitareminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        // 🔗 Referencias UI
        val doctorInputLayout: TextInputLayout = findViewById(R.id.doctorInputLayout)
        val dateInputLayout: TextInputLayout = findViewById(R.id.dateInputLayout)
        val timeInputLayout: TextInputLayout = findViewById(R.id.timeInputLayout)
        val continueButton: Button = findViewById(R.id.continueButton)

        continueButton.setOnClickListener {

            // Leer datos
            val doctor = doctorInputLayout.editText?.text.toString().trim()
            val fecha = dateInputLayout.editText?.text.toString().trim()
            val hora = timeInputLayout.editText?.text.toString().trim()

            // Validación desharcodeada
            if (doctor.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                val errorMsg = getString(R.string.error_campo_requerido)
                
                doctorInputLayout.error = if (doctor.isEmpty()) errorMsg else null
                dateInputLayout.error = if (fecha.isEmpty()) errorMsg else null
                timeInputLayout.error = if (hora.isEmpty()) errorMsg else null
                
                return@setOnClickListener
            }

            // Crear objeto Cita
            val cita = Cita(doctor, fecha, hora)

            // Recuperar datos previos
            val medicamento = intent.getParcelableExtra<Medicamento>("EXTRA_MEDICAMENTO")
            val actividad = intent.getParcelableExtra<Actividad>("EXTRA_ACTIVIDAD")

            // Ir al Dashboard (Fin del flujo)
            val dashboardIntent = Intent(this, DashboardActivity::class.java)
            dashboardIntent.putExtra("EXTRA_MEDICAMENTO", medicamento)
            dashboardIntent.putExtra("EXTRA_ACTIVIDAD", actividad)
            dashboardIntent.putExtra("EXTRA_CITA", cita)

            startActivity(dashboardIntent)
            finish()
        }
    }
}
