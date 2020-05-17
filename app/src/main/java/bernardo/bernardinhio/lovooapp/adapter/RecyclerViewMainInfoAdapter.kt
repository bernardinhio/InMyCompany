package bernardo.bernardinhio.lovooapp.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bernardo.bernardinhio.lovooapp.R
import bernardo.bernardinhio.lovooapp.datamodel.RecyclerViewImageDataModel
import bernardo.bernardinhio.lovooapp.datamodel.RecyclerViewMainInfoDataModel

class RecyclerViewMainInfoAdapter(
    val context: Context,
    val arrayListData: List<RecyclerViewMainInfoDataModel> = mutableListOf<RecyclerViewMainInfoDataModel>()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = arrayListData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainInfoRecyclerViewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_main_info, parent, false))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemData = arrayListData.get(position)

        (viewHolder as MainInfoRecyclerViewViewHolder).apply {
            textViewName.text = itemData.name
            container.background = matchDepartmentColor(itemData.department)
            textViewType.text = itemData.type
            textViewRoomNumber.text = "num: #${itemData.roomNumber}"
            textViewOfficeLevel.text = "F: ${itemData.officeLevel}"
            textViewFactTitle.text = itemData.factTitle
            textViewFactText.text = itemData.factText
        }

        if (itemData.factImageList.isNotEmpty()){
            populateRecyclerViewImages(
                (viewHolder as MainInfoRecyclerViewViewHolder).recyclerViewImages,
                itemData.factImageList
            )
        }
    }

    private fun matchDepartmentColor(department: String): Drawable? {
        return when(department){
            "" -> ColorDrawable(Integer.decode("FF6666"))
            "shit" -> ColorDrawable(Integer.decode("FF6666"))
            "shitt" -> ColorDrawable(Integer.decode("FF6666"))
            else -> ColorDrawable(Integer.decode("FF6666"))
        }
    }

    private fun populateRecyclerViewImages(
        recyclerViewImages: RecyclerView?,
        imageList: List<String>
    ) {

        // setup the inner Horizontal RecyclerView
        recyclerViewImages?.setHasFixedSize(false)
        recyclerViewImages?.layoutManager = LinearLayoutManager(context.applicationContext, LinearLayoutManager.HORIZONTAL,false)

        val dataRecyclerViewImages: ArrayList<RecyclerViewImageDataModel> = ArrayList<RecyclerViewImageDataModel>()

        val adapterRecyclerViewImages = RecyclerViewImagesAdapter(context, dataRecyclerViewImages)
        recyclerViewImages?.adapter = adapterRecyclerViewImages

        imageList.forEach {
            dataRecyclerViewImages.add(RecyclerViewImageDataModel(it))
        }

        adapterRecyclerViewImages.notifyDataSetChanged()
    }

    inner class MainInfoRecyclerViewViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val container = itemView.findViewById<ConstraintLayout>(R.id.title)
        val textViewName = itemView.findViewById<TextView>(R.id.title)
        val textViewType = itemView.findViewById<TextView>(R.id.title)
        val textViewRoomNumber = itemView.findViewById<TextView>(R.id.title)
        val textViewOfficeLevel = itemView.findViewById<TextView>(R.id.title)
        val textViewFactTitle = itemView.findViewById<TextView>(R.id.title)
        val textViewFactText = itemView.findViewById<TextView>(R.id.title)
        val recyclerViewImages = itemView.findViewById<RecyclerView>(R.id.title)
    }
}