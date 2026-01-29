package com.example.vitareminder.contract

import com.example.vitareminder.model.Medicamento

interface DashboardContract {

    interface View {
        fun showMedicamento(medicamento: Medicamento)

        fun showToast(message: String)

        fun navigateToTreatments()
        fun navigateToMainAndClearTask()

        fun getMsgEnDesarrollo(): String
        fun getEditarPerfilMsg(): String
    }

    interface ActivityLogic {
        fun onStart(medicamento: Medicamento?)
        fun onBottomNavSelected(itemId: Int): Boolean

        fun onProfileMenuEditProfile()
        fun onProfileMenuLogout()

        fun detach()
    }
}
