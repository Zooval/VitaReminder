package com.example.vitareminder.contract

import com.example.vitareminder.model.Actividad
import com.example.vitareminder.model.Cita
import com.example.vitareminder.model.Medicamento

interface AnadirCitaContract {

    interface View {
        fun getRequiredFieldError(): String

        fun showFieldErrors(
            doctorError: String?,
            dateError: String?,
            timeError: String?
        )

        fun navigateToDashboard(
            medicamento: Medicamento?,
            actividad: Actividad?,
            cita: Cita
        )
    }

    interface ActivityLogic {
        fun onContinueClicked(
            doctor: String,
            fecha: String,
            hora: String,
            medicamento: Medicamento?,
            actividad: Actividad?
        )

        fun detach()
    }
}
