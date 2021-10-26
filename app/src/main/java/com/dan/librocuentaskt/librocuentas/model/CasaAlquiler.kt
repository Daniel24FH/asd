package com.dan.librocuentaskt.librocuentas.model

data class CasaAlquiler(
    val codCasaAlquiler: String ? = null,
    val desCasaAlquiler: String = ""
){
    override fun toString(): String {
        return desCasaAlquiler
    }
}
