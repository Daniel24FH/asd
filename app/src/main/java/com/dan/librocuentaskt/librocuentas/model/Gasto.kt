package com.dan.librocuentaskt.librocuentas.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*
import java.io.Serializable


data class Gasto(
    var mtoGasto : Double ?= 0.0,
    var codTipoGasto: Int? = 0,
    var codTipoServicio: Int? = 0,
    var fecGasto: Date? = null,
    var descripcion: String? = null,
    @ServerTimestamp
    var fecRegistro: Date? = null
) : Serializable