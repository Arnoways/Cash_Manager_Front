package com.example.cashmanagerfront.enums

enum class PaymentStatus {
    NONE {
        override fun value() = "UNDEFINED"
    },
    PENDING {
        override fun value() = "PENDING"
    },
    AUTHORIZED {
        override fun value() = "AUTHORIZED"
    },
    REFUSED {
        override fun value() = "REFUSED"
    };

    abstract fun value(): String
}