package com.example.vitareminder

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // Puedes comentar o eliminar esto si causa problemas con la BottomNavigationView
        setContentView(R.layout.activity_dashboard)

        /*
        // Este bloque ajusta los paddings para la pantalla completa (edge-to-edge).
        // A veces puede interferir con la posición de la BottomNavigationView.
        // Si la barra de navegación se ve mal, puedes probar comentando este bloque.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */

        // --- INICIO DE LA LÓGICA EXISTENTE ---
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

        // --- INICIO DE LA LÓGICA DE LA BARRA DE NAVEGACIÓN ---

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Marcar el ítem "Para hoy" como seleccionado por defecto al iniciar la pantalla
        bottomNavigationView.selectedItemId = R.id.navigation_today

        // Configurar el listener para manejar los clics en los ítems del menú
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_treatments -> {
                    // Acción para "Tratamientos": Iniciar la Activity de Tratamientos
                    val intent = Intent(applicationContext, TreatmentsActivity::class.java)
                    startActivity(intent)
                    // Anulamos la animación de transición para que el cambio parezca más fluido
                    overridePendingTransition(0, 0)
                    true // Devuelve true para indicar que el evento fue manejado
                }
                R.id.navigation_today -> {
                    // Ya estamos en esta pantalla, no es necesario hacer nada.
                    // Devuelve true para mantener el ítem seleccionado.
                    true
                }
                else -> {
                    // Para los otros botones ("Progreso", "Historial"), mostrar un mensaje temporal
                    Toast.makeText(this, "Función no implementada", Toast.LENGTH_SHORT).show()
                    // Devuelve false para que el ítem no se seleccione al hacer clic
                    false
                }
            }
        }
    }
}
