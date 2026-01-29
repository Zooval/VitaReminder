package com.example.vitareminder.presenter

import com.example.vitareminder.contract.TreatmentsContract

class TreatmentsActivityLogic(
    private var view: TreatmentsContract.View?
) : TreatmentsContract.ActivityLogic {

    override fun onNuevoTratamientoClicked() {
        view?.navigateToNuevoTratamiento()
    }

    override fun onVolverClicked() {
        view?.closeScreen()
    }

    override fun detach() {
        view = null
    }
}
