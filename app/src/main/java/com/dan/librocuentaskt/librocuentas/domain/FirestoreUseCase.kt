package com.dan.librocuentaskt.librocuentas.domain

import androidx.lifecycle.MutableLiveData
import com.dan.librocuentaskt.librocuentas.data.repo.FirebaseRepo
import com.dan.librocuentaskt.librocuentas.model.*

class FirestoreUseCase {

    val repo = FirebaseRepo()
    fun setRegistroIngresoFirestore(ingreso : Ingreso){
        repo.setRegistroIngreso(ingreso)

    }

    fun getTipoIngresosFirestore():MutableLiveData<ArrayList<TipoIngreso>> {
        return repo.getTipoIngreso()
    }


    fun getTipoAlquilerFirestore():MutableLiveData<ArrayList<TipoAlquiler>> {
        return repo.getTipoAlquiler()
    }

    fun getTipoCasaFirestore():MutableLiveData<ArrayList<CasaAlquiler>> {
        return repo.getTipoCasaAlquiler()
    }

    fun getTipoLoteFirestore(codCasaAlq: String?):MutableLiveData<ArrayList<Lote>> {
        return repo.getTipoLote(codCasaAlq)
    }

    fun getTipoViviendaFirestore(codCasaLote: String?):MutableLiveData<ArrayList<ViviendaAlquiler>> {
        return repo.getTipoVivienda(codCasaLote)
    }

    fun getListaIngresosFirestore():MutableLiveData<ArrayList<Ingreso>> {
        return repo.getListaIngresos()
    }



    fun setRegistroGastoFirestore(gasto:Gasto){
        repo.setRegistroGasto(gasto)

    }

    fun getTipoGastosFirestore():MutableLiveData<ArrayList<TipoGasto>> {
        return repo.getTipoGasto()
    }


    fun getTipoServicioFirestore():MutableLiveData<ArrayList<TipoServicio>> {
        return repo.getTipoServicio()
    }


    fun getListaGastosFirestore():MutableLiveData<ArrayList<Gasto>> {
        return repo.getListaGastos()
    }



}