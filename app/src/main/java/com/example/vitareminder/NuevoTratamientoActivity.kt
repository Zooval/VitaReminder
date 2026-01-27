package com.example.vitareminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class NuevoTratamientoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nuevo_tratamiento)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Obtener referencias a los componentes
        val treatmentNameInputLayout: TextInputLayout = findViewById(R.id.treatmentNameInputLayout)
        val cbMedicamento: CheckBox = findViewById(R.id.cbMedicamento)
        val cbActividad: CheckBox = findViewById(R.id.cbActividad)
        val cbCitaDr: CheckBox = findViewById(R.id.cbCitaDr)
        val continueButton: Button = findViewById(R.id.continueButton)

        // 2. Configurar el listener para el botón "Continuar"
        continueButton.setOnClickListener {
            val treatmentName = treatmentNameInputLayout.editText?.text.toString().trim()

            val selectedCategories = ArrayList<String>()

            if (cbMedicamento.isChecked) selectedCategories.add("MEDICAMENTO")
            if (cbActividad.isChecked) selectedCategories.add("ACTIVIDAD")
            if (cbCitaDr.isChecked) selectedCategories.add("CITA")

            if (treatmentName.isNotBlank() && selectedCategories.isNotEmpty()) {

                val intent = Intent(this, AnadirMedicamentoActivity::class.java)
                intent.putExtra("TREATMENT_NAME", treatmentName)
                intent.putStringArrayListExtra("CATEGORIES", selectedCategories)
                startActivity(intent)

            } else {
                if (treatmentName.isBlank()) {
                    treatmentNameInputLayout.error = "El nombre del tratamiento no puede estar vacío"
                }
                if (selectedCategories.isEmpty()) {
                    Toast.makeText(this, "Selecciona al menos una categoría", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}