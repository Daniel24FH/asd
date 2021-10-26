
package com.dan.librocuentaskt.librocuentas.ui.gastos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dan.librocuentaskt.R
import com.dan.librocuentaskt.librocuentas.model.Gasto
import com.dan.librocuentaskt.librocuentas.viewmodel.GastoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.*

class GastosListaActivity : AppCompatActivity() {


    //private lateinit var dbref : DatabaseReference
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerview: RecyclerView
    private lateinit var tvMtoTotalGastos: TextView
    private lateinit var gastoArrayList: ArrayList<Gasto>
    private lateinit var gastosAdapter: GastosAdapter
    private lateinit var fab_agregarGasto: FloatingActionButton
    private lateinit var viewmodel: GastoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos_lista)

        viewmodel = ViewModelProvider(this).get(GastoViewModel::class.java)
        initView()
    }

    override fun onResume() {
        super.onResume()
        getGastosData()
    }

    private fun initView() {
        gastoArrayList = arrayListOf<Gasto>()
        gastosAdapter = GastosAdapter(gastoArrayList)

        fab_agregarGasto = findViewById<FloatingActionButton>(R.id.fab_agregarGasto)
        tvMtoTotalGastos = findViewById<Button>(R.id.tvMtoTotalGastos)
        recyclerview = findViewById(R.id.gastos_list)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        fab_agregarGasto.setOnClickListener {
            val intent = Intent(this, RegistroGastoActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getGastosData() {
        gastoArrayList.clear()
        db = FirebaseFirestore.getInstance()

        val myCollection = db.collection("gastos")
        val query: Query = myCollection.orderBy("fecRegistro", Query.Direction.DESCENDING)
        // val query: Query = myCollection
        query.addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        gastoArrayList.add(dc.document.toObject(Gasto::class.java))
                    }
                }
                sumaTotal()
                recyclerview.adapter = gastosAdapter
                gastosAdapter.notifyDataSetChanged()
            }
        })

        viewmodel.getListaGastos().observe(this, androidx.lifecycle.Observer {

                asd ->
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, asd)
            gastoArrayList.clear()
            gastoArrayList.addAll(asd)
            gastosAdapter = GastosAdapter(gastoArrayList)
            recyclerview.setHasFixedSize(true)
            sumaTotal()
            recyclerview.adapter = gastosAdapter
            gastosAdapter.notifyDataSetChanged()
        })


    }


    private fun sumaTotal() {
        tvMtoTotalGastos.setText("0.00")
        var mtoTotal: Double = 0.00
        for (Gasto in gastoArrayList) {
            if (Gasto.mtoGasto != null) {
                var mtoIngre: Double = Gasto.mtoGasto!!.toDouble()
                mtoTotal = mtoTotal + mtoIngre
            }
            tvMtoTotalGastos.text = mtoTotal.toString()
        }
    }


}