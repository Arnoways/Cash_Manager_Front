package com.example.cashmanagerfront.data.API

import khttp.*
import org.json.JSONObject
import com.example.cashmanagerfront.data.model.Product
import com.example.cashmanagerfront.data.model.Cart



val defaultRoute = ""


private fun login(username : String, password : String) {

    val url = "/login"
    val payload = mapOf("username" to username, "password" to password)
    val r = post(url, data= JSONObject(payload))
}

private fun getAllProduct() {

    var url = defaultRoute + "/api/product"
    var r = get(url)

    val productList : JSONObject = r.jsonObject

}

private fun getAllProduct(idProduct: Int) {

    var url = defaultRoute +"api/product/"+idProduct
    var r = get(url)

    val product : JSONObject = r.jsonObject
}

private fun postCart(cart: Cart) {

    val url = defaultRoute + "/api/cart"
    val payload = mapOf("cart" to cart)

    val cart = post(url, data=JSONObject(payload))
}