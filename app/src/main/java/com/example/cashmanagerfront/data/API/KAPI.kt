package com.example.cashmanagerfront.data.API

import khttp.*
import org.json.JSONObject
import com.example.cashmanagerfront.data.model.Cart
import org.json.JSONArray


val serveurRoute = "http://3.231.177.119:8080"

/***
 *
 * Gestion de l'authentification
 *
 */
private fun signIn(email : String, pwd : String): Int {

    val url = serveurRoute + "/api/signIn"
    val payload = mapOf("email" to email, "pwd" to pwd)
    val r = post(url, data= JSONObject(payload))

    return  r.jsonObject.getInt("userID")
}

private fun signOut(userId : Int) {

    val url = serveurRoute + "/api/signOut/"+userId
    val payload = mapOf("userId" to userId)
    val r = post(url, data= JSONObject(payload))
}

private fun signUp(email : String, login: String, pwd : String): Int {

    val url = serveurRoute + "/api/signIn"
    val payload = mapOf("email" to email, "pwd" to pwd, "login" to login)
    val r = post(url, data= JSONObject(payload))

    return  r.jsonObject.getInt("userID")

}

/***
 *
 * Gestion des produits
 *
 */

private fun getAllProduct(): JSONArray {

    var url = serveurRoute + "/api/product"
    var r = get(url)

    val productList : JSONArray = r.jsonArray

    return productList
}

private fun getAllProduct(idProduct: Int): JSONObject {

    var url = serveurRoute +"api/product/"+idProduct
    var r = get(url)

    val product : JSONObject = r.jsonObject

    return product
}



/***
 *
 * Gestion de panier
 *
 */

private fun postCart(cart: Cart) {

    val url = serveurRoute + "/api/cart"
    val payload = mapOf("cart" to cart)

    val r = post(url, data=JSONObject(payload))
}

/**
private fun putCart(cartId: Int) {

    val url = serveurRoute + "/api/carts/"+cartId
    val payload = mapOf("cart" to cart)

    val r = post(url, data=JSONObject(payload))
}
*/
