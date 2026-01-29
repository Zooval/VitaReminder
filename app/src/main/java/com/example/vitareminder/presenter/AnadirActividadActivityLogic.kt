package com.example.vitareminder.presenter

import com.example.vitareminder.contract.AnadirActividadContract
import com.example.vitareminder.model.Actividad
import com.example.vitareminder.model.Medicamento

class AnadirActividadActivityLogic(
    private var view: AnadirActividadContract.View?
) : AnadirActividadContract.ActivityLogic {

    override fun onContinueClicked(
        nombre: String,
        duracion: String,
        frecuencia: String,
        categories: ArrayList<String>,
        medicamento: Medicamento?
    ) {
        val n = nombre.trim()
        val d = duracion.trim()
        val f = frecuencia.trim()

        val errorMsg = view?.getRequiredFieldError() ?: "Campo requerido"

        val nameError = if (n.isEmpty()) errorMsg else null
        val durationError = if (d.isEmpty()) errorMsg else null
        val frequencyError = if (f.isEmpty()) errorMsg else null

        if (nameError != null || durationError != null || frequencyError != null) {
            view?.showFieldErrors(nameError, durationError, frequencyError)
            return
        }

        val actividad = Actividad(n, d, f)

        val cleanCategories = ArrayList(categories)
        cleanCategories.remove("ACTIVIDAD")

        if (cleanCategories.contains("CITA")) {
            view?.navigateToCita(cleanCategories, medicamento, actividad)
        } else {
            view?.navigateToDashboard(cleanCategories, medicamento, actividad)
        }
    }

    override fun detach() {
        view = null
    }
}
