package com.example.vitareminder.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vitareminder.R
import com.example.vitareminder.contract.TreatmentsContract
import com.example.vitareminder.presenter.TreatmentsActivityLogic

class TreatmentsActivity : AppCompatActivity(), TreatmentsContract.View {

    private lateinit var logic: TreatmentsContract.ActivityLogic

    private lateinit var btnNuevoTratamiento: Button
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_treatments)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        logic = TreatmentsActivityLogic(this)

        btnNuevoTratamiento = findViewById(R.id.btnNuevoTratamiento)
        btnVolver = findViewById(R.id.btnVolver)

        btnNuevoTratamiento.setOnClickListener { logic.onNuevoTratamientoClicked() }
        btnVolver.setOnClickListener { logic.onVolverClicked() }
    }

    override fun navigateToNuevoTratamiento() {
        startActivity(Intent(this, NuevoTratamientoActivity::class.java))
    }

    override fun closeScreen() {
        finish()
    }

    override fun onDestroy() {
        logic.detach()
        super.onDestroy()
    }
}