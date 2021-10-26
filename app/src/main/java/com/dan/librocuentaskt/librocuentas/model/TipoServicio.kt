package com.dan.librocuentaskt.librocuentas.model

class TipoServicio (
    val codTipoServicio : Int ? = 0,
    val desTipoServicio : String = ""
){
    override fun toString(): String {
        return desTipoServicio
    }
}
