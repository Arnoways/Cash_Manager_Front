package com.example.cashmanagerfront.data

import com.example.cashmanagerfront.data.model.Product
import java.io.File


class ProductDataSource() {
    var products: MutableList<Product> = ArrayList();

    fun loadProducts(): MutableList<Product> {
        // get the json file
        File("@res/products.txt").forEachLine {
            val datas: List<String> = it.split(";")

            products.add(Product(datas[0].toInt(), datas[1], datas[2].toDouble()))
        }

        return products
    }
}