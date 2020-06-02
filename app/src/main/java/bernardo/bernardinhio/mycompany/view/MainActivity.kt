package bernardo.bernardinhio.mycompany.view

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bernardo.bernardinhio.mycompany.R
import bernardo.bernardinhio.mycompany.retrofit.dataprovider.LoginDataProvider
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        showHomePageAnimation()

        subscribeToBackendConnectionStatus()
    }

    private fun showHomePageAnimation(){
        logoHomePage.visibility = View.VISIBLE
        logoHomePage.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.bounce
            )
        )
        companyName.visibility = View.VISIBLE
        companyName.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.bounce
            )
        )
        websiteName.visibility = View.VISIBLE
        websiteName.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.blink
            )
        )
    }

    private fun subscribeToBackendConnectionStatus() {

        val mainActivityObserver: Observer<String> = object : Observer<String> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(connectionInfo: String) {
                when(connectionInfo){
                    LoginDataProvider.BackendStatus.SUCCESSFUL_CONNECTION.message -> {
                        logoHomePage.clearAnimation()
                        companyName.clearAnimation()
                        websiteName.clearAnimation()
                        logoHomePage.visibility = View.GONE
                        companyName.visibility = View.GONE
                        websiteName.visibility = View.GONE
                        startActivity(Intent(this@MainActivity, ResultActivity::class.java))
                        updateUi(LoginStaus.SUCCESS)
                    }
                    LoginDataProvider.BackendStatus.REQUEST_NOT_MADE_YET.message -> {}
                    else -> {
                        Toast.makeText(this@MainActivity, "Connection:\n$connectionInfo", Toast.LENGTH_LONG).show()
                        updateUi(LoginStaus.FAILURE)
                    }
                }
            }

            override fun onError(e: Throwable) {}
        }

        LoginDataProvider.connectionStatus.subscribe(mainActivityObserver)
    }

    private fun updateUi(loginStaus:LoginStaus){
        when(loginStaus){
            LoginStaus.PENDING -> {
                progressBarMainActivity.setVisibility(View.VISIBLE)
                inputUsername.isEnabled = false
                inputPassword.isEnabled = false
                btnConnectToApi.isEnabled = false
            }
            else -> {
                progressBarMainActivity.setVisibility(View.GONE)
                inputUsername.isEnabled = true
                inputPassword.isEnabled = true
                btnConnectToApi.isEnabled = true
            }
        }
    }

    enum class LoginStaus{
        PENDING, SUCCESS, FAILURE
    }

    fun connectToApi(view: View) {
        updateUi(LoginStaus.PENDING)

        /**
         * To mock I used:
         * https://www.mocky.io/
         * I choose the "Status code" to be:
         * "200 OK"
         * I Chose "Switch to advanced mode" and I added a custom header
         * Authorization : "Basic YmVybmFyZG86YmVybmFyZGluaGlv"
         * I got the "Basic YmVybmFyZG86YmVybmFyZGluaGlv" by debugging and getting
         * the resulted value of the "authHeader" when trying to login with
         * username = "bernardo" and password = "bernardinhio"
         * I obtained
         * http://www.mocky.io/v2/5ed691013400003ed306db30
         *
          */

        val username: String = inputUsername.text.toString()
        val password: String = inputPassword.text.toString()
        val base = "$username:$password"
        // As seen in the Postman tool to check the Http Authentication call,
        // we need to encode the username & password so the server can read
        // and can check to see if those authentication are true
        val encodedBase = Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP)
        val authHeader = "Basic $encodedBase"
        // save it for next activities when Swipe to refresh for example
        LoginDataProvider.loginAuthenticationHeader = authHeader

        LoginDataProvider.login(authHeader)
    }
}