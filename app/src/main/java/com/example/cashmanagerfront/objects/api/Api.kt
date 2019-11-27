package com.example.cashmanagerfront.objects.api

import com.example.cashmanagerfront.models.Product
import com.example.cashmanagerfront.objects.Cart
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody

object Api {
    val serveurRoute = "http://3.231.177.119:8080"

    /***
     *
     * Gestion de l'authentification
     *
     */
    fun signIn(email : String, pwd : String): Int {

        val url = serveurRoute + "/api/signIn"
        val payload = mapOf("email" to email, "pwd" to pwd)
        val r = Fuel
            .post(url)
            .jsonBody(payload.toString())
            .response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    println("[response bytes] ${String(bytes)}")
                }
                if (error != null) {
                    println(error)
                }
            }
        return -1


    }

    fun signOut(userId : Int) {

        val url = serveurRoute + "/api/signOut/"+userId
        val payload = mapOf("userId" to userId)
        val r = Fuel
            .post(url)
            .jsonBody(payload.toString())
            .response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    println("[response bytes] ${String(bytes)}")
                }
                if (error != null) {
                    println(error)
                }
            }
    }

    fun signUp(email : String, login: String, pwd : String): Int {

        val url = serveurRoute + "/api/signIn"
        val payload = mapOf("email" to email, "pwd" to pwd, "login" to login)
        val r = Fuel
            .post(url)
            .jsonBody(payload.toString())
            .response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    println("[response bytes] ${String(bytes)}")
                }
                if (error != null) {
                    println(error)
                }
            }

        return -1

    }

    /***
     *
     * Gestion des produits
     *
     */

    fun getAllProducts() {
        var url = serveurRoute + "/api/products"
        var r = Fuel.get(url).response { request, response, result ->
            println(request)
            println(response)
            val (bytes, error) = result
            if (bytes != null) {
                println("[response bytes] ${String(bytes)}")
            }
            if (error != null) {
                println(error)
            }
        }
    }

    fun getAProduct(idProduct: Int): Product {

        var url = serveurRoute +"api/product/"+idProduct
        var r = Fuel.get(url).response { request, response, result ->
            println(request)
            println(response)
            val (bytes, error) = result
            if (bytes != null) {
                println("[response bytes] ${String(bytes)}")
            }
            if (error != null) {
                println(error)
            }
        }

        return Product()
    }



    /***
     *
     * Gestion de panier
     *
     */

     fun postCart(cart: Cart) {

        val url = serveurRoute + "/api/cart"
        val payload = mapOf("cart" to cart)

        val r = Fuel
            .post(url)
            .jsonBody(payload.toString())
            .response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    println("[response bytes] ${String(bytes)}")
                }
                if (error != null) {
                    println(error)
                }
            }
    }

    /**
    private fun putCart(cartId: Int) {

    val url = serveurRoute + "/api/carts/"+cartId
    val payload = mapOf("cart" to cart)

    val r = post(url, data=JSONObject(payload))
    }
     */

}