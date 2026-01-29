package com.example.vitareminder.presenter

import com.example.vitareminder.contract.NuevoTratamientoContract

class NuevoTratamientoActivityLogic(
    private var view: NuevoTratamientoContract.View?
) : NuevoTratamientoContract.ActivityLogic {

    override fun onContinueClicked(
        treatmentName: String,
        medicamentoChecked: Boolean,
        actividadChecked: Boolean,
        citaChecked: Boolean
    ) {
        val name = treatmentName.trim()
        val selectedCategories = ArrayList<String>()

        if (medicamentoChecked) selectedCategories.add("MEDICAMENTO")
        if (actividadChecked) selectedCategories.add("ACTIVIDAD")
        if (citaChecked) selectedCategories.add("CITA")

        var valid = true

        if (name.isBlank()) {
            view?.showTreatmentNameError(view?.getErrorTratamientoVacio())
            valid = false
        } else {
            view?.showTreatmentNameError(null)
        }

        if (selectedCategories.isEmpty()) {
            view?.showCategorySelectionErrorToast(view?.getErrorSeleccionCategoria().orEmpty())
            valid = false
        }

        if (!valid) return

        view?.navigateToAnadirMedicamento(name, selectedCategories)
    }

    override fun detach() {
        view = null
    }
}
