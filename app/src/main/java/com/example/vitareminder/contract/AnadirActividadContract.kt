package com.example.vitareminder.contract

import com.example.vitareminder.model.Actividad
import com.example.vitareminder.model.Medicamento

interface AnadirActividadContract {

    interface View {
        fun getRequiredFieldError(): String

        fun showFieldErrors(
            nameError: String?,
            durationError: String?,
            frequencyError: String?
        )

        fun navigateToCita(
            categories: ArrayList<String>,
            medicamento: Medicamento?,
            actividad: Actividad
        )

        fun navigateToDashboard(
            categories: ArrayList<String>,
            medicamento: Medicamento?,
            actividad: Actividad
        )
    }

    // Esto es tu "Presenter" pero lo llamas Activity (como pediste)
    interface ActivityLogic {
        fun onContinueClicked(
            nombre: String,
            duracion: String,
            frecuencia: String,
            categories: ArrayList<String>,
            medicamento: Medicamento?
        )

        fun detach()
    }
}
