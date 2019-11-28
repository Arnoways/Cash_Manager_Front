package com.example.cashmanagerfront.objects.api

import com.example.cashmanagerfront.models.Product
import com.example.cashmanagerfront.objects.Cart
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import org.json.JSONArray
import org.json.JSONObject

object Api {
    val serveurRoute = "http://3.231.177.119:8080"
    var token: String? = null

    /***
     *
     * Authentification
     *
     */

    /*
        This function allow the user to connect to the server, and get a token as a response.
     */
    fun signIn(email : String, pwd : String): Int {
        var ret = 0
        val url = serveurRoute + "/api/signIn"
        val payload = JSONObject(mapOf("email" to email, "pwd" to pwd))

        val r = Fuel
            .post(url)
            .jsonBody(payload.toString())
            .response { request, response, result ->
                val (bytes, error) = result
                if (bytes != null) {
                    val r = JSONObject(String(bytes))
                    ret = r.getInt("userID")
                    token = r.getString("token")

                }
                if (error != null) {
                    println(error)
                }
            }

        r.join()

        return ret


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

                    // destroy the token
                    token = null
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
     * Products
     *
     */

    fun deleteProduct(id: Int): Boolean {
        var ret = false
        val url = serveurRoute + "/api/product"

        val r = Fuel
            .delete(url + id.toString())
            .authentication()
            .bearer(token!!)
            .response { request, response, result ->
                val (bytes, error) = result
                if (bytes != null) {
                    ret = true
                    println("Product deleted")
                }
                if (error != null) {
                    println(error)
                }
            }

        r.join()

        return ret
    }

    fun createProduct(id: Int, name: String, price: Double): Boolean {
        var ret = false
        val url = serveurRoute + "/api/product"
        val payload = JSONObject(mapOf("description" to "", "id" to id, "image" to "", "name" to name, "price" to price))

        val r = Fuel
            .post(url)
            .authentication()
            .bearer(token!!)
            .jsonBody(payload.toString())
            .response { request, response, result ->
                val (bytes, error) = result
                if (bytes != null) {
                    ret = true
                    println("Product created")

                }
                if (error != null) {
                    println(error)
                }
            }

        r.join()

        return ret
    }
    /*
        This function make a call to the server, get a JSONArray as response, and parse it to return a Mutable List of Products
     */
    fun getAllProducts(): MutableList<Product> {
        var url = serveurRoute + "/api/products"
        val productList: MutableList<Product> = mutableListOf()

        var r = Fuel
            .get(url)
            .authentication()
            .bearer(token!!)
            .response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    val rArray = JSONArray(String(bytes))

                    var index = 0
                    while(index < rArray.length()) {
                        val rProduct = JSONObject(rArray[index].toString())
                        val product = Product(
                            rProduct.getInt("id"),
                            rProduct.getString("name"),
                            rProduct.getDouble("price")
                        )

                        productList.add(product)

                        index++
                    }
                }
                if (error != null) {
                    println(error)
                }
            }

        r.join()

        return productList
    }

    /*
        This function make a call to the server, get a JSONObject as response, and parse it to return Product
     */
    fun getProduct(idProduct: Int): Product {
        var url = serveurRoute +"api/product/"+idProduct

        var product: Product? = null
        var r = Fuel
            .get(url)
            .authentication()
            .bearer(token!!)
            .response { request, response, result ->
                println(request)
                println(response)
                val (bytes, error) = result
                if (bytes != null) {
                    val r = JSONObject(String(bytes))
                    product = Product(
                        r.getInt("id"),
                        r.getString("name"),
                        r.getDouble("price")
                    )

                }
                if (error != null) {
                    println(error)
                }
            }

        return product!!
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
            .authentication()
            .bearer(token!!)
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