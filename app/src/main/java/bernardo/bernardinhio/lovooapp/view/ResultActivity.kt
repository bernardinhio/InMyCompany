package bernardo.bernardinhio.lovooapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bernardo.bernardinhio.lovooapp.R
import bernardo.bernardinhio.lovooapp.adapter.RecyclerViewMainInfoAdapter
import bernardo.bernardinhio.lovooapp.datamodel.RecyclerViewMainInfoDataModel
import bernardo.bernardinhio.lovooapp.retrofit.dataprovider.LoginDataProvider
import bernardo.bernardinhio.lovooapp.retrofit.model.BackendDataModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ResultActivity : AppCompatActivity() {

    private lateinit var recyclerViewMainInfo: RecyclerView
    private lateinit var adapterRecyclerViewMainInfo: RecyclerViewMainInfoAdapter
    private val dataRecyclerViewMainInfo: ArrayList<RecyclerViewMainInfoDataModel> = ArrayList<RecyclerViewMainInfoDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        title = "My company events"

        setupRecyclerViewMain()
        setupAdapterRecyclerViewMain()

        subscribeToBackendData()
    }

    private fun setupRecyclerViewMain() {
        recyclerViewMainInfo = recyclerViewMainInfo
        recyclerViewMainInfo.setHasFixedSize(false)
        recyclerViewMainInfo.setLayoutManager(LinearLayoutManager(this.applicationContext))
    }

    private fun setupAdapterRecyclerViewMain() {
        adapterRecyclerViewMainInfo = RecyclerViewMainInfoAdapter(this, dataRecyclerViewMainInfo)
        recyclerViewMainInfo.adapter = adapterRecyclerViewMainInfo
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
        backendData.forEach { itemData ->
            dataRecyclerViewMainInfo.add(
                    RecyclerViewMainInfoDataModel(
                            itemData.name.orEmpty(),
                            itemData.department.orEmpty(),
                            itemData.type?:itemData.typ.orEmpty(),
                            itemData.roomNumber.orEmpty(),
                            itemData.officeLevel?.toString().orEmpty(),
                            itemData.id.orEmpty(),
                            itemData.lovooFact?.title.orEmpty(),
                            itemData.lovooFact?.text.orEmpty(),
                            itemData.lovooFact?.images.orEmpty()
                    )
            )
        }

        adapterRecyclerViewMainInfo.notifyDataSetChanged()
    }
}