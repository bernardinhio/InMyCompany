package bernardo.bernardinhio.lovooapp.view

import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bernardo.bernardinhio.lovooapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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