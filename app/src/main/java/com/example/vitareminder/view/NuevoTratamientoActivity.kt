package com.example.vitareminder.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vitareminder.R
import com.example.vitareminder.contract.NuevoTratamientoContract
import com.example.vitareminder.presenter.NuevoTratamientoActivityLogic
import com.google.android.material.textfield.TextInputLayout

class NuevoTratamientoActivity : AppCompatActivity(), NuevoTratamientoContract.View {

    private lateinit var logic: NuevoTratamientoContract.ActivityLogic

    private lateinit var treatmentNameInputLayout: TextInputLayout
    private lateinit var cbMedicamento: CheckBox
    private lateinit var cbActividad: CheckBox
    private lateinit var cbCitaDr: CheckBox
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nuevo_tratamiento)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        logic = NuevoTratamientoActivityLogic(this)

        treatmentNameInputLayout = findViewById(R.id.treatmentNameInputLayout)
        cbMedicamento = findViewById(R.id.cbMedicamento)
        cbActividad = findViewById(R.id.cbActividad)
        cbCitaDr = findViewById(R.id.cbCitaDr)
        continueButton = findViewById(R.id.continueButton)

        continueButton.setOnClickListener {
            logic.onContinueClicked(
                treatmentName = treatmentNameInputLayout.editText?.text?.toString().orEmpty(),
                medicamentoChecked = cbMedicamento.isChecked,
                actividadChecked = cbActividad.isChecked,
                citaChecked = cbCitaDr.isChecked
            )
        }
    }

    override fun getErrorTratamientoVacio(): String = getString(R.string.error_tratamiento_vacio)

    override fun getErrorSeleccionCategoria(): String = getString(R.string.error_seleccion_categoria)

    override fun showTreatmentNameError(error: String?) {
        treatmentNameInputLayout.error = error
    }

    override fun showCategorySelectionErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToAnadirMedicamento(treatmentName: String, categories: ArrayList<String>) {
        val i = Intent(this, AnadirMedicamentoActivity::class.java).apply {
            putExtra("TREATMENT_NAME", treatmentName)
            putStringArrayListExtra("CATEGORIES", categories)
        }
        startActivity(i)
    }

    override fun onDestroy() {
        logic.detach()
        super.onDestroy()
    }
}