package com.example.vitareminder.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.example.vitareminder.view.MainActivity
import com.example.vitareminder.R
import com.example.vitareminder.view.TreatmentsActivity
import com.example.vitareminder.contract.DashboardContract
import com.example.vitareminder.model.Medicamento
import com.example.vitareminder.presenter.DashboardActivityLogic
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity(), DashboardContract.View {

    private lateinit var auth: FirebaseAuth
    private lateinit var logic: DashboardContract.ActivityLogic

    private lateinit var medicamentosContainer: LinearLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()
        logic = DashboardActivityLogic(this, auth)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        medicamentosContainer = findViewById(R.id.medicamentosContainer)

        // Obtener extra (la View puede leer intent extras)
        val medicamento = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_MEDICAMENTO", Medicamento::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Medicamento>("EXTRA_MEDICAMENTO")
        }

        // Delegar al logic
        logic.onStart(medicamento)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_today

        bottomNavigationView.setOnItemSelectedListener { item ->
            logic.onBottomNavSelected(item.itemId)
        }
    }

    override fun showMedicamento(medicamento: Medicamento) {
        val itemView = LayoutInflater.from(this)
            .inflate(R.layout.item_medicamento, medicamentosContainer, false)

        val tvNombre: TextView = itemView.findViewById(R.id.tvMedicamentoNombre)
        val tvDosis: TextView = itemView.findViewById(R.id.tvMedicamentoDosis)
        val tvHorario: TextView = itemView.findViewById(R.id.tvMedicamentoHorario)

        tvNombre.text = medicamento.nombre
        tvDosis.text = "${medicamento.tipo}, ${medicamento.dosis}"
        tvHorario.text = medicamento.horario

        medicamentosContainer.addView(itemView)
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToTreatments() {
        val i = Intent(applicationContext, TreatmentsActivity::class.java)
        startActivity(i)
        overridePendingTransition(0, 0)
    }

    override fun navigateToMainAndClearTask() {
        val i = Intent(this, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

    override fun getMsgEnDesarrollo(): String = getString(R.string.msj_en_desarrollo)

    override fun getEditarPerfilMsg(): String = getString(R.string.editar_perfil)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                val anchor = findViewById<View>(R.id.action_profile)
                mostrarMenuPerfil(anchor)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun mostrarMenuPerfil(anchor: View) {
        val popup = PopupMenu(this, anchor)
        popup.menuInflater.inflate(R.menu.menu_perfil, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_edit_profile -> {
                    logic.onProfileMenuEditProfile()
                    true
                }
                R.id.action_logout -> {
                    logic.onProfileMenuLogout()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    override fun onDestroy() {
        logic.detach()
        super.onDestroy()
    }
}