package com.dan.librocuentaskt.librocuentas.ui.ingresos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dan.librocuentaskt.R
import com.dan.librocuentaskt.librocuentas.model.Ingreso
import com.dan.librocuentaskt.librocuentas.viewmodel.IngresoViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DetalleIngresoActivity: AppCompatActivity()  {

    private lateinit var viewmodel: IngresoViewModel
    private lateinit var ingresoModel: Ingreso

    private lateinit var ll_tipoIngreso: LinearLayout
    private lateinit var ll_tipoAlquiler: LinearLayout
    private lateinit var ll_tipoCasa: LinearLayout
    private lateinit var ll_tipoLote: LinearLayout
    private lateinit var ll_tipoVivienda: LinearLayout
    private lateinit var ll_mtoIngreso: LinearLayout
    private lateinit var ll_fecIngreso: LinearLayout
    private lateinit var ll_descripcion: LinearLayout
    private lateinit var tv_tipoIngreso: TextView
    private lateinit var tv_tipoAlquiler: TextView
    private lateinit var tv_tipoCasa: TextView
    private lateinit var tv_tipoLote: TextView
    private lateinit var tv_tipoVivienda: TextView
    private lateinit var tv_mtoIngreso: TextView
    private lateinit var tv_fecIngreso: TextView
    private lateinit var tv_descripcion: TextView
    private lateinit var btn_delete: MaterialButton
    private lateinit var btn_edit: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        ingresoModel = intent.getSerializableExtra("ingresoModel") as Ingreso

        setContentView(R.layout.detalle_ingreso_layout)

        viewmodel = ViewModelProvider(this).get(IngresoViewModel::class.java)

        initView()
        initEvent()
    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)


    }


    private fun initView() {
        btn_edit = findViewById<MaterialButton>(R.id.btn_edit)
        btn_delete = findViewById<MaterialButton>(R.id.btn_delete)

        ll_tipoAlquiler = findViewById<LinearLayout>(R.id.ll_tipoAlquiler)
        ll_tipoCasa = findViewById<LinearLayout>(R.id.ll_tipoCasa)
        ll_tipoLote = findViewById<LinearLayout>(R.id.ll_tipoLote)
        ll_tipoVivienda = findViewById<LinearLayout>(R.id.ll_tipoVivienda)

        tv_tipoIngreso = findViewById<TextView>(R.id.tv_tipoIngreso)
        tv_tipoAlquiler = findViewById<TextView>(R.id.tv_tipoAlquiler)
        tv_tipoCasa = findViewById<TextView>(R.id.tv_tipoCasa)
        tv_tipoLote = findViewById<TextView>(R.id.tv_tipoLote)
        tv_tipoVivienda = findViewById<TextView>(R.id.tv_tipoVivienda)
        tv_fecIngreso = findViewById<TextView>(R.id.tv_fecIngreso)
        tv_mtoIngreso = findViewById<TextView>(R.id.tv_mtoIngreso)
        tv_descripcion = findViewById<TextView>(R.id.tv_descripcion)


    }

    private fun initEvent() {


  }




}