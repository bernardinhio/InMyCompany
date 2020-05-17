package bernardo.bernardinhio.lovooapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bernardo.bernardinhio.lovooapp.R
import bernardo.bernardinhio.lovooapp.retrofit.dataprovider.LoginDataProvider
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        subscribeToBackendConnectionStatus()
    }

    private fun subscribeToBackendConnectionStatus() {

        val mainActivityObserver: Observer<String> = object : Observer<String> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(connectionInfo: String) {
                when(connectionInfo){
                    LoginDataProvider.BackendStatus.SUCCESSFUL_CONNECTION.message -> startResultActivity()
                    LoginDataProvider.BackendStatus.REQUEST_NOT_MADE_YET.message -> {}
                    else -> Toast.makeText(this@MainActivity, "Connection:\n$connectionInfo", Toast.LENGTH_LONG).show()
                }
            }

            override fun onError(e: Throwable) {}
        }

        LoginDataProvider.connectionStatus.subscribe(mainActivityObserver)
    }

    private fun startResultActivity() {
        startActivity(Intent(this, ResultActivity::class.java))
        progressBarMainActivity.setVisibility(View.GONE)
        inputUsername.isEnabled = true
        inputPassword.isEnabled = true
        btnConnectToApi.isEnabled = true
    }

    fun connectToApi(view: View) {
        progressBarMainActivity.setVisibility(View.VISIBLE)
        inputUsername.isEnabled = false
        inputPassword.isEnabled = false
        btnConnectToApi.isEnabled = false

        val username: String = inputUsername.text.toString()
        val password: String = inputPassword.text.toString()
        val base: String = "$username:$password"
        // As seen in the Postman tool to check the Http Authentication call,
        // we need to encode the username & password so the server can read
        // and can check to see if those authentication are true
        val encodedBase = Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP)
        val authHeader: String = "Basic $encodedBase"

        LoginDataProvider.login(authHeader)
    }
}