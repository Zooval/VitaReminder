package com.example.vitareminder

import android.content.Intent // ¡IMPORTANTE AÑADIR ESTO!
import android.os.Bundle
import android.widget.Button // ¡IMPORTANTE AÑADIR ESTO!import androidx.activity.enableEdgeToEdge
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
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

        // --- INICIO DE LA LÓGICA DE NAVEGACIÓN ---

        // 1. Obtener referencias a los componentes
        val treatmentNameInputLayout: TextInputLayout = findViewById(R.id.treatmentNameInputLayout)
        val btnMedicamento: MaterialButton = findViewById(R.id.btnMedicamento)
        val continueButton: Button = findViewById(R.id.continueButton)

        // 2. Configurar el listener para el botón "Continuar"
        continueButton.setOnClickListener {
            val treatmentName = treatmentNameInputLayout.editText?.text.toString()

            // Validación básica: El nombre no puede estar vacío y el botón de medicamento debe estar seleccionado
            if (treatmentName.isNotBlank() && btnMedicamento.isChecked) {
                // Si la validación pasa, navegamos a la siguiente pantalla
                val intent = Intent(this, AnadirMedicamentoActivity::class.java)
                startActivity(intent)
            } else {
                // Mostramos un error si no se cumplen las condiciones
                if (treatmentName.isBlank()) {
                    treatmentNameInputLayout.error = "El nombre del tratamiento no puede estar vacío"
                } else {
                    treatmentNameInputLayout.error = null // Limpia el error si ya tenía uno
                }

                if (!btnMedicamento.isChecked) {
                    // Puedes mostrar un Toast o Snackbar si no se ha seleccionado ninguna opción
                    android.widget.Toast.makeText(this, "Debes seleccionar 'Medicamento'", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
