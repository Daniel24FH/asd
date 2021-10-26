package com.dan.librocuentaskt.librocuentas.model

data class TipoIngreso  (
    val codTipoIngreso : Int ? = 0,
    val desTipoIngreso : String = ""
){
    override fun toString(): String {
        return desTipoIngreso
    }
}


