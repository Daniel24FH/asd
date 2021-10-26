package com.dan.librocuentaskt.librocuentas.ui.ingresos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dan.librocuentaskt.R
import com.dan.librocuentaskt.librocuentas.model.Ingreso
import com.dan.librocuentaskt.librocuentas.viewmodel.IngresoViewModel
import java.lang.ClassCastException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class IngresoAdapter(ingresosList: ArrayList<Ingreso>) : RecyclerView.Adapter<IngresoAdapter.ViewHolder>() {
    lateinit var context: Context
     lateinit var  newIngresoAdapterListener: IngresoAdapterListener

    abstract class IngresoAdapterListener {
        abstract fun onItemSelected(ingresoModel: Ingreso)

    }

    lateinit var  ingresosList: ArrayList<Ingreso>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_ingresos, parent, false)
        return  ViewHolder(View)

    }
    fun IngresoAdapter() {}
    fun IngresoAdapter( ingresosList: ArrayList<Ingreso>) {
        this.ingresosList = ingresosList
    /*    initListener(IngresoAdapterListener)*/
        try {
            this.newIngresoAdapterListener = newIngresoAdapterListener
        } catch (e: ClassCastException) {
            //e.Tag(e.message).w(e.localizedMessage)
        }
    }

     fun initListener(newIngresoAdapterListener:IngresoAdapter.IngresoAdapterListener) {
          try {
              this.newIngresoAdapterListener = newIngresoAdapterListener
          } catch (e: ClassCastException) {
              //e.Tag(e.message).w(e.localizedMessage)
          }
      }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ingresosModel: Ingreso = ingresosList.get(position)
        holder.bind(context, ingresosModel,IngresoAdapter.ItemClickListener(ingresosModel))

    }


    override fun getItemCount(): Int {

        return ingresosList.size
    }


    class ViewHolder(View: View) : RecyclerView.ViewHolder(View) {

        val tvFecIngreso: TextView = View.findViewById(R.id.tvFecIngreso)
        val tvTipoIngreso: TextView = View.findViewById(R.id.tvTipoIngreso)
        val tvMtoIngreso: TextView = View.findViewById(R.id.tvMtoIngreso)
        val tvDescripcion: TextView = View.findViewById(R.id.tvDescripcion)
        val cv_ingresos: CardView = View.findViewById(R.id.cv_ingresos)

        fun bind(
            context: Context?,
            ingresoModel: Ingreso,
            listener: View.OnClickListener?
        ) {

            /*codTipoIngreso.text = ingresoModel.codTipoIngreso
            codTipoAlquiler.text = ingresoModel.codTipoAlquiler
            codAlquilerCasa.text = ingresoModel.codAlquilerCasa
            codLote.text = ingresoModel.codLote
            codTipoPiso.text = ingresoModel.codTipoPiso
            codTipoCasa.text = ingresoModel.codTipoCasa
            codTipoZonaCasa.text = ingresoModel.codTipoZonaCasa
            codCasa.text = ingresoModel.codCasa
            fecIngreso.text = ingresoModel.fecIngreso
            mtoIngreso.text = ingresoModel.mtoIngreso
            fecRegistro.text = ingresoModel.fecRegistro
            descripcion.text = ingresoModel.descripcion*/
            tvFecIngreso.text = ingresoModel.fecIngreso.toString()
            var asd: String = ""
            if (ingresoModel.fecIngreso != null) {
                asd = getFechaDDMMYYYY(ingresoModel.fecIngreso!!)
            }
            tvFecIngreso.text = asd
            //val codTipoIngreso : Int? = ingresoModel.codTipoIngreso
            //tvTipoIngreso.text =ingresoModel.codTipoIngreso!=null ?getTipoIngreso(ingresoModel.codTipoIngreso) : "-"
            tvTipoIngreso.text =
                if (ingresoModel.codTipoIngreso != null) getTipoIngreso(ingresoModel.codTipoIngreso!!) else "-"
            tvMtoIngreso.text = ingresoModel.mtoIngreso.toString()
            tvDescripcion.text = ingresoModel.descripcion


            cv_ingresos!!.setOnClickListener(listener)
        }

        private fun getFechaDDMMYYYY(fechaIngreso: Date): String {
            var fecha: String = ""
            val nuevoformato = SimpleDateFormat("dd/MM/yyyy")
            fecha = nuevoformato.format(fechaIngreso)
            return fecha
        }
        fun getTipoIngreso(codTipoIng: Int): String {
            var codTipoIngreso : Int = codTipoIng.toInt()
            var tipoIngreso: String? = " "
            when (codTipoIngreso) {
                1 -> tipoIngreso = "Alquiler"
                2 -> tipoIngreso = "Pensión"
                3 -> tipoIngreso = "Prestamo"
                4 -> tipoIngreso = "Otros"
                else -> { // Note the block
                    tipoIngreso=""
                }
            }
            return tipoIngreso
        }

    }


    private class ItemClickListener(model: Ingreso) : View.OnClickListener {
        private val model: Ingreso

        init {
            this.model = model
        }
         override fun onClick(v: View) {
            if (v.id == R.id.cv_ingresos) {
              //  newIngresoAdapterListener.onItemSelected(model)


            }
        }

    }



}