package com.example.vitareminder.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vitareminder.view.DashboardActivity
import com.example.vitareminder.R
import com.example.vitareminder.model.Medicamento
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
                // Crear el objeto Medicamento con los datos
                val nuevoMedicamento = Medicamento(nombre, tipo, dosis, horario)

                // Recuperar categorías seleccionadas
                val categories = intent.getStringArrayListExtra("CATEGORIES") ?: arrayListOf()

                // Ya completamos MEDICAMENTO, la quitamos
                categories.remove("MEDICAMENTO")

                // Decidir a dónde ir ahora
                val nextIntent = when {
                    categories.contains("ACTIVIDAD") ->
                        Intent(this, AnadirActividadActivity::class.java)

                    categories.contains("CITA") ->
                        Intent(this, AnadirCitaActivity::class.java)

                    else ->
                        Intent(this, DashboardActivity::class.java)
                }

                // Pasar datos a la siguiente pantalla
                nextIntent.putExtra("EXTRA_MEDICAMENTO", nuevoMedicamento)
                nextIntent.putStringArrayListExtra("CATEGORIES", categories)

                // Ir a la siguiente pantalla
                startActivity(nextIntent)
                finish()


            } else {
                // Muestra un error si algún campo está vacío usando recursos de strings
                val errorMsg = getString(R.string.error_campo_requerido)

                nameInputLayout.error = if (nombre.isEmpty()) errorMsg else null
                typeInputLayout.error = if (tipo.isEmpty()) errorMsg else null
                doseInputLayout.error = if (dosis.isEmpty()) errorMsg else null
                scheduleInputLayout.error = if (horario.isEmpty()) errorMsg else null
            }
        }
    }
}