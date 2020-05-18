package bernardo.bernardinhio.lovooapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bernardo.bernardinhio.lovooapp.R
import bernardo.bernardinhio.lovooapp.datamodel.ImageItemDataModel
import bernardo.bernardinhio.lovooapp.view.ImageModalActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recyclerview_images.view.*

class ImagesAdapter(
    val context: Context,
    val arrayListData: List<ImageItemDataModel> = mutableListOf<ImageItemDataModel>()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = arrayListData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageRecyclerViewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_images, parent, false))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemData = arrayListData.get(position)

        (viewHolder as ImageRecyclerViewViewHolder).apply {
            Glide.with(context)
                .load(itemData.imageUrl)
                .centerCrop()
                .into(viewHolder.imageViewSocialEventPhoto)
        }

        viewHolder.imageViewSocialEventPhoto.setOnClickListener {
            val intent = Intent(context, ImageModalActivity::class.java)
            intent.putExtra("imageUrl", itemData.imageUrl)
            context.startActivity(intent)
        }
    }

    inner class ImageRecyclerViewViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val imageViewSocialEventPhoto = itemView.imageViewEventPreview
    }

}