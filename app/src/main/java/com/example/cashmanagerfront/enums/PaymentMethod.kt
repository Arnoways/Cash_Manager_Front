package com.example.cashmanagerfront.enums

enum class PaymentMethod {
    NONE {
        override fun value() = "UNDEFINED"
    },
    CHEQUE {
        override fun value() = "CHEQUE"
    },
    CARD {
        override fun value() = "CARD"
    };

    abstract fun value(): String
}