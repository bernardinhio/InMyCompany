package bernardo.bernardinhio.lovooapp.retrofit.dataprovider

import android.accounts.AuthenticatorException
import android.util.Log
import bernardo.bernardinhio.lovooapp.retrofit.model.BackendDataModel
import bernardo.bernardinhio.lovooapp.retrofit.service.RetrofitInstance
import com.google.gson.JsonSyntaxException
import io.reactivex.subjects.ReplaySubject
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

const val LOG_TAG = "RetrofitMessage"

object LoginDataProvider {

    const val BASE_URL_LOGIN = "https://europe-west1-lv-trialwork.cloudfunctions.net/lovooOffice/"

    var appData = ReplaySubject.create<List<BackendDataModel>>()
    var connectionStatus = ReplaySubject.create<String>().apply { onNext(BackendStatus.REQUEST_NOT_MADE_YET.message) }

    var loginAuthenticationHeader: String = ""

    fun login(authHeader: String) {

        val loginCall = RetrofitInstance
                .setupRetrofitCalls()
                .getLoginCall(authHeader)

        loginCall.enqueue(object : Callback<List<BackendDataModel>> {

            override fun onResponse(
                call: Call<List<BackendDataModel>>,
                response: Response<List<BackendDataModel>>
            ) {

                if (response.isSuccessful && response.code() == HttpURLConnection.HTTP_OK) {

                    val body: List<BackendDataModel>? = response.body()

                    if (body.isNullOrEmpty()) { // no data in backend
                        notifyDataProvider(listOf(), BackendStatus.SUCCESSFUL_CONNECTION_BUT_NO_DATA.message)
                        Log.d(LOG_TAG, BackendStatus.SUCCESSFUL_CONNECTION_BUT_NO_DATA.message)
                    } else {
                        notifyDataProvider(body, BackendStatus.SUCCESSFUL_CONNECTION.message)
                        notifyLoggerSuccess(body) // Just used in Logger
                    }

                } else {
                    notifyDataProvider(listOf(), BackendStatus.SOMETHING_WRONG_CODE_200.message)
                    Log.d(LOG_TAG, BackendStatus.SOMETHING_WRONG_CODE_200.message)
                }
            }

            override fun onFailure(call: Call<List<BackendDataModel>>, error: Throwable) {

                when (error) {
                    is SocketTimeoutException -> notifyDataProvider(listOf(), BackendStatus.ERROR_CONNECTION_TIMEOUT.message)
                    is UnknownHostException -> notifyDataProvider(listOf(), BackendStatus.ERROR_NO_INTERNET.message)
                    is ConnectException -> notifyDataProvider(listOf(), BackendStatus.ERROR_SERVER_NOT_RESPONDING.message)
                    is JSONException -> notifyDataProvider(listOf(), BackendStatus.ERROR_PARSING.message)
                    is JsonSyntaxException -> notifyDataProvider(listOf(), BackendStatus.ERROR_PARSING_SYNTAX.message)
                    is IOException -> notifyDataProvider(listOf(), BackendStatus.ERROR_OTHER.message)
                    is AuthenticatorException -> notifyDataProvider(listOf(), BackendStatus.ERROR_WRONG_LOGIN_CODE.message)
                    // more error Classes depending on the Api specific exceptions
                }

            }
        })
    }

    enum class BackendStatus(val message: String) {
        SUCCESSFUL_CONNECTION("Request Successful"),
        SUCCESSFUL_CONNECTION_BUT_NO_DATA("Request Successful but no data in backend"),
        SOMETHING_WRONG_CODE_200("Something wrong: request not succeeded"),
        ERROR_CONNECTION_TIMEOUT("Error: Connection timeout"),
        ERROR_NO_INTERNET("Error: No Internet"),
        ERROR_SERVER_NOT_RESPONDING("Error: Server not responding"),
        ERROR_PARSING("Error: Parse error"),
        ERROR_PARSING_SYNTAX("Error: Parse error Syntax"),
        ERROR_OTHER("Error: throwable error"),
        ERROR_WRONG_LOGIN_CODE("Error: Wrong Login code"),
        REQUEST_NOT_ALLOWED("Request not allowed by Admin"),
        REQUEST_NOT_MADE_YET("Request not made yet")
    }

    /**
     * Subject emit for the first time as Observable and then can later be used
     * as Observer to observer an Observable source and nitify its own observers
     */
    private fun notifyDataProvider(responseBody: List<BackendDataModel>, connectionMessage: String) {
        appData.onNext(responseBody)
        connectionStatus.onNext(connectionMessage)
    }

    private fun notifyLoggerSuccess(body: List<BackendDataModel>) {
        body.forEach {
            Log.d(LOG_TAG, it.department.orEmpty())
            Log.d(LOG_TAG, it.id.orEmpty())
            Log.d(LOG_TAG, it.name.orEmpty())
            Log.d(LOG_TAG, it.roomNumber.orEmpty())
            Log.d(LOG_TAG, it.officeLevel?.toString().orEmpty())
            Log.d(LOG_TAG, it.typ.orEmpty())
            Log.d(LOG_TAG, it.type.orEmpty())
            Log.d(LOG_TAG, it.lovooFact?.title.orEmpty())
            Log.d(LOG_TAG, it.lovooFact?.text.orEmpty())
            it.lovooFact?.images?.forEach {
                Log.d(LOG_TAG, it)
            }
        }
    }

}