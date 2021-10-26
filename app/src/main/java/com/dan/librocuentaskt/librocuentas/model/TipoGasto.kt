package com.dan.librocuentaskt.librocuentas.model

class TipoGasto (
    val codTipoGasto : Int ? = 0,
    val desTipoGasto : String = ""
){
    override fun toString(): String {
        return desTipoGasto
    }
}
