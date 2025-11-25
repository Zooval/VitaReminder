package com.example.vitareminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class AnadirMedicamentoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anadir_medicamento)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- INICIO DE LA LÓGICA ---
        val nameInputLayout: TextInputLayout = findViewById(R.id.nameInputLayout)
        val typeInputLayout: TextInputLayout = findViewById(R.id.typeInputLayout)
        val doseInputLayout: TextInputLayout = findViewById(R.id.doseInputLayout)
        val scheduleInputLayout: TextInputLayout = findViewById(R.id.scheduleInputLayout)
        val continueButton: Button = findViewById(R.id.continueButton)

        continueButton.setOnClickListener {
            val nombre = nameInputLayout.editText?.text.toString().trim()
            val tipo = typeInputLayout.editText?.text.toString().trim()
            val dosis = doseInputLayout.editText?.text.toString().trim()
            val horario = scheduleInputLayout.editText?.text.toString().trim()

            // Validación simple
            if (nombre.isNotEmpty() && tipo.isNotEmpty() && dosis.isNotEmpty() && horario.isNotEmpty()) {
                // 1. Crear el objeto Medicamento con los datos
                val nuevoMedicamento = Medicamento(nombre, tipo, dosis, horario)

                // 2. Crear el Intent y pasar el objeto
                val intent = Intent(this, DashboardActivity::class.java).apply {
                    putExtra("EXTRA_MEDICAMENTO", nuevoMedicamento)
                    // Limpia las actividades anteriores para que no se pueda volver
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)

            } else {
                // Muestra un error si algún campo está vacío
                if (nombre.isEmpty()) nameInputLayout.error = "Campo requerido" else nameInputLayout.error = null
                if (tipo.isEmpty()) typeInputLayout.error = "Campo requerido" else typeInputLayout.error = null
                if (dosis.isEmpty()) doseInputLayout.error = "Campo requerido" else doseInputLayout.error = null
                if (horario.isEmpty()) scheduleInputLayout.error = "Campo requerido" else scheduleInputLayout.error = null
            }
        }
    }
}
