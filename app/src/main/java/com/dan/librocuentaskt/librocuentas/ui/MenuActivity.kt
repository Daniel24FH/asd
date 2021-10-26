package com.dan.librocuentaskt.librocuentas.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintSet
import com.dan.librocuentaskt.R
import com.dan.librocuentaskt.librocuentas.ui.gastos.GastosListaActivity
import com.dan.librocuentaskt.librocuentas.ui.ingresos.IngresosListaActivity

class MenuActivity : AppCompatActivity() {
    private lateinit var cv_ingresos: CardView
    private lateinit var cv_gastos: CardView
    private lateinit var custom_toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initView()
    }
    private fun initView() {

        cv_ingresos = findViewById<CardView>(R.id.cv_ingresos)
        cv_gastos = findViewById<CardView>(R.id.cv_gastos)
        custom_toolbar = findViewById<Toolbar>(R.id.custom_toolbar)

        custom_toolbar.setTitle("asdasd")
        custom_toolbar.setTitleTextColor(Color.BLACK)
        cv_ingresos.setOnClickListener {
            val intent = Intent(this, IngresosListaActivity::class.java)
            startActivity(intent)
        }

        cv_gastos.setOnClickListener {
            val intent = Intent(this, GastosListaActivity::class.java)
            startActivity(intent)
        }

    }
}