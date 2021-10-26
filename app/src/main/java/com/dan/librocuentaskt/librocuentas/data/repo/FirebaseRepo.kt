package com.dan.librocuentaskt.librocuentas.data.repo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dan.librocuentaskt.librocuentas.model.*
import com.google.firebase.firestore.*


class FirebaseRepo {

    private var _liveTipoIngresos: MutableLiveData<ArrayList<TipoIngreso>> =
        MutableLiveData<ArrayList<TipoIngreso>>()
    private var _liveTipoAlquiler: MutableLiveData<ArrayList<TipoAlquiler>> =
        MutableLiveData<ArrayList<TipoAlquiler>>()
    private var _liveTipoCasaAlquiler: MutableLiveData<ArrayList<CasaAlquiler>> =
        MutableLiveData<ArrayList<CasaAlquiler>>()
    private var _liveTipoLote: MutableLiveData<ArrayList<Lote>> = MutableLiveData<ArrayList<Lote>>()
    private var _liveTipoVivienda: MutableLiveData<ArrayList<ViviendaAlquiler>> =
        MutableLiveData<ArrayList<ViviendaAlquiler>>()
    private var _liveIngresos: MutableLiveData<ArrayList<Ingreso>> =
        MutableLiveData<ArrayList<Ingreso>>()


    private var _liveTipoGastos: MutableLiveData<ArrayList<TipoGasto>> =
        MutableLiveData<ArrayList<TipoGasto>>()
    private var _liveTipoServicio: MutableLiveData<ArrayList<TipoServicio>> =
        MutableLiveData<ArrayList<TipoServicio>>()
    private var _liveGastos: MutableLiveData<ArrayList<Gasto>> =
        MutableLiveData<ArrayList<Gasto>>()

    val db = FirebaseFirestore.getInstance()


    fun getTipoIngreso(): MutableLiveData<ArrayList<TipoIngreso>> {
        db.collection("TipoIngreso").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val arrayTipoIngresos = ArrayList<TipoIngreso>()
                val documents = snapshot.documents
                documents.forEach {
                    val tipoIngresos = it.toObject(TipoIngreso::class.java)
                    if (tipoIngresos != null) {
                        arrayTipoIngresos.add(tipoIngresos!!)
                    }
                }
                _liveTipoIngresos.value = arrayTipoIngresos
            }

        }
        return _liveTipoIngresos
    }

    fun getTipoAlquiler(): MutableLiveData<ArrayList<TipoAlquiler>> {
        db.collection("TipoAlquiler").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val arrayTipoAlquiler = ArrayList<TipoAlquiler>()
                val documents = snapshot.documents
                documents.forEach {
                    val tipoAlquiler = it.toObject(TipoAlquiler::class.java)
                    if (tipoAlquiler != null) {
                        arrayTipoAlquiler.add(tipoAlquiler!!)
                    }
                }
                _liveTipoAlquiler.value = arrayTipoAlquiler
            }

        }
        return _liveTipoAlquiler
    }

    fun getTipoCasaAlquiler(): MutableLiveData<ArrayList<CasaAlquiler>> {
        db.collection("TipoCasaAlquiler").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val arrayTipoCasaAlquiler = ArrayList<CasaAlquiler>()
                val documents = snapshot.documents
                documents.forEach {
                    val obj = it.toObject(CasaAlquiler::class.java)
                    if (obj != null) {
                        arrayTipoCasaAlquiler.add(obj!!)
                    }
                }
                _liveTipoCasaAlquiler.value = arrayTipoCasaAlquiler
            }

        }
        return _liveTipoCasaAlquiler
    }

    fun getTipoLote(codCasaAlq: String?): MutableLiveData<ArrayList<Lote>> {

        if (codCasaAlq != null) {
            val myCollection = db.collection("TipoLote")
            val query: Query =
                myCollection.orderBy("codLote").startAt(codCasaAlq).endAt(codCasaAlq + '~');

            query.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen Failed", e)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val arrayTipoLote = ArrayList<Lote>()
                    val documents = snapshot.documents
                    documents.forEach {
                        val obj = it.toObject(Lote::class.java)
                        if (obj != null) {
                            arrayTipoLote.add(obj!!)
                        }
                    }
                    _liveTipoLote.value = arrayTipoLote
                }

            }
        }
        return _liveTipoLote
    }


    fun getTipoVivienda(codCasaLote: String?): MutableLiveData<ArrayList<ViviendaAlquiler>> {

        if (codCasaLote != null) {
            val myCollection = db.collection("TipoVivienda")
            val query: Query =
                myCollection.orderBy("codViviendaAlquiler").startAt(codCasaLote)
                    //.endAt(codCasaLote + '~').whereEqualTo("indEstado",1)
                    .endAt(codCasaLote + '~')
            query.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "*** Listen Failed", e)
                    println(" *** Listen Failed")
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val arrayTipoViviendaAlquiler = ArrayList<ViviendaAlquiler>()
                    val documents = snapshot.documents
                    documents.forEach {
                        val obj = it.toObject(ViviendaAlquiler::class.java)
                        if (obj != null) {
                            if(obj.indEstado==1) {
                                arrayTipoViviendaAlquiler.add(obj!!)
                            }
                        }
                    }
                    _liveTipoVivienda.value = arrayTipoViviendaAlquiler
                }

            }
        }
        return _liveTipoVivienda

    }

/*    internal var tipoIngresoLista: MutableLiveData<ArrayList<TipoIngreso>>
        get() { return _liveTipoIngresos }
        set(value) { _liveTipoIngresos = value }*/

    fun setRegistroIngreso(ingreso: Ingreso) {
        db.collection("ingresos").add(ingreso).addOnCompleteListener {
            if (it.isSuccessful) {

            } else {

            }

        }
    }
   /*
    fun geta() {

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

            }
        })
        return _liveTipoVivienda
    }*/

    fun getListaIngresos(): MutableLiveData<ArrayList<Ingreso>> {
        val myCollection = db.collection("ingresos")
        val query: Query = myCollection.orderBy("fecRegistro", Query.Direction.DESCENDING)
        // val query: Query = myCollection
        query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val arrayIngresos = ArrayList<Ingreso>()
                val documents = snapshot.documents
                documents.forEach {
                    val obj = it.toObject(Ingreso::class.java)
                    if (obj != null) {
                        arrayIngresos.add(obj!!)
                    }
                }
                _liveIngresos.value = arrayIngresos
            }

        }
        return _liveIngresos
    }




    /*****************GASTOS***************/


    fun getTipoGasto(): MutableLiveData<ArrayList<TipoGasto>> {
        db.collection("TipoGasto").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val arrayTipoGastos = ArrayList<TipoGasto>()
                val documents = snapshot.documents
                documents.forEach {
                    val tipoGastos = it.toObject(TipoGasto::class.java)
                    if (tipoGastos != null) {
                        arrayTipoGastos.add(tipoGastos!!)
                    }
                }
                _liveTipoGastos.value = arrayTipoGastos
            }

        }
        return _liveTipoGastos
    }

    fun getTipoServicio(): MutableLiveData<ArrayList<TipoServicio>> {
        db.collection("TipoServicio").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val arrayTipoServicio = ArrayList<TipoServicio>()
                val documents = snapshot.documents
                documents.forEach {
                    val tipoServicios = it.toObject(TipoServicio::class.java)
                    if (tipoServicios != null) {
                        arrayTipoServicio.add(tipoServicios!!)
                    }
                }
                _liveTipoServicio.value = arrayTipoServicio
            }

        }
        return _liveTipoServicio
    }

    fun setRegistroGasto(gasto: Gasto) {
        db.collection("gastos").add(gasto).addOnCompleteListener {
            if (it.isSuccessful) {

            } else {

            }

        }
    }
    /*
     fun geta() {

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

             }
         })
         return _liveTipoVivienda
     }*/

    fun getListaGastos(): MutableLiveData<ArrayList<Gasto>> {
        val myCollection = db.collection("gastos")
        val query: Query = myCollection.orderBy("fecRegistro", Query.Direction.DESCENDING)
        // val query: Query = myCollection
        query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen Failed", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val arrayGastos = ArrayList<Gasto>()
                val documents = snapshot.documents
                documents.forEach {
                    val obj = it.toObject(Gasto::class.java)
                    if (obj != null) {
                        arrayGastos.add(obj!!)
                    }
                }
                _liveGastos.value = arrayGastos
            }

        }
        return _liveGastos
    }

}