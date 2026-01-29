package com.example.vitareminder.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class AnadirCitaActivity : AppCompatActivity(), AnadirCitaContract.View {

    private lateinit var logic: AnadirCitaContract.ActivityLogic

    private lateinit var doctorInputLayout: TextInputLayout
    private lateinit var dateInputLayout: TextInputLayout
    private lateinit var timeInputLayout: TextInputLayout
    
    private lateinit var dateEditText: TextInputEditText
    private lateinit var timeEditText: TextInputEditText
    
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
        
        dateEditText = findViewById(R.id.dateEditText)
        timeEditText = findViewById(R.id.timeEditText)
        
        continueButton = findViewById(R.id.continueButton)

        // --- SELECTOR DE FECHA ---
        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val dateFormatted = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                dateEditText.setText(dateFormatted)
            }, year, month, day)
            datePickerDialog.show()
        }

        // --- SELECTOR DE HORA ---
        timeEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val timeFormatted = String.format("%02d:%02d", selectedHour, selectedMinute)
                timeEditText.setText(timeFormatted)
            }, hour, minute, true)
            timePickerDialog.show()
        }

        // Recuperar datos previos
        medicamento = intent.getParcelableExtra("EXTRA_MEDICAMENTO")
        actividad = intent.getParcelableExtra("EXTRA_ACTIVIDAD")

        continueButton.setOnClickListener {
            logic.onContinueClicked(
                doctor = doctorInputLayout.editText?.text?.toString().orEmpty(),
                fecha = dateEditText.text.toString(),
                hora = timeEditText.text.toString(),
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
