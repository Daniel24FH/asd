package com.dan.librocuentaskt.librocuentas.model

data class Lote(
    val codLote: String ? = null,
    val desLote: String = "",
    val desCortaLote: String? = null
){
    override fun toString(): String {
        return desLote
    }
}

