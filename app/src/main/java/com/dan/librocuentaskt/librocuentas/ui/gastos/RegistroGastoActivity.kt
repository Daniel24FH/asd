package com.dan.librocuentaskt.librocuentas.ui.gastos

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.dan.librocuentaskt.R
import com.dan.librocuentaskt.librocuentas.model.*
import com.dan.librocuentaskt.librocuentas.viewmodel.GastoViewModel
import com.dan.librocuentaskt.utils.DatePickerFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegistroGastoActivity : AppCompatActivity() {


    private lateinit var btn_Guardar: MaterialButton
    private lateinit var actv_tipoGasto: AutoCompleteTextView
    private lateinit var actv_tipoServicio: AutoCompleteTextView

    private lateinit var et_mtoGasto: TextInputEditText
    private lateinit var et_fecGasto: TextInputEditText
    private lateinit var et_descripcion: TextInputEditText
    private lateinit var til_tipoGasto: TextInputLayout
    private lateinit var til_tipoServicio: TextInputLayout
    private lateinit var til_mtoGasto: TextInputLayout
    private lateinit var til_fecGasto: TextInputLayout
    private lateinit var til_descripcion: TextInputLayout
    private lateinit var tipoGastoArrayList: ArrayList<TipoGasto>
    private lateinit var tipoServicioArrayList: ArrayList<TipoServicio>



    private lateinit var viewmodel: GastoViewModel

    private var indexTipoGasto = 0
    private var indexTipoServicio = 0
    private var indexTipoCasaAlquiler = 0
    private var indexTipoLote = 0
    private var indexTipoViviendaAlq = 0

    private var isValido = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_gasto_activity)

        viewmodel = ViewModelProvider(this).get(GastoViewModel::class.java)

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

        actv_tipoGasto = findViewById<AutoCompleteTextView>(R.id.actv_tipoGasto)
        actv_tipoServicio = findViewById<AutoCompleteTextView>(R.id.actv_tipoServicio)

        et_mtoGasto = findViewById<TextInputEditText>(R.id.et_mtoGasto)
        et_fecGasto = findViewById<TextInputEditText>(R.id.et_fecGasto)
        et_descripcion = findViewById<TextInputEditText>(R.id.et_descripcion)
        til_tipoGasto = findViewById<TextInputLayout>(R.id.til_tipoGasto)
        til_tipoServicio = findViewById<TextInputLayout>(R.id.til_tipoServicio)

        til_mtoGasto = findViewById<TextInputLayout>(R.id.til_mtoGasto)
        til_fecGasto = findViewById<TextInputLayout>(R.id.til_fecGasto)
        til_descripcion = findViewById<TextInputLayout>(R.id.til_descripcion)


    }

    private fun initEvent() {

        var codTipoIng: Int = 0
        var codTipoAlq: Int = 0
        var codCasaAlq: String = ""
        var codLt: String = ""
        var codViviendaAlq: String = ""

        cargarSpinnerTipoGasto()
        cargarSpinnerTipoServicio()

        btn_Guardar.setOnClickListener {

            isValido=true;
            validarTipoGasto()
            validarTipoServicio()

            validarFecha()
            validarMonto()
            validarDescripcion()
            if (isValido) {
                guardarGasto()
            }
        }
        et_fecGasto.setOnClickListener {
            showDatePickerDialog()
            til_fecGasto.setError(null)
        }

        actv_tipoGasto.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                til_tipoGasto.setError(null)

                indexTipoGasto = position

                if (tipoGastoArrayList != null && !tipoGastoArrayList.isEmpty()) {
                    codTipoIng = tipoGastoArrayList.get(indexTipoGasto).codTipoGasto.toString().toInt()

                }
                if (codTipoIng == 1) {

                } else {

                }

            }
        actv_tipoServicio.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                til_tipoServicio.setError(null)
                indexTipoServicio = position


                if (tipoServicioArrayList != null && !tipoServicioArrayList.isEmpty()) {
                    codTipoAlq =
                        tipoServicioArrayList.get(indexTipoServicio).codTipoServicio.toString()
                            .toInt()
                }
                if (codTipoAlq != 0) {
                    if (codTipoAlq == 1) {

                    } else {

                    }
                }
            }
        et_descripcion.addTextChangedListener(descripcionTextWatcher)

        et_mtoGasto.addTextChangedListener(montoTextWatcher)
    }

    private fun validarDescripcion() {
        if(et_descripcion.text.toString().isEmpty()){
            til_descripcion.setError("campo requerido")
            isValido=false
        }
        else  til_descripcion.setError(null)
    }

    private fun validarMonto() {
        var monto = if(!et_mtoGasto.text.toString().isEmpty()){ et_mtoGasto.text.toString().toDouble()}else 0.00
        if(monto<=0){
            til_mtoGasto.setError("El monto tiene que ser mayor a 0")
            isValido=false
        }
        else  til_mtoGasto.setError(null)
    }

    private fun validarFecha() {
        if(et_fecGasto.text.toString().isEmpty()){
            isValido=false
            til_fecGasto.setError("Fecha es requerida")
        }
        else  til_fecGasto.setError(null)
    }






    private fun validarTipoServicio() {
        var codTipoIngr = 0
        if (tipoGastoArrayList != null && !tipoGastoArrayList.isEmpty()) {
            codTipoIngr = tipoGastoArrayList.get(indexTipoGasto).codTipoGasto.toString().toInt()
        }
        if(codTipoIngr == 1 && actv_tipoServicio.text.toString().isEmpty())
        {
            til_tipoServicio.setError("Campo requerido")
            isValido=false
        }
        else til_tipoGasto.setError(null)
    }

    private fun validarTipoGasto() {
        if( actv_tipoGasto.text.toString().isEmpty())
        {
            til_tipoGasto.setError("Campo requerido")
            isValido=false
        }
        else til_tipoGasto.setError(null)


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
        et_fecGasto.text = str.toEditable()
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun guardarGasto() {
        var codTipoGasto: Int = 0
        var codTipoServicio: Int = 0
        var codCasaAlquiler: String = ""
        var codLote: String = ""
        var codViviendaAlquiler: String = ""

        if (tipoGastoArrayList != null && !tipoGastoArrayList.isEmpty()) {
            codTipoGasto =
                tipoGastoArrayList.get(indexTipoGasto).codTipoGasto.toString().toInt()
        }
        if (tipoServicioArrayList != null && !tipoServicioArrayList.isEmpty()) {
            codTipoServicio =
                tipoServicioArrayList.get(indexTipoServicio).codTipoServicio.toString().toInt()
        }

        var fecGastoStr: String = et_fecGasto.text.toString().trim()

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var fecpago: Date? = null
        try {
            fecpago = sdf.parse(fecGastoStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        var fecGasto: Date? = fecpago
        var mtoGasto: Double = et_mtoGasto.text.toString().trim().toDouble()
        var descripcion: String = et_descripcion.text.toString().trim()

        var gastoBuild: Gasto = Gasto(
            codTipoGasto = codTipoGasto,
            codTipoServicio = codTipoServicio,
            fecGasto = fecGasto,
            mtoGasto = mtoGasto,
            descripcion = descripcion
        )
        viewmodel.registrarGasto(gastoBuild)

        super.onBackPressed()
    }


    private fun cargarSpinnerTipoGasto() {
        tipoGastoArrayList = arrayListOf<TipoGasto>()
        /*val adapter: ArrayAdapter<TipoGasto> = ArrayAdapter<TipoGasto>(
            this, android.R.layout.simple_spinner_dropdown_item, tipoGastoArrayList
        )
        actv_tipoGasto.setAdapter(adapter)*/
        viewmodel.getTipoGastos().observe(this, androidx.lifecycle.Observer { arrayLis ->
            actv_tipoGasto.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayLis))
            tipoGastoArrayList.clear()
            tipoGastoArrayList.addAll(arrayLis)
        })

    }

    private fun cargarSpinnerTipoServicio() {
        tipoServicioArrayList = arrayListOf<TipoServicio>()
        /*     val adapter: ArrayAdapter<TipoServicio> = ArrayAdapter<TipoServicio>(
                 this, android.R.layout.simple_spinner_dropdown_item, tipoServicioArrayList
             )
             actv_tipoServicio.setAdapter(adapter)*/
        viewmodel.getTipoServicio().observe(this, androidx.lifecycle.Observer { arrayLis ->
            actv_tipoServicio.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayLis))
            tipoServicioArrayList.clear()
            tipoServicioArrayList.addAll(arrayLis)
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