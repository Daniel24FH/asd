package com.dan.librocuentaskt.librocuentas.model

data class ViviendaAlquiler(
    val codViviendaAlquiler: String ? = null,
    val desViviendaAlquiler: String = "",
    val desLargaViviendaAlquiler: String = "",
    val indEstado: Int ? = 0
){
    override fun toString(): String {
        return desLargaViviendaAlquiler
    }
}
