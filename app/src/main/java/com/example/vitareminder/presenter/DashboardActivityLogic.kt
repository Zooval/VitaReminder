package com.example.vitareminder.presenter

import com.example.vitareminder.contract.DashboardContract
import com.example.vitareminder.model.Actividad
import com.example.vitareminder.model.Cita
import com.example.vitareminder.model.Medicamento
import com.example.vitareminder.R
import com.google.firebase.auth.FirebaseAuth

class DashboardActivityLogic(
    private var view: DashboardContract.View?,
    private val auth: FirebaseAuth
) : DashboardContract.ActivityLogic {

    override fun onStart(medicamento: Medicamento?, actividad: Actividad?, cita: Cita?) {
        medicamento?.let { view?.showMedicamento(it) }
        actividad?.let { view?.showActividad(it) }
        cita?.let { view?.showCita(it) }
    }

    override fun onBottomNavSelected(itemId: Int): Boolean {
        return when (itemId) {
            R.id.navigation_treatments -> {
                view?.navigateToTreatments()
                true
            }
            R.id.navigation_today -> true
            else -> {
                view?.showToast(view?.getMsgEnDesarrollo().orEmpty())
                false
            }
        }
    }

    override fun onProfileMenuEditProfile() {
        view?.showToast(view?.getEditarPerfilMsg().orEmpty())
    }

    override fun onProfileMenuLogout() {
        auth.signOut()
        view?.navigateToMainAndClearTask()
    }

    override fun detach() {
        view = null
    }
}
