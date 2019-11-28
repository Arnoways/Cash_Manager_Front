package com.example.cashmanagerfront.data

import android.app.Activity
import com.example.cashmanagerfront.models.Product
import com.example.cashmanagerfront.objects.api.Api
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class BddDataSource(var myActivity: Activity) {

    fun generateProducts() {
        // get the json file
        val inputStream: InputStream = myActivity.assets.open("products.txt")
        val inputStreamReader = InputStreamReader(inputStream)
        var line: String?
        val br = BufferedReader(inputStreamReader)
        line = br.readLine()
        while (line != null) {
            val datas: List<String> = line.split(";")
            Api.createProduct(datas[0].toInt(), datas[1], datas[2].toDouble())
            line = br.readLine()
        }
    }
}