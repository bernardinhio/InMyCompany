package bernardo.bernardinhio.lovooapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bernardo.bernardinhio.lovooapp.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_modal_full_size.*

class ImageModalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_modal_full_size)

        buttonClose.setOnClickListener { finish() }

        val intentImageUrl = intent
        val imageUrl: String? = intentImageUrl?.getStringExtra("imageUrl")

        Glide.with(this)
            .load(imageUrl)
            // when there is slow internet or any network problem or image not available anymore
            .placeholder(R.drawable.loading_image)
            .fitCenter()
            .into(imageViewEventFullSize)
    }
}