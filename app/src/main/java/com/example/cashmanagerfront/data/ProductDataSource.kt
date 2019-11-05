package com.example.cashmanagerfront.data

import android.app.Activity
import com.example.cashmanagerfront.data.model.Product
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader


class ProductDataSource(var myActivity: Activity) {
    var products: MutableList<Product> = ArrayList()


    fun loadProducts(): MutableList<Product> {

        // get the json file
        val inputStream:InputStream = myActivity.assets.open("products.txt")
        val inputStreamReader = InputStreamReader(inputStream)
        var line: String?
        val br = BufferedReader(inputStreamReader)
        line = br.readLine()
        while (line != null) {
            val datas: List<String> = line.split(";")
            products.add(Product(datas[0].toInt(), datas[1], datas[2].toDouble()))
            line = br.readLine()
        }

        return products
    }
}