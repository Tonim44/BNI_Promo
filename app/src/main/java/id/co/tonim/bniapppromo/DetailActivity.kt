package id.co.tonim.bniapppromo

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import id.co.tonim.bniapppromo.data.PromoItem
import id.co.tonim.bniapppromo.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var promoItem : PromoItem

    companion object {
        const val EXTRA_PROMO = "extra_promo"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        promoItem = intent.getParcelableExtra<PromoItem>(EXTRA_PROMO) as PromoItem

        val title = promoItem.name
        val description = promoItem.desc
        val date = promoItem.date
        val urlToImage = promoItem.imageUrl

        binding.back.setOnClickListener(View.OnClickListener { onBackPressed() })

        binding.titleTextView.text = title
        binding.description.text = description
        binding.dateTextView.text = date

        if (urlToImage.isNotEmpty()) {
            Picasso.get().load(urlToImage).into(binding.imageView)
        } else {
            binding.imageView.setImageResource(R.drawable.imagetest)
        }

    }
}