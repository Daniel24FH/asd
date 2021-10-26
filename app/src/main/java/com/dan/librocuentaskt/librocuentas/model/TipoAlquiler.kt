package com.dan.librocuentaskt.librocuentas.model

data class TipoAlquiler(
    val codTipoAlquiler: Int ? = 0,
    val desTipoAlquiler: String = ""
){
    override fun toString(): String {
        return desTipoAlquiler
    }
}
