package com.example.vitareminder.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vitareminder.view.AnadirCitaActivity
import com.example.vitareminder.view.DashboardActivity
import com.example.vitareminder.R
import com.example.vitareminder.contract.AnadirActividadContract
import com.example.vitareminder.model.Actividad
import com.example.vitareminder.model.Medicamento
import com.example.vitareminder.presenter.AnadirActividadActivityLogic
import com.google.android.material.textfield.TextInputLayout

class AnadirActividadActivity : AppCompatActivity(), AnadirActividadContract.View {

    private lateinit var logic: AnadirActividadContract.ActivityLogic

    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var durationInputLayout: TextInputLayout
    private lateinit var frequencyInputLayout: TextInputLayout
    private lateinit var continueButton: Button
    
    private lateinit var durationAutoComplete: AutoCompleteTextView
    private lateinit var frequencyAutoComplete: AutoCompleteTextView

    private var categories: ArrayList<String> = arrayListOf()
    private var medicamento: Medicamento? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anadir_actividad)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar lógica (presenter)
        logic = AnadirActividadActivityLogic(this)

        // Bind UI
        nameInputLayout = findViewById(R.id.activityNameInputLayout)
        durationInputLayout = findViewById(R.id.activityDurationInputLayout)
        frequencyInputLayout = findViewById(R.id.activityFrequencyInputLayout)
        
        durationAutoComplete = findViewById(R.id.durationAutoComplete)
        frequencyAutoComplete = findViewById(R.id.frequencyAutoComplete)
        
        continueButton = findViewById(R.id.continueButton)

        // --- CONFIGURACIÓN DE DESPLEGABLES ---
        val duraciones = arrayOf("15 min", "30 min", "45 min", "1 hora", "1.5 horas", "2 horas")
        val adapterDuracion = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, duraciones)
        durationAutoComplete.setAdapter(adapterDuracion)

        val frecuencias = arrayOf("Diariamente", "3 veces por semana", "Fines de semana", "Cada 2 días")
        val adapterFrecuencia = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, frecuencias)
        frequencyAutoComplete.setAdapter(adapterFrecuencia)

        // Recuperar datos previos
        categories = intent.getStringArrayListExtra("CATEGORIES") ?: arrayListOf()
        medicamento = intent.getParcelableExtra("EXTRA_MEDICAMENTO")

        continueButton.setOnClickListener {
            logic.onContinueClicked(
                nombre = nameInputLayout.editText?.text?.toString().orEmpty(),
                duracion = durationAutoComplete.text.toString(),
                frecuencia = frequencyAutoComplete.text.toString(),
                categories = categories,
                medicamento = medicamento
            )
        }
    }

    override fun getRequiredFieldError(): String {
        return getString(R.string.error_campo_requerido)
    }

    override fun showFieldErrors(nameError: String?, durationError: String?, frequencyError: String?) {
        nameInputLayout.error = nameError
        durationInputLayout.error = durationError
        frequencyInputLayout.error = frequencyError
    }

    override fun navigateToCita(
        categories: ArrayList<String>,
        medicamento: Medicamento?,
        actividad: Actividad
    ) {
        val nextIntent = Intent(this, AnadirCitaActivity::class.java).apply {
            putExtra("EXTRA_MEDICAMENTO", medicamento)
            putExtra("EXTRA_ACTIVIDAD", actividad)
            putStringArrayListExtra("CATEGORIES", categories)
        }
        startActivity(nextIntent)
        finish()
    }


    override fun navigateToDashboard(
        categories: ArrayList<String>,
        medicamento: Medicamento?,
        actividad: Actividad
    ) {
        val nextIntent = Intent(this, DashboardActivity::class.java).apply {
            putExtra("EXTRA_MEDICAMENTO", medicamento)
            putExtra("EXTRA_ACTIVIDAD", actividad)
            putStringArrayListExtra("CATEGORIES", categories)
        }
        startActivity(nextIntent)
        finish()
    }

    override fun onDestroy() {
        logic.detach()
        super.onDestroy()
    }
}
