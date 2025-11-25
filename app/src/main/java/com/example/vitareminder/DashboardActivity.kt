package com.example.vitareminder

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- INICIO DE LA LÓGICA ---
        val medicamentosContainer: LinearLayout = findViewById(R.id.medicamentosContainer)

        // 1. Recibir el objeto Medicamento del Intent
        val medicamento = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("EXTRA_MEDICAMENTO", Medicamento::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Medicamento>("EXTRA_MEDICAMENTO")
        }

        // 2. Si el medicamento no es nulo, inflar la vista y mostrar los datos
        medicamento?.let { med ->
            val itemView = LayoutInflater.from(this).inflate(R.layout.item_medicamento, medicamentosContainer, false)

            val tvNombre: TextView = itemView.findViewById(R.id.tvMedicamentoNombre)
            val tvDosis: TextView = itemView.findViewById(R.id.tvMedicamentoDosis)
            val tvHorario: TextView = itemView.findViewById(R.id.tvMedicamentoHorario)

            tvNombre.text = med.nombre
            // Combinamos tipo y dosis para mostrarlo en el subtítulo
            tvDosis.text = "${med.tipo}, ${med.dosis}"
            tvHorario.text = med.horario

            medicamentosContainer.addView(itemView)
        }
    }
}
