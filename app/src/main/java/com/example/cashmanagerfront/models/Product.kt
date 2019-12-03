package com.example.cashmanagerfront.models

data class Product(var id: Int = 0, var name : String = "", var price: Double = 0.0) {
    var price_without_taxes: Double = price * 0.80;
}