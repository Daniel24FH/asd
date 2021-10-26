package com.dan.librocuentaskt.librocuentas.ui.ingresos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dan.librocuentaskt.R
import com.dan.librocuentaskt.librocuentas.model.Ingreso
import com.dan.librocuentaskt.librocuentas.viewmodel.IngresoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.*
import java.io.Serializable

class IngresosListaActivity : AppCompatActivity() {


    //private lateinit var dbref : DatabaseReference
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerview: RecyclerView
    private lateinit var tvMtoTotalIngresos: TextView
    private lateinit var ingresoArrayList: ArrayList<Ingreso>
    private lateinit var ingresoAdapter: IngresosAdapter
    private lateinit var fab_agregarIngreso: FloatingActionButton
    private lateinit var viewmodel: IngresoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresos_lista)

        viewmodel = ViewModelProvider(this).get(IngresoViewModel::class.java)
        initView()
    }

    override fun onResume() {
        super.onResume()
        getIngresosData()
    }

    private fun initView() {
        ingresoArrayList = arrayListOf<Ingreso>()
        ingresoAdapter = IngresosAdapter(ingresoArrayList)

        fab_agregarIngreso = findViewById<FloatingActionButton>(R.id.fab_agregarIngreso)
        tvMtoTotalIngresos = findViewById<Button>(R.id.tvMtoTotalIngresos)
        recyclerview = findViewById(R.id.ingresos_list)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)

        fab_agregarIngreso.setOnClickListener {
            val intent = Intent(this, RegistroIngresoActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getIngresosData() {
        ingresoArrayList.clear()
        db = FirebaseFirestore.getInstance()

        val myCollection = db.collection("ingresos")
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
                        ingresoArrayList.add(dc.document.toObject(Ingreso::class.java))
                    }
                }
                sumaTotal()
                recyclerview.adapter = ingresoAdapter
                ingresoAdapter.notifyDataSetChanged()
            }
        })

        viewmodel.getListaIngresos().observe(this, androidx.lifecycle.Observer {

                asd ->
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, asd)
            ingresoArrayList.clear()
            ingresoArrayList.addAll(asd)
            ingresoAdapter = IngresosAdapter(ingresoArrayList)
            recyclerview.setHasFixedSize(true)
            sumaTotal()
            recyclerview.adapter = ingresoAdapter
            ingresoAdapter.notifyDataSetChanged()
        })


    }


    private fun sumaTotal() {
        tvMtoTotalIngresos.setText("0.00")
        var mtoTotal: Double = 0.00
        for (Ingreso in ingresoArrayList) {
            if (Ingreso.mtoIngreso != null) {
                var mtoIngre: Double = Ingreso.mtoIngreso!!.toDouble()
                mtoTotal = mtoTotal + mtoIngre
            }
            tvMtoTotalIngresos.text = mtoTotal.toString()
        }
    }

    fun IngresoAdapter.onItemSelected(ingresoModel: Ingreso?) {
        val intent = Intent(this, DetalleIngresoActivity::class.java)
        startActivity(intent)
    }

}