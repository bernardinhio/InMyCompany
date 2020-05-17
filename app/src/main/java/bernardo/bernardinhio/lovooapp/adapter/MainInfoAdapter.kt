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
import bernardo.bernardinhio.lovooapp.datamodel.ImageItemDataModel
import bernardo.bernardinhio.lovooapp.datamodel.MainInfoItemDataModel
import kotlinx.android.synthetic.main.item_recyclerview_main_info.view.*

class MainInfoAdapter(
    val context: Context,
    val arrayListData: List<MainInfoItemDataModel> = mutableListOf<MainInfoItemDataModel>()
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
            textViewRoomNumber.text = "Num: #${itemData.roomNumber}"
            textViewOfficeLevel.text = "F: ${itemData.officeLevel}"
            textViewFactTitle.text = itemData.factTitle
            textViewFactText.text = itemData.factText
        }

        if (itemData.factImageList.isNotEmpty()){
            populateRecyclerViewImages(
                viewHolder.recyclerViewImages, itemData.factImageList
            )
        }
    }

    private fun matchDepartmentColor(department: String): Drawable? {
        return when(department){
            "all" -> ColorDrawable(Integer.decode("#00ccff"))
            "support" -> ColorDrawable(Integer.decode("#00e64d"))
            "hr" -> ColorDrawable(Integer.decode("#ffff00"))
            "engineering" -> ColorDrawable(Integer.decode("#ffcc66"))
            "management" -> ColorDrawable(Integer.decode("#ff0000"))
            "marketing" -> ColorDrawable(Integer.decode("#ff66ff"))
            "data" -> ColorDrawable(Integer.decode("#b3b3ff"))
            else -> {ColorDrawable(Integer.decode("#000000"))}
        }
    }

    private fun populateRecyclerViewImages(
        recyclerViewImages: RecyclerView?,
        imageList: List<String>
    ) {

        // setup the inner Horizontal RecyclerView
        recyclerViewImages?.setHasFixedSize(false)
        recyclerViewImages?.layoutManager = LinearLayoutManager(context.applicationContext, LinearLayoutManager.HORIZONTAL,false)

        val dataImageItems: ArrayList<ImageItemDataModel> = ArrayList<ImageItemDataModel>()

        val adapterRecyclerViewImages = ImagesAdapter(context, dataImageItems)
        recyclerViewImages?.adapter = adapterRecyclerViewImages

        imageList.forEach {
            dataImageItems.add(ImageItemDataModel(it))
        }

        adapterRecyclerViewImages.notifyDataSetChanged()
    }

    inner class MainInfoRecyclerViewViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val container = itemView.containerMainInfoItem
        val textViewName = itemView.tvName
        val textViewType = itemView.tvType
        val textViewRoomNumber = itemView.tvRoomNumber
        val textViewOfficeLevel = itemView.tvOfficeLevel
        val textViewFactTitle = itemView.tvFactTitle
        val textViewFactText = itemView.tvFactText
        val recyclerViewImages = itemView.recyclerViewImages
    }
}