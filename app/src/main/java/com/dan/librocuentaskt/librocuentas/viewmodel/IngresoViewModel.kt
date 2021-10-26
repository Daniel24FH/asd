package com.dan.librocuentaskt.librocuentas.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dan.librocuentaskt.librocuentas.domain.FirestoreUseCase
import com.dan.librocuentaskt.librocuentas.model.*

class IngresoViewModel : ViewModel(){

    val firestoreUseCase = FirestoreUseCase()

    fun registrarIngreso(ingreso : Ingreso){
        firestoreUseCase.setRegistroIngresoFirestore(ingreso)
    }
    fun getTipoIngresos():MutableLiveData<ArrayList<TipoIngreso>> {
        return firestoreUseCase.getTipoIngresosFirestore()
    }

    fun getTipoAlquiler():MutableLiveData<ArrayList<TipoAlquiler>> {
        return firestoreUseCase.getTipoAlquilerFirestore()
    }

    fun getTipoCasa():MutableLiveData<ArrayList<CasaAlquiler>> {
        return firestoreUseCase.getTipoCasaFirestore()
    }

    fun getTipoLote(codCasaAlq: String?):MutableLiveData<ArrayList<Lote>> {
        return firestoreUseCase.getTipoLoteFirestore(codCasaAlq)
    }

    fun getTipoVivienda(codCasaLote: String?):MutableLiveData<ArrayList<ViviendaAlquiler>> {
        return firestoreUseCase.getTipoViviendaFirestore(codCasaLote)
    }

    fun getListaIngresos():MutableLiveData<ArrayList<Ingreso>> {
        return firestoreUseCase.getListaIngresosFirestore()
    }


}