package com.example.cashmanagerfront.data.model
import com.example.cashmanagerfront.ProductList
import com.example.cashmanagerfront.data.model.Product


data class Cart(var id: Int = 0, var price_total: Double = 0.0, var items: Array<Product> = emptyArray()
)
