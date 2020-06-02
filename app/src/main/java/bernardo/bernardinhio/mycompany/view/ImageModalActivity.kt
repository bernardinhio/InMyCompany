package bernardo.bernardinhio.mycompany.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bernardo.bernardinhio.mycompany.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_modal_full_size.*

class ImageModalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_modal_full_size)

        buttonClose.setOnClickListener { finish() }

        val imageUrl: String? = this.intent?.getStringExtra("imageUrl")

        Glide.with(this)
            .load(imageUrl)
            .fitCenter()
            .placeholder(R.drawable.loading_image)
            .into(imageViewEventFullSize)
    }
}