package com.example.vitareminder.presenter

import com.example.vitareminder.contract.AnadirCitaContract
import com.example.vitareminder.model.Actividad
import com.example.vitareminder.model.Cita
import com.example.vitareminder.model.Medicamento

class AnadirCitaActivityLogic(
    private var view: AnadirCitaContract.View?
) : AnadirCitaContract.ActivityLogic {

    override fun onContinueClicked(
        doctor: String,
        fecha: String,
        hora: String,
        medicamento: Medicamento?,
        actividad: Actividad?
    ) {
        val d = doctor.trim()
        val f = fecha.trim()
        val h = hora.trim()

        val errorMsg = view?.getRequiredFieldError() ?: "Campo requerido"

        val doctorError = if (d.isEmpty()) errorMsg else null
        val dateError = if (f.isEmpty()) errorMsg else null
        val timeError = if (h.isEmpty()) errorMsg else null

        if (doctorError != null || dateError != null || timeError != null) {
            view?.showFieldErrors(doctorError, dateError, timeError)
            return
        }

        val cita = Cita(d, f, h)
        view?.navigateToDashboard(medicamento, actividad, cita)
    }

    override fun detach() {
        view = null
    }
}
