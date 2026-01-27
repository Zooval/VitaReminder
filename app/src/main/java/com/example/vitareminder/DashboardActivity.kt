package com.example.vitareminder

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()

        // --- LÓGICA DE MEDICAMENTOS ---
        val medicamentosContainer: LinearLayout = findViewById(R.id.medicamentosContainer)

        val medicamento = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_MEDICAMENTO", Medicamento::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Medicamento>("EXTRA_MEDICAMENTO")
        }

        medicamento?.let { med ->
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_medicamento, medicamentosContainer, false)
            val tvNombre: TextView = itemView.findViewById(R.id.tvMedicamentoNombre)
            val tvDosis: TextView = itemView.findViewById(R.id.tvMedicamentoDosis)
            val tvHorario: TextView = itemView.findViewById(R.id.tvMedicamentoHorario)

            tvNombre.text = med.nombre
            tvDosis.text = "${med.tipo}, ${med.dosis}"
            tvHorario.text = med.horario

            medicamentosContainer.addView(itemView)
        }

        val actividadesContainer: LinearLayout = findViewById(R.id.actividadesContainer)

        val actividad = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_ACTIVIDAD", Actividad::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Actividad>("EXTRA_ACTIVIDAD")
        }

        actividad?.let { act ->
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_actividad, actividadesContainer, false)

            itemView.findViewById<TextView>(R.id.tvActividadNombre).text = act.nombre
            itemView.findViewById<TextView>(R.id.tvActividadDetalle)
                .text = "${act.duracion} · ${act.frecuencia}"

            actividadesContainer.addView(itemView)
        }

        // --- LÓGICA DE CITAS ---
        val citasContainer: LinearLayout = findViewById(R.id.citasContainer)

        val cita = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_CITA", Cita::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Cita>("EXTRA_CITA")
        }

        cita?.let { c ->
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_cita, citasContainer, false)

            itemView.findViewById<TextView>(R.id.tvCitaDoctor).text = c.doctor
            itemView.findViewById<TextView>(R.id.tvCitaFecha).text = c.fecha
            itemView.findViewById<TextView>(R.id.tvCitaHora).text = c.hora

            citasContainer.addView(itemView)
        }



        // --- BARRA DE NAVEGACIÓN Y CERRAR SESIÓN ---
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_today

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_treatments -> {
                    val intent = Intent(applicationContext, TreatmentsActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.navigation_today -> true
                R.id.navigation_logout -> {
                    cerrarSesion()
                    true
                }
                else -> {
                    Toast.makeText(this, "Función no implementada", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
    }

    private fun cerrarSesion() {
        auth.signOut() // Cierra la sesión en Firebase
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}