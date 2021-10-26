
package com.dan.librocuentaskt.librocuentas.ui.gastos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dan.librocuentaskt.R
import com.dan.librocuentaskt.librocuentas.model.Gasto
import com.dan.librocuentaskt.librocuentas.viewmodel.GastoViewModel
import java.lang.ClassCastException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GastosAdapter(private var gastosList: ArrayList<Gasto>) :
    RecyclerView.Adapter<GastosAdapter.MyViewHolder>() {
    lateinit var context: Context
/*
    lateinit var newGastosAdapterListener: GastosAdapter.GastosAdapterListener*/
/*    lateinit var  gastosList: ArrayList<Gasto>*/





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        this.context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_gastos,
            parent, false
        )
        return MyViewHolder(itemView)

    }
/*    fun GastosAdapter() {}
    fun GastosAdapter( gastosList: ArrayList<Gasto>) {
        this.gastosList = gastosList
        initListener(GastosAdapter.GastosAdapterListener)
    }*/

    /* fun initListener(newGastosAdapterListener:GastosAdapter.GastosAdapterListener) {
          try {
              this.newGastosAdapterListener = newGastosAdapterListener
          } catch (e: ClassCastException) {
              //e.Tag(e.message).w(e.localizedMessage)
          }
      }*/

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val gastosModel: Gasto = gastosList.get(position)
        holder.bind(context, gastosModel)

    }


    override fun getItemCount(): Int {

        return gastosList.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvFecGasto: TextView = itemView.findViewById(R.id.tvFecGasto)
        val tvTipoGasto: TextView = itemView.findViewById(R.id.tvTipoGasto)
        val tvMtoGasto: TextView = itemView.findViewById(R.id.tvMtoGasto)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val cv_gastos: CardView = itemView.findViewById(R.id.cv_gastos)

        fun bind(
            context: Context?,
            gastoModel: Gasto/*,
            listener: View.OnClickListener?*/
        ) {

            /*codTipoGasto.text = gastoModel.codTipoGasto
            codTipoAlquiler.text = gastoModel.codTipoAlquiler
            codAlquilerCasa.text = gastoModel.codAlquilerCasa
            codLote.text = gastoModel.codLote
            codTipoPiso.text = gastoModel.codTipoPiso
            codTipoCasa.text = gastoModel.codTipoCasa
            codTipoZonaCasa.text = gastoModel.codTipoZonaCasa
            codCasa.text = gastoModel.codCasa
            fecGasto.text = gastoModel.fecGasto
            mtoGasto.text = gastoModel.mtoGasto
            fecRegistro.text = gastoModel.fecRegistro
            descripcion.text = gastoModel.descripcion*/
            tvFecGasto.text = gastoModel.fecGasto.toString()
            var asd: String = ""
            if (gastoModel.fecGasto != null) {
                asd = getFechaDDMMYYYY(gastoModel.fecGasto!!)
            }
            tvFecGasto.text = asd
            //val codTipoGasto : Int? = gastoModel.codTipoGasto
            //tvTipoGasto.text =gastoModel.codTipoGasto!=null ?getTipoGasto(gastoModel.codTipoGasto) : "-"
            tvTipoGasto.text =
                if (gastoModel.codTipoGasto != null) getTipoGasto(gastoModel.codTipoGasto!!) else "-"
            tvMtoGasto.text = gastoModel.mtoGasto.toString()
            tvDescripcion.text = gastoModel.descripcion


            /*      cv_gastos!!.setOnClickListener(listener)*/
        }

        private fun getFechaDDMMYYYY(fechaGasto: Date): String {
            var fecha: String = ""
            val nuevoformato = SimpleDateFormat("dd/MM/yyyy")
            fecha = nuevoformato.format(fechaGasto)
            return fecha
        }
        fun getTipoGasto(codTipoIng: Int): String {
            var codTipoGasto : Int = codTipoIng.toInt()
            var tipoGasto: String? = " "
            when (codTipoGasto) {
                1 -> tipoGasto = "Alquiler"
                2 -> tipoGasto = "Pensión"
                3 -> tipoGasto = "Prestamo"
                4 -> tipoGasto = "Otros"
                else -> { // Note the block
                    tipoGasto=""
                }
            }
            return tipoGasto
        }

    }




}