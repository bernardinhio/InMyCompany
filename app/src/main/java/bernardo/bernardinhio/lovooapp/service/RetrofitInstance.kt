package bernardo.bernardinhio.lovooapp.service

import bernardo.bernardinhio.lovooapp.dataprovider.LoginDataProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    fun setupRetrofitCalls(): RetrofitCalls {
        return Retrofit.Builder()
            .baseUrl(LoginDataProvider.BASE_URL_LOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitCalls::class.java)
    }
}