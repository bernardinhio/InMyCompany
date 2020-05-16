package bernardo.bernardinhio.lovooapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import bernardo.bernardinhio.lovooapp.R
import bernardo.bernardinhio.lovooapp.dataprovider.LoginDataProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username: String = "lovooTrialUser"
        val password: String = "lovoo#2018"
        val base: String = "$username:$password"

        val encoded = Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP)
        // that becomes readable by the server, he can check to see it is true

        val authHeader: String = "Basic $encoded"

        LoginDataProvider.login(authHeader)
    }
}