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
                // 1️⃣ Crear el objeto Medicamento
                val nuevoMedicamento = Medicamento(nombre, tipo, dosis, horario)

                // Recuperar categorías seleccionadas
                val categories = intent.getStringArrayListExtra("CATEGORIES") ?: arrayListOf()

                //Ya completamos MEDICAMENTO, la quitamos
                categories.remove("MEDICAMENTO")

                //Decidir a dónde ir ahora
                val nextIntent = when {
                    categories.contains("ACTIVIDAD") ->
                        Intent(this, AnadirActividadActivity::class.java)

                    categories.contains("CITA") ->
                        Intent(this, AnadirCitaActivity::class.java)

                    else ->
                        Intent(this, DashboardActivity::class.java)
                }

// 5️⃣ Pasar datos a la siguiente pantalla
                nextIntent.putExtra("EXTRA_MEDICAMENTO", nuevoMedicamento)
                nextIntent.putStringArrayListExtra("CATEGORIES", categories)

// 6️⃣ Ir a la siguiente pantalla
                startActivity(nextIntent)
                finish()


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
