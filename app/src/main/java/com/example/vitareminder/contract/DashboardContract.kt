package com.example.vitareminder.contract

import com.example.vitareminder.model.Actividad
import com.example.vitareminder.model.Cita
import com.example.vitareminder.model.Medicamento

interface DashboardContract {

    interface View {
        fun showMedicamento(medicamento: Medicamento)
        fun showActividad(actividad: Actividad)
        fun showCita(cita: Cita)

        fun showToast(message: String)

        fun navigateToTreatments()
        fun navigateToMainAndClearTask()

        fun getMsgEnDesarrollo(): String
        fun getEditarPerfilMsg(): String
    }

    interface ActivityLogic {
        fun onStart(medicamento: Medicamento?, actividad: Actividad?, cita: Cita?)
        fun onBottomNavSelected(itemId: Int): Boolean

        fun onProfileMenuEditProfile()
        fun onProfileMenuLogout()

        fun detach()
    }
}
