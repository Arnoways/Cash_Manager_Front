package com.example.cashmanagerfront.objects

import com.example.cashmanagerfront.enums.PaymentMethod
import com.example.cashmanagerfront.enums.PaymentStatus

object Payment {

    var cart: Cart? = null
    var method: String? = null
    var status: String? = null

    init {
        method = PaymentMethod.NONE.value()
        status = PaymentStatus.NONE.value()
    }

    fun display(): String {
        var ret = ""

        if (method == PaymentMethod.CHEQUE.value()) ret = "CHEQUE"
        else if (method == PaymentMethod.CARD.value()) ret = "CARD"

        if (status != PaymentStatus.NONE.value()) return "$ret $status"
        else return "No payment done"
    }
}