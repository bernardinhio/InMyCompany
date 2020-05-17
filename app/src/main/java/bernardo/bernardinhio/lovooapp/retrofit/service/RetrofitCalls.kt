package bernardo.bernardinhio.lovooapp.retrofit.service

import bernardo.bernardinhio.lovooapp.retrofit.model.BackendDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitCalls {

    // We need to add the authorization header as seen in Postman tool
    @GET("basic")
    fun getLoginCall(@Header("Authorization") authHeader: String): Call<List<BackendDataModel>>
}