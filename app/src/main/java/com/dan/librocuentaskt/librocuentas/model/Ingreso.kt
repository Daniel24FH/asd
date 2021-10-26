package com.dan.librocuentaskt.librocuentas.model

import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.*

data class Ingreso(
    var codTipoIngreso : Int ?= 0,
    var codTipoAlquiler : Int?=0,
    var codCasaAlquiler : String ?= null,
    var codLote : String ?= null,
    var codTipoPiso : String ?= null,
    var codTipoCasa : String ?= null,
    var codTipoZonaCasa : String ?= null,
    var codViviendaAlquiler : String ?= null,
    var fecIngreso : Date ?= null,
    var mtoIngreso : Double ?= 0.0,
    var descripcion : String ?= null,
    @ServerTimestamp
    var fecRegistro : Date?= null,
    ):Serializable


