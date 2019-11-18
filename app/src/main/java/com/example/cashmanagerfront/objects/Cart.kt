package com.example.cashmanagerfront.objects

import com.example.cashmanagerfront.models.Order
import com.example.cashmanagerfront.models.Product

object Cart {

    val products: MutableList<Product> = mutableListOf()

    /*
        Add a new product to our cart
     */
    fun add(product: Product): Boolean {
        return products.add(product)
    }

    /*
        Remove a product from our cart
     */
    fun remove(product: Product): Boolean {
        return products.remove(product)
    }

    /*
        This method return a list of Orders with the name of the product, the quantity and the total price without taxes
     */
    fun orders(): MutableList<Order> {
        val productsCopy: MutableList<Product> = mutableListOf()

        val orders: MutableList<Order> = mutableListOf()

        var index = 0
        for (item in products) {
            if (!productsCopy.contains(item)) {
                val quantity = products.count { it -> it.id == item.id }
                val price = quantity * item.price_without_taxes
                val name = item.name

                orders.add(Order(index, name, quantity, price))
                // add this item to our checked list
                productsCopy.add(item)

                index++
            }

        }

        return orders
    }

    fun empty() {
        products.clear()
    }
}