package com.dan.librocuentaskt.librocuentas.model

data class Prestamo(
    val codPrestamo: Int ? = 0,
    val desPrestamo: String = ""
){
    override fun toString(): String {
        return desPrestamo
    }
}
