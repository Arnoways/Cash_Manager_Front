package com.example.cashmanagerfront.datasources.API

import com.android.volley.Request


sealed class ApiRoute {

    private val apiKey: String
        get() {
            return ""
        }

    val timeOut: Int
        get() {
            return 3000
        }

    private val baseUrl: String
        get() {
            return when (this) {
                is DownloadImage -> "https://image.tmdb.org/t/p/"
                else -> "https://api.themoviedb.org/3/"
            }
        }

    val tag: String
        get() {
            return this::class.java.canonicalName ?: this.fullUrl
        }

    val fullUrl: String
        get() {
            return "$baseUrl${
            when (this) {
                is GetAllProducts -> "/api/product"
                is GetProductById -> "api/product/${this.productId}"
                is GetCartById -> "/api/carts/${this.cartId}"
                is GetUserById -> "movie/${this.userId}"
                is DownloadImage -> "${this.fileSize}/${this.imageUrl}?api_key=$apiKey"
                is SortProductBy -> ""/** Requete avec query filtre */
                //is GetVideos -> "movie/${this.movieId}/videos"
            }}"
        }

    val httpMethod: Int
        get() {
            return Request.Method.GET
        }

    val params: HashMap<String, String>
        get() {
            val map = hashMapOf<String, String>()
            map["api_key"] = apiKey
            map["language"] = "en-US"
            return when (this) {
                is SortProductBy -> {
                    map["query"] = this.query
                    map["page"] = this.page.toString()
                    map
                }
                else -> map
            }
        }

    val headers: HashMap<String, String>
        get() {
            val map = hashMapOf<String, String>()
            map["Content-Type"] = "application/json;charset=utf-8"
            return when (this) {
                is GetAllProducts -> {
                    map["page"] = this.page.toString()
                    map
                }
                else -> {
                    map
                }
            }
        }

    data class GetAllProducts(var page: Int) : ApiRoute()

    data class GetProductById(var productId: String) : ApiRoute()

    data class GetCartById(var cartId: String) : ApiRoute()

    data class GetUserById(var userId: String) : ApiRoute()

    data class DownloadImage(var imageUrl: String, var fileSize: String) : ApiRoute()

    data class SortProductBy(var query: String, var page: Int) : ApiRoute()
}
