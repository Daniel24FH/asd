package com.dan.librocuentaskt.librocuentas.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dan.librocuentaskt.librocuentas.domain.FirestoreUseCase
import com.dan.librocuentaskt.librocuentas.model.*

class GastoViewModel: ViewModel() {


    val firestoreUseCase = FirestoreUseCase()

    fun registrarGasto(gasto: Gasto){
        firestoreUseCase.setRegistroGastoFirestore(gasto)
    }
    fun getTipoGastos(): MutableLiveData<ArrayList<TipoGasto>> {
        return firestoreUseCase.getTipoGastosFirestore()
    }

    fun getTipoServicio(): MutableLiveData<ArrayList<TipoServicio>> {
        return firestoreUseCase.getTipoServicioFirestore()
    }

    fun getListaGastos(): MutableLiveData<ArrayList<Gasto>> {
        return firestoreUseCase.getListaGastosFirestore()
    }

}