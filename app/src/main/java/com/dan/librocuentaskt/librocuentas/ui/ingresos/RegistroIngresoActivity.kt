package com.dan.librocuentaskt.librocuentas.ui.ingresos

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.dan.librocuentaskt.R
import com.dan.librocuentaskt.librocuentas.model.*
import com.dan.librocuentaskt.librocuentas.viewmodel.IngresoViewModel
import com.dan.librocuentaskt.utils.DatePickerFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RegistroIngresoActivity : AppCompatActivity() {
    

    private lateinit var btn_Guardar: MaterialButton
    private lateinit var actv_tipoIngreso: AutoCompleteTextView
    private lateinit var actv_tipoAlquiler: AutoCompleteTextView
    private lateinit var actv_tipoCasaAlquiler: AutoCompleteTextView
    private lateinit var actv_tipoLote: AutoCompleteTextView
    private lateinit var actv_tipoViviendaAlq: AutoCompleteTextView
    private lateinit var et_mtoIngreso: TextInputEditText
    private lateinit var et_fecIngreso: TextInputEditText
    private lateinit var et_descripcion: TextInputEditText
    private lateinit var til_tipoIngreso: TextInputLayout
    private lateinit var til_tipoAlquiler: TextInputLayout
    private lateinit var til_tipoCasaAlquiler: TextInputLayout
    private lateinit var til_tipoLote: TextInputLayout
    private lateinit var til_tipoViviendaAlq: TextInputLayout
    private lateinit var til_mtoIngreso: TextInputLayout
    private lateinit var til_fecIngreso: TextInputLayout
    private lateinit var til_descripcion: TextInputLayout
    private lateinit var tipoIngresoArrayList: ArrayList<TipoIngreso>
    private lateinit var tipoAlquilerArrayList: ArrayList<TipoAlquiler>
    private lateinit var tipoCasaAlqArrayList: ArrayList<CasaAlquiler>
    private lateinit var tipoLoteArrayList: ArrayList<Lote>
    private lateinit var tipoViviendaAlqArrayList: ArrayList<ViviendaAlquiler>
    private lateinit var ll_tipoCasaLote: LinearLayout
    private lateinit var ll_tipoAlquiler: LinearLayout
    private lateinit var ll_tipoViviendaAlq: LinearLayout

    private lateinit var viewmodel: IngresoViewModel

    private var indexTipoIngreso = 0
    private var indexTipoAlquiler = 0
    private var indexTipoCasaAlquiler = 0
    private var indexTipoLote = 0
    private var indexTipoViviendaAlq = 0

    private var isValido = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_ingreso_activity)

        viewmodel = ViewModelProvider(this).get(IngresoViewModel::class.java)

        initView()
        initEvent()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onResume() {
        super.onResume()
    }


    private fun initView() {
        btn_Guardar = findViewById<MaterialButton>(R.id.btn_guardar)

        actv_tipoIngreso = findViewById<AutoCompleteTextView>(R.id.actv_tipoIngreso)
        actv_tipoAlquiler = findViewById<AutoCompleteTextView>(R.id.actv_tipoAlquiler)
        actv_tipoCasaAlquiler = findViewById<AutoCompleteTextView>(R.id.actv_tipoCasaAlquiler)
        actv_tipoLote = findViewById<AutoCompleteTextView>(R.id.actv_tipoLote)
        actv_tipoViviendaAlq = findViewById<AutoCompleteTextView>(R.id.actv_tipoViviendaAlq)
        et_mtoIngreso = findViewById<TextInputEditText>(R.id.et_mtoIngreso)
        et_fecIngreso = findViewById<TextInputEditText>(R.id.et_fecIngreso)
        et_descripcion = findViewById<TextInputEditText>(R.id.et_descripcion)
        til_tipoIngreso = findViewById<TextInputLayout>(R.id.til_tipoIngreso)
        til_tipoAlquiler = findViewById<TextInputLayout>(R.id.til_tipoAlquiler)
        til_tipoCasaAlquiler = findViewById<TextInputLayout>(R.id.til_tipoCasaAlquiler)
        til_tipoLote = findViewById<TextInputLayout>(R.id.til_tipoLote)
        til_tipoViviendaAlq = findViewById<TextInputLayout>(R.id.til_tipoViviendaAlq)
        til_mtoIngreso = findViewById<TextInputLayout>(R.id.til_mtoIngreso)
        til_fecIngreso = findViewById<TextInputLayout>(R.id.til_fecIngreso)
        til_descripcion = findViewById<TextInputLayout>(R.id.til_descripcion)
        ll_tipoAlquiler = findViewById<LinearLayout>(R.id.ll_tipoAlquiler)
        ll_tipoCasaLote = findViewById<LinearLayout>(R.id.ll_tipoCasaLote)
        ll_tipoViviendaAlq = findViewById<LinearLayout>(R.id.ll_tipoViviendaAlq)

    }

    private fun initEvent() {

        var codTipoIng: Int = 0
        var codTipoAlq: Int = 0
        var codCasaAlq: String = ""
        var codLt: String = ""
        var codViviendaAlq: String = ""

        cargarSpinnerTipoIngreso()
        cargarSpinnerTipoAlquiler()
        cargarSpinnerTipoCasaAlquiler()
        cargarSpinnerTipoLote(null)
        cargarSpinnerViviendaAlquiler(null)

        btn_Guardar.setOnClickListener {

            isValido=true;
            validarTipoIngreso()
            validarTipoAlquiler()
            validarTipoCasaAlquiler()
            validarTipoLote()
            validarTipoVivienda()
            validarFecha()
            validarMonto()
            validarDescripcion()
           if (isValido) {
              guardarIngreso()
            }
        }
        et_fecIngreso.setOnClickListener {
            showDatePickerDialog()
        til_fecIngreso.setError(null)
        }

        actv_tipoIngreso.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                til_tipoIngreso.setError(null)
                actv_tipoCasaAlquiler.setText(null)
                actv_tipoLote.setText(null)
                actv_tipoViviendaAlq.setText(null)
                indexTipoIngreso = position

                if (tipoIngresoArrayList != null && !tipoIngresoArrayList.isEmpty()) {
                    codTipoIng = tipoIngresoArrayList.get(indexTipoIngreso).codTipoIngreso.toString().toInt()

                }
                if (codTipoIng == 1) {
                    ll_tipoAlquiler.isVisible = true
                } else {
                    ll_tipoAlquiler.isGone = true
                    ll_tipoCasaLote.isGone = true
                    ll_tipoViviendaAlq.isGone = true
                }

            }
        actv_tipoAlquiler.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                til_tipoAlquiler.setError(null)
                indexTipoAlquiler = position
                actv_tipoLote.setText(null)
                actv_tipoViviendaAlq.setText(null)
                actv_tipoCasaAlquiler.setText(null)

                if (tipoAlquilerArrayList != null && !tipoAlquilerArrayList.isEmpty()) {
                    codTipoAlq =
                        tipoAlquilerArrayList.get(indexTipoAlquiler).codTipoAlquiler.toString()
                            .toInt()
                }
                if (codTipoAlq != 0) {
                    if (codTipoAlq == 1) {
                        ll_tipoCasaLote.isVisible = true
                        ll_tipoViviendaAlq.isVisible = true

                    } else {
                        ll_tipoCasaLote.isGone = true
                        ll_tipoViviendaAlq.isGone = true
                    }
                }
            }
        actv_tipoCasaAlquiler.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                til_tipoCasaAlquiler.setError(null)

                indexTipoCasaAlquiler = position
                if (tipoCasaAlqArrayList != null && !tipoCasaAlqArrayList.isEmpty()) {
                    codCasaAlq =
                        tipoCasaAlqArrayList.get(indexTipoCasaAlquiler).codCasaAlquiler.toString()
                }
                if (codCasaAlq != null && !codCasaAlq.isEmpty()) {
                    actv_tipoLote.setText(null)
                    actv_tipoViviendaAlq.setText(null)
                    cargarSpinnerTipoLote(codCasaAlq)

                }
            }
        actv_tipoLote.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                til_tipoLote.setError(null)

                indexTipoLote = position
                if (tipoLoteArrayList != null && !tipoLoteArrayList.isEmpty()) {
                    codLt = tipoLoteArrayList.get(indexTipoLote).codLote.toString().trim()
                    actv_tipoViviendaAlq.setText(null)
                    cargarSpinnerViviendaAlquiler(codLt)
                }
            }
        actv_tipoViviendaAlq.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                til_tipoViviendaAlq.setError(null)

                indexTipoViviendaAlq = position
            }

        et_descripcion.addTextChangedListener(descripcionTextWatcher)

        et_mtoIngreso.addTextChangedListener(montoTextWatcher)
    }

    private fun validarDescripcion() {
        if(et_descripcion.text.toString().isEmpty()){
            til_descripcion.setError("campo requerido")
            isValido=false
        }
        else  til_descripcion.setError(null)
    }

    private fun validarMonto() {
        var monto = if(!et_mtoIngreso.text.toString().isEmpty()){ et_mtoIngreso.text.toString().toDouble()}else 0.00
      if(monto<=0){
          til_mtoIngreso.setError("El monto tiene que ser mayor a 0")
          isValido=false
      }
        else  til_mtoIngreso.setError(null)
    }

    private fun validarFecha() {
       if(et_fecIngreso.text.toString().isEmpty()){
           isValido=false
           til_fecIngreso.setError("Fecha es requerida")
       }
        else  til_fecIngreso.setError(null)
    }

    private fun validarTipoVivienda() {
        var codTipoIngr = 0
        if (tipoIngresoArrayList != null && !tipoIngresoArrayList.isEmpty()) {
            codTipoIngr = tipoIngresoArrayList.get(indexTipoIngreso).codTipoIngreso.toString().toInt()
        }
        if(codTipoIngr == 1 &&actv_tipoViviendaAlq.text.toString().isEmpty())
        {
            til_tipoViviendaAlq.setError("Campo requerido")
            isValido=false
        }
        else til_tipoViviendaAlq.setError(null)
    }

    private fun validarTipoLote() {
        var codTipoIngr = 0
        if (tipoIngresoArrayList != null && !tipoIngresoArrayList.isEmpty()) {
            codTipoIngr = tipoIngresoArrayList.get(indexTipoIngreso).codTipoIngreso.toString().toInt()
        }
        if(codTipoIngr == 1  && actv_tipoLote.text.toString().isEmpty())
        {
            til_tipoLote.setError("Campo requerido")
            isValido=false
        }
        else til_tipoLote.setError(null)
    }

    private fun validarTipoCasaAlquiler() {
        var codTipoIngr = 0
        if (tipoIngresoArrayList != null && !tipoIngresoArrayList.isEmpty()) {
            codTipoIngr = tipoIngresoArrayList.get(indexTipoIngreso).codTipoIngreso.toString().toInt()
        }
        if(codTipoIngr == 1 && actv_tipoCasaAlquiler.text.toString().isEmpty())
        {   til_tipoCasaAlquiler.setError("Campo requerido")
            isValido=false
        }
        else til_tipoCasaAlquiler.setError(null)
    }

    private fun validarTipoAlquiler() {
        var codTipoIngr = 0
        if (tipoIngresoArrayList != null && !tipoIngresoArrayList.isEmpty()) {
            codTipoIngr = tipoIngresoArrayList.get(indexTipoIngreso).codTipoIngreso.toString().toInt()
        }
        if(codTipoIngr == 1 && actv_tipoAlquiler.text.toString().isEmpty())
        {
            til_tipoAlquiler.setError("Campo requerido")
            isValido=false
        }
        else til_tipoIngreso.setError(null)
    }

    private fun validarTipoIngreso() {
        if( actv_tipoIngreso.text.toString().isEmpty())
        {
            til_tipoIngreso.setError("Campo requerido")
           isValido=false
        }
        else til_tipoIngreso.setError(null)


    }

    private fun showDatePickerDialog() {
        val datePicker =
            DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val sb = StringBuilder()
        sb.append(day).append("/").append(month).append("/").append(year)
        val str = sb.toString()
        et_fecIngreso.text = str.toEditable()
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun guardarIngreso() {
        var codTipoIngreso: Int = 0
        var codTipoAlquiler: Int = 0
        var codCasaAlquiler: String = ""
        var codLote: String = ""
        var codViviendaAlquiler: String = ""

        if (tipoIngresoArrayList != null && !tipoIngresoArrayList.isEmpty()) {
            codTipoIngreso =
                tipoIngresoArrayList.get(indexTipoIngreso).codTipoIngreso.toString().toInt()
        }
        if (tipoAlquilerArrayList != null && !tipoAlquilerArrayList.isEmpty()) {
            codTipoAlquiler =
                tipoAlquilerArrayList.get(indexTipoAlquiler).codTipoAlquiler.toString().toInt()
        }
        if (tipoCasaAlqArrayList != null && !tipoCasaAlqArrayList.isEmpty()) {
            codCasaAlquiler =
                tipoCasaAlqArrayList.get(indexTipoCasaAlquiler).codCasaAlquiler.toString()
        }
        if (tipoLoteArrayList != null && !tipoLoteArrayList.isEmpty()) {
            codLote = tipoLoteArrayList.get(indexTipoLote).codLote.toString()
        }
        if (tipoViviendaAlqArrayList != null && !tipoViviendaAlqArrayList.isEmpty()) {
            codViviendaAlquiler =
                tipoViviendaAlqArrayList.get(indexTipoViviendaAlq).codViviendaAlquiler.toString()
        }

        var fecIngresoStr: String = et_fecIngreso.text.toString().trim()

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var fecpago: Date? = null
        try {
            fecpago = sdf.parse(fecIngresoStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        var fecIngreso: Date? = fecpago
        var mtoIngreso: Double = et_mtoIngreso.text.toString().trim().toDouble()
        var descripcion: String = et_descripcion.text.toString().trim()

        var ingresoBuild: Ingreso = Ingreso(
            codTipoIngreso = codTipoIngreso,
            codTipoAlquiler = codTipoAlquiler,
            codCasaAlquiler = codCasaAlquiler,
            codLote = codLote,
            codViviendaAlquiler = codViviendaAlquiler,
            fecIngreso = fecIngreso,
            mtoIngreso = mtoIngreso,
            descripcion = descripcion
        )
        viewmodel.registrarIngreso(ingresoBuild)

        super.onBackPressed()
    }


    private fun cargarSpinnerTipoIngreso() {
        tipoIngresoArrayList = arrayListOf<TipoIngreso>()
        /*val adapter: ArrayAdapter<TipoIngreso> = ArrayAdapter<TipoIngreso>(
            this, android.R.layout.simple_spinner_dropdown_item, tipoIngresoArrayList
        )
        actv_tipoIngreso.setAdapter(adapter)*/
        viewmodel.getTipoIngresos().observe(this, androidx.lifecycle.Observer { arrayLis ->
            actv_tipoIngreso.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayLis))
            tipoIngresoArrayList.clear()
            tipoIngresoArrayList.addAll(arrayLis)
        })

    }

    private fun cargarSpinnerTipoAlquiler() {
        tipoAlquilerArrayList = arrayListOf<TipoAlquiler>()
   /*     val adapter: ArrayAdapter<TipoAlquiler> = ArrayAdapter<TipoAlquiler>(
            this, android.R.layout.simple_spinner_dropdown_item, tipoAlquilerArrayList
        )
        actv_tipoAlquiler.setAdapter(adapter)*/
        viewmodel.getTipoAlquiler().observe(this, androidx.lifecycle.Observer { arrayLis ->
            actv_tipoAlquiler.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayLis))
            tipoAlquilerArrayList.clear()
            tipoAlquilerArrayList.addAll(arrayLis)
        })

    }

    private fun cargarSpinnerTipoCasaAlquiler() {
        tipoCasaAlqArrayList = arrayListOf<CasaAlquiler>()
     /*   val adapter: ArrayAdapter<CasaAlquiler> = ArrayAdapter<CasaAlquiler>(
            this, android.R.layout.simple_spinner_dropdown_item, tipoCasaAlqArrayList
        )
        actv_tipoCasaAlquiler.setAdapter(adapter)*/
        viewmodel.getTipoCasa().observe(this, androidx.lifecycle.Observer { arrayLis ->
            actv_tipoCasaAlquiler.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayLis))
            tipoCasaAlqArrayList.clear()
            tipoCasaAlqArrayList.addAll(arrayLis)
        })

    }


    private fun cargarSpinnerTipoLote(codCasaAlq: String?) {
        tipoLoteArrayList = arrayListOf<Lote>()
      /*  val adapter: ArrayAdapter<Lote> = ArrayAdapter<Lote>(
            this, android.R.layout.simple_spinner_dropdown_item, tipoLoteArrayList)
        actv_tipoLote.setAdapter(adapter)
        */
        viewmodel.getTipoLote(codCasaAlq).observe(this, androidx.lifecycle.Observer { arrayLis ->
            actv_tipoLote.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayLis))
            tipoLoteArrayList.clear()
            tipoLoteArrayList.addAll(arrayLis)
        })

    }


    private fun cargarSpinnerViviendaAlquiler(codCasaLote: String?) {
        tipoViviendaAlqArrayList = arrayListOf<ViviendaAlquiler>()
        viewmodel.getTipoVivienda(codCasaLote).observe(this, androidx.lifecycle.Observer {
                arrayLis -> actv_tipoViviendaAlq.setAdapter(ArrayAdapter(this, R.layout.simple_spinner_dropdown_item_marquee_forever, arrayLis
                )
            )
            tipoViviendaAlqArrayList.clear()
            tipoViviendaAlqArrayList.addAll(arrayLis)
        })
    }

    private val montoTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            validarMonto()

        }

        override fun afterTextChanged(et: Editable) {}
    }

    private val descripcionTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            validarDescripcion()

        }

        override fun afterTextChanged(et: Editable) {}
    }


}