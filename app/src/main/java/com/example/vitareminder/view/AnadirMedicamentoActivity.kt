package com.example.vitareminder.view

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vitareminder.R
import com.example.vitareminder.model.Medicamento
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

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
        
        val typeAutoComplete: AutoCompleteTextView = findViewById(R.id.typeAutoComplete)
        val doseAutoComplete: AutoCompleteTextView = findViewById(R.id.doseAutoComplete)
        val scheduleEditText: TextInputEditText = findViewById(R.id.scheduleEditText)
        
        val continueButton: Button = findViewById(R.id.continueButton)

        // --- CONFIGURACIÓN DE DESPLEGABLE TIPO ---
        val tipos = arrayOf("Pastilla", "Jarabe", "Inyección", "Cápsula", "Gotas", "Pomada")
        val adapterTipo = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tipos)
        typeAutoComplete.setAdapter(adapterTipo)

        // --- CONFIGURACIÓN DE DESPLEGABLE DOSIS ---
        val dosisOptions = arrayOf("1 unidad", "2 unidades", "5 ml", "10 ml", "1 aplicación", "500 mg", "1 gr")
        val adapterDosis = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, dosisOptions)
        doseAutoComplete.setAdapter(adapterDosis)

        // --- CONFIGURACIÓN DE SELECTOR DE HORA (TIMEPICKER) ---
        scheduleEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hora = calendar.get(Calendar.HOUR_OF_DAY)
            val minuto = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val timeFormatted = String.format("%02d:%02d", selectedHour, selectedMinute)
                scheduleEditText.setText(timeFormatted)
            }, hora, minuto, true)
            
            timePickerDialog.show()
        }

        continueButton.setOnClickListener {
            val nombre = nameInputLayout.editText?.text.toString().trim()
            val tipo = typeAutoComplete.text.toString().trim()
            val dosis = doseAutoComplete.text.toString().trim()
            val horario = scheduleEditText.text.toString().trim()

            // Validación
            if (nombre.isNotEmpty() && tipo.isNotEmpty() && dosis.isNotEmpty() && horario.isNotEmpty()) {
                val nuevoMedicamento = Medicamento(nombre, tipo, dosis, horario)
                val categories = intent.getStringArrayListExtra("CATEGORIES") ?: arrayListOf()
                categories.remove("MEDICAMENTO")

                val nextIntent = when {
                    categories.contains("ACTIVIDAD") -> Intent(this, AnadirActividadActivity::class.java)
                    categories.contains("CITA") -> Intent(this, AnadirCitaActivity::class.java)
                    else -> Intent(this, DashboardActivity::class.java)
                }

                nextIntent.putExtra("EXTRA_MEDICAMENTO", nuevoMedicamento)
                nextIntent.putStringArrayListExtra("CATEGORIES", categories)
                startActivity(nextIntent)
                finish()
            } else {
                val errorMsg = getString(R.string.error_campo_requerido)
                nameInputLayout.error = if (nombre.isEmpty()) errorMsg else null
                typeInputLayout.error = if (tipo.isEmpty()) errorMsg else null
                doseInputLayout.error = if (dosis.isEmpty()) errorMsg else null
                scheduleInputLayout.error = if (horario.isEmpty()) errorMsg else null
            }
        }
    }
}
