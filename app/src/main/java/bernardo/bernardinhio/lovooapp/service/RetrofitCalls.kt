package bernardo.bernardinhio.lovooapp.service

import bernardo.bernardinhio.lovooapp.model.LoginDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitCalls {

    /**
     * We need to add the authorization header
     */
    @GET("basic")
    fun getLoginCall(@Header("Authorization") authHeader: String): Call<List<LoginDataModel>>
}