package com.example.cashmanagerfront.models

import com.example.cashmanagerfront.objects.Cart

class Order(var id: Int = 0, var name: String = "", var quantity: Int = 0, var price: Double = 0.0) {

    fun getProduct(): Product? {
        return Cart.products.find { it -> it.name == name }
    }
}