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

class IngresosAdapter(private var ingresosList: ArrayList<Ingreso>) :
    RecyclerView.Adapter<IngresosAdapter.MyViewHolder>() {
    lateinit var context: Context
/*
    lateinit var newIngresosAdapterListener: IngresosAdapter.IngresosAdapterListener*/
/*    lateinit var  ingresosList: ArrayList<Ingreso>*/





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        this.context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_ingresos,
            parent, false
        )
        return MyViewHolder(itemView)

    }
/*    fun IngresosAdapter() {}
    fun IngresosAdapter( ingresosList: ArrayList<Ingreso>) {
        this.ingresosList = ingresosList
        initListener(IngresosAdapter.IngresosAdapterListener)
    }*/

  /* fun initListener(newIngresosAdapterListener:IngresosAdapter.IngresosAdapterListener) {
        try {
            this.newIngresosAdapterListener = newIngresosAdapterListener
        } catch (e: ClassCastException) {
            //e.Tag(e.message).w(e.localizedMessage)
        }
    }*/

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val ingresosModel: Ingreso = ingresosList.get(position)
        holder.bind(context, ingresosModel)

    }


    override fun getItemCount(): Int {

        return ingresosList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvFecIngreso: TextView = itemView.findViewById(R.id.tvFecIngreso)
        val tvTipoIngreso: TextView = itemView.findViewById(R.id.tvTipoIngreso)
        val tvMtoIngreso: TextView = itemView.findViewById(R.id.tvMtoIngreso)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val cv_ingresos: CardView = itemView.findViewById(R.id.cv_ingresos)

        fun bind(
            context: Context?,
            ingresoModel: Ingreso/*,
            listener: View.OnClickListener?*/
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


      /*      cv_ingresos!!.setOnClickListener(listener)*/
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




}