package com.example.vitareminder.contract

import com.example.vitareminder.model.Medicamento

interface AnadirMedicamentoContract {

    interface View {
        fun getRequiredFieldError(): String

        fun showFieldErrors(
            nameError: String?,
            typeError: String?,
            doseError: String?,
            scheduleError: String?
        )

        fun navigateToActividad(
            categories: ArrayList<String>,
            medicamento: Medicamento
        )

        fun navigateToCita(
            categories: ArrayList<String>,
            medicamento: Medicamento
        )

        fun navigateToDashboard(
            categories: ArrayList<String>,
            medicamento: Medicamento
        )
    }

    interface ActivityLogic {
        fun onContinueClicked(
            nombre: String,
            tipo: String,
            dosis: String,
            horario: String,
            categories: ArrayList<String>
        )

        fun detach()
    }
}
