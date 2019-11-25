package com.example.cashmanagerfront.datasources.API

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.StringRequest


class ApiClient(private var ctx: Context) {

    /** Exemple de requete avec appel de la route
     fun getTopRateMovies(
        page: Int,
        completion: (pageCount: Int, moviesList: ArrayList<Movie>, error: String?) -> Unit
    ) {
        val route = ApiRoute.GetTopRateMovies(page)
        getListOfMovies(route, completion)
    }
*/
    /***
     * PERFORM REQUEST
     */
    private fun performRequest(route: ApiRoute, completion: (success: Boolean, apiResponse: ApiResponse) -> Unit) {
        val parameters = StringBuilder()
        var count = 0
        route.params.map {
            parameters.append("${it.key}=${it.value}")
            if (count < route.params.size) {
                parameters.append("&")
            }
            count++
        }

        val request = object : StringRequest(route.httpMethod, "${route.fullUrl}?$parameters", { response ->
            this.handle(response, completion)
        }, {
            it.printStackTrace()
            if (it.networkResponse != null && it.networkResponse.data != null)
                this.handle(String(it.networkResponse.data), completion)
            else
                this.handle(getStringError(it), completion)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                return route.headers
            }
        }

        request.retryPolicy = DefaultRetryPolicy(
            route.timeOut,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        request.tag = route.tag

        val rq = getRequestQueue()
        rq.cancelAll(route.tag)
        rq.add(request)
    }

    /**
     * This method will make the creation of the answer as ApiResponse
     **/
    private fun handle(response: String, completion: (success: Boolean, apiResponse: ApiResponse) -> Unit) {
        val ar = ApiResponse(response)
        completion.invoke(ar.success, ar)
    }

    /**
     * This method will return the error as String
     **/
    private fun getStringError(volleyError: VolleyError): String {
        return when (volleyError) {
            is TimeoutError -> "The connection timed out."
            is NoConnectionError -> "The connection could not be established."
            is AuthFailureError -> "There was an authentication failure in your request."
            is ServerError -> "Error while processing the server response."
            is NetworkError -> "Network error, please verify your connection."
            is ParseError -> "Error while processing the server response."
            else -> "Internet error"
        }
    }

    /**
     * We create and return a new instance for the queue of Volley requests.
     **/
    private fun getRequestQueue(): RequestQueue {
        val maxCacheSize = 20 * 1024 * 1024
        val cache = DiskBasedCache(ctx.cacheDir, maxCacheSize)
        val netWork = BasicNetwork(HurlStack())
        val mRequestQueue = RequestQueue(cache, netWork)
        mRequestQueue.start()
        System.setProperty("http.keepAlive", "false")
        return mRequestQueue
    }

}