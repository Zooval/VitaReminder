package com.example.vitareminder.contract

interface NuevoTratamientoContract {

    interface View {
        fun getErrorTratamientoVacio(): String
        fun getErrorSeleccionCategoria(): String

        fun showTreatmentNameError(error: String?)
        fun showCategorySelectionErrorToast(message: String)

        fun navigateToAnadirMedicamento(
            treatmentName: String,
            categories: ArrayList<String>
        )
    }

    interface ActivityLogic {
        fun onContinueClicked(
            treatmentName: String,
            medicamentoChecked: Boolean,
            actividadChecked: Boolean,
            citaChecked: Boolean
        )

        fun detach()
    }
}
