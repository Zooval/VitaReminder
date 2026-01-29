package com.example.vitareminder.contract

interface TreatmentsContract {

    interface View {
        fun navigateToNuevoTratamiento()
        fun closeScreen()
    }

    interface ActivityLogic {
        fun onNuevoTratamientoClicked()
        fun onVolverClicked()
        fun detach()
    }
}
