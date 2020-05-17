package bernardo.bernardinhio.lovooapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bernardo.bernardinhio.lovooapp.R
import bernardo.bernardinhio.lovooapp.adapter.MainInfoAdapter
import bernardo.bernardinhio.lovooapp.datamodel.MainInfoItemDataModel
import bernardo.bernardinhio.lovooapp.retrofit.dataprovider.LoginDataProvider
import bernardo.bernardinhio.lovooapp.retrofit.model.BackendDataModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    private lateinit var adapterMainInfo: MainInfoAdapter
    private val dataRecyclerViewMainInfo = ArrayList<MainInfoItemDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        title = "My company events"

        setupRecyclerViewMain()
        setupAdapterRecyclerViewMain()

        subscribeToBackendData()
    }

    private fun setupRecyclerViewMain() {
        recyclerViewMainInfo.setHasFixedSize(false)
        recyclerViewMainInfo.setLayoutManager(LinearLayoutManager(this.applicationContext))
    }

    private fun setupAdapterRecyclerViewMain() {
        adapterMainInfo = MainInfoAdapter(this, dataRecyclerViewMainInfo)
        recyclerViewMainInfo.adapter = adapterMainInfo
    }

    private fun subscribeToBackendData() {

        val resultActivityObserver: Observer<List<BackendDataModel>> = object : Observer<List<BackendDataModel>> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {}

            override fun onNext(backendData: List<BackendDataModel>) {
                if (LoginDataProvider.connectionStatus.value.equals(LoginDataProvider.BackendStatus.SUCCESSFUL_CONNECTION.message)){
                    populateRecyclerView(backendData)
                }
            }

            override fun onError(e: Throwable) {}
        }

        LoginDataProvider.appData.subscribe(resultActivityObserver)
    }

    private fun populateRecyclerView(backendData: List<BackendDataModel>) {
        backendData.forEach { backendItemData ->
            dataRecyclerViewMainInfo.add(
                    MainInfoItemDataModel(
                            backendItemData.name.orEmpty(),
                            backendItemData.department.orEmpty(),
                            backendItemData.type?:backendItemData.typ.orEmpty(),
                            backendItemData.roomNumber.orEmpty(),
                            backendItemData.officeLevel?.toString().orEmpty(),
                            backendItemData.id.orEmpty(),
                            backendItemData.lovooFact?.title.orEmpty(),
                            backendItemData.lovooFact?.text.orEmpty(),
                            backendItemData.lovooFact?.images.orEmpty()
                    )
            )
        }

        adapterMainInfo.notifyDataSetChanged()
    }
}