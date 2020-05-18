package bernardo.bernardinhio.lovooapp.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
            textViewRoomNumber.text = "Room: #${itemData.roomNumber}"
            textViewOfficeLevel.text = "Floor: ${itemData.officeLevel}"
            textViewFactTitle.text = itemData.factTitle
            textViewFactText.text = itemData.factText
        }

        if (itemData.factImageList.isNotEmpty()){
            viewHolder.recyclerViewImages.visibility = View.VISIBLE
            viewHolder.textViewFactTitle.visibility = View.VISIBLE
            viewHolder.textViewFactText.visibility = View.VISIBLE
            populateRecyclerViewImages(
                viewHolder.recyclerViewImages, itemData.factImageList
            )
        } else {
            viewHolder.recyclerViewImages.visibility = View.GONE
            viewHolder.textViewFactTitle.visibility = View.GONE
            viewHolder.textViewFactText.visibility = View.GONE
        }

    }

    private fun matchDepartmentColor(department: String): Drawable? {
        return when(department){
            "all" -> ContextCompat.getDrawable(context, R.drawable.background_card_department_all)
            "support" -> ContextCompat.getDrawable(context, R.drawable.background_card_department_support)
            "hr" -> ContextCompat.getDrawable(context, R.drawable.background_card_department_hr)
            "engineering" -> ContextCompat.getDrawable(context, R.drawable.background_card_department_engineering)
            "management" -> ContextCompat.getDrawable(context, R.drawable.background_card_department_management)
            "marketing" -> ContextCompat.getDrawable(context, R.drawable.background_card_department_marketing)
            "data" -> ContextCompat.getDrawable(context, R.drawable.background_card_department_data)
            else -> ContextCompat.getDrawable(context, R.drawable.background_card_department_else)
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