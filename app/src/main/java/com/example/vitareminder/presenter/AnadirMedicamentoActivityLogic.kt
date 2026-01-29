package com.example.vitareminder.presenter

import com.example.vitareminder.contract.AnadirMedicamentoContract
import com.example.vitareminder.model.Medicamento

class AnadirMedicamentoActivityLogic(
    private var view: AnadirMedicamentoContract.View?
) : AnadirMedicamentoContract.ActivityLogic {

    override fun onContinueClicked(
        nombre: String,
        tipo: String,
        dosis: String,
        horario: String,
        categories: ArrayList<String>
    ) {
        val n = nombre.trim()
        val t = tipo.trim()
        val d = dosis.trim()
        val h = horario.trim()

        val errorMsg = view?.getRequiredFieldError() ?: "Campo requerido"

        val nameError = if (n.isEmpty()) errorMsg else null
        val typeError = if (t.isEmpty()) errorMsg else null
        val doseError = if (d.isEmpty()) errorMsg else null
        val scheduleError = if (h.isEmpty()) errorMsg else null

        if (nameError != null || typeError != null || doseError != null || scheduleError != null) {
            view?.showFieldErrors(nameError, typeError, doseError, scheduleError)
            return
        }

        val nuevoMedicamento = Medicamento(n, t, d, h)

        val cleanCategories = ArrayList(categories)
        cleanCategories.remove("MEDICAMENTO")

        when {
            cleanCategories.contains("ACTIVIDAD") ->
                view?.navigateToActividad(cleanCategories, nuevoMedicamento)

            cleanCategories.contains("CITA") ->
                view?.navigateToCita(cleanCategories, nuevoMedicamento)

            else ->
                view?.navigateToDashboard(cleanCategories, nuevoMedicamento)
        }
    }

    override fun detach() {
        view = null
    }
}
