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
import com.example.vitareminder.contract.AnadirCitaContract
import com.example.vitareminder.model.Actividad
import com.example.vitareminder.model.Cita
import com.example.vitareminder.model.Medicamento
import com.example.vitareminder.presenter.AnadirCitaActivityLogic
import com.google.android.material.textfield.TextInputLayout

class AnadirCitaActivity : AppCompatActivity(), AnadirCitaContract.View {

    private lateinit var logic: AnadirCitaContract.ActivityLogic

    private lateinit var doctorInputLayout: TextInputLayout
    private lateinit var dateInputLayout: TextInputLayout
    private lateinit var timeInputLayout: TextInputLayout
    private lateinit var continueButton: Button

    private var medicamento: Medicamento? = null
    private var actividad: Actividad? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anadir_cita)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        logic = AnadirCitaActivityLogic(this)

        doctorInputLayout = findViewById(R.id.doctorInputLayout)
        dateInputLayout = findViewById(R.id.dateInputLayout)
        timeInputLayout = findViewById(R.id.timeInputLayout)
        continueButton = findViewById(R.id.continueButton)

        // Recuperar datos previos
        medicamento = intent.getParcelableExtra("EXTRA_MEDICAMENTO")
        actividad = intent.getParcelableExtra("EXTRA_ACTIVIDAD")

        continueButton.setOnClickListener {
            logic.onContinueClicked(
                doctor = doctorInputLayout.editText?.text?.toString().orEmpty(),
                fecha = dateInputLayout.editText?.text?.toString().orEmpty(),
                hora = timeInputLayout.editText?.text?.toString().orEmpty(),
                medicamento = medicamento,
                actividad = actividad
            )
        }
    }

    override fun getRequiredFieldError(): String {
        return getString(R.string.error_campo_requerido)
    }

    override fun showFieldErrors(doctorError: String?, dateError: String?, timeError: String?) {
        doctorInputLayout.error = doctorError
        dateInputLayout.error = dateError
        timeInputLayout.error = timeError
    }

    override fun navigateToDashboard(medicamento: Medicamento?, actividad: Actividad?, cita: Cita) {
        val dashboardIntent = Intent(this, DashboardActivity::class.java).apply {
            putExtra("EXTRA_MEDICAMENTO", medicamento)
            putExtra("EXTRA_ACTIVIDAD", actividad)
            putExtra("EXTRA_CITA", cita)
        }
        startActivity(dashboardIntent)
        finish()
    }

    override fun onDestroy() {
        logic.detach()
        super.onDestroy()
    }
}