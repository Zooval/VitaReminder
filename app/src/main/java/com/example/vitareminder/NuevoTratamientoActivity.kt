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
        val cbMediciones: CheckBox = findViewById(R.id.cbMediciones)
        val cbSintomas: CheckBox = findViewById(R.id.cbSintomas)
        val cbCitaDr: CheckBox = findViewById(R.id.cbCitaDr)
        val continueButton: Button = findViewById(R.id.continueButton)

        // 2. Configurar el listener para el botón "Continuar"
        continueButton.setOnClickListener {
            val treatmentName = treatmentNameInputLayout.editText?.text.toString().trim()

            // Verificar si al menos una categoría está seleccionada
            val isAnyCategorySelected = cbMedicamento.isChecked || cbActividad.isChecked ||
                    cbMediciones.isChecked || cbSintomas.isChecked ||
                    cbCitaDr.isChecked

            // Validación básica
            if (treatmentName.isNotBlank() && isAnyCategorySelected) {

                // Si seleccionó medicamento, vamos a esa pantalla
                if (cbMedicamento.isChecked) {
                    val intent = Intent(this, AnadirMedicamentoActivity::class.java)
                    // Podrías pasar el nombre del tratamiento a la siguiente pantalla
                    intent.putExtra("TREATMENT_NAME", treatmentName)
                    startActivity(intent)
                } else {
                    // Si seleccionó otras cosas pero no medicamento, por ahora mostramos un mensaje
                    Toast.makeText(this, "Funcionalidad para otras categorías en desarrollo", Toast.LENGTH_SHORT).show()
                }

            } else {
                // Manejo de errores visuales
                if (treatmentName.isBlank()) {
                    treatmentNameInputLayout.error = "El nombre del tratamiento no puede estar vacío"
                } else {
                    treatmentNameInputLayout.error = null
                }

                if (!isAnyCategorySelected) {
                    Toast.makeText(this, "Por favor, selecciona al menos una categoría", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}