package id.co.tonim.bniapppromo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.co.tonim.bniapppromo.DetailActivity
import id.co.tonim.bniapppromo.data.PromoItem
import id.co.tonim.bniapppromo.R

class PromoAdapter(private val promoList: List<PromoItem>) :
    RecyclerView.Adapter<PromoAdapter.PromoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_promo, parent, false)
        return PromoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val currentItem = promoList[position]
        holder.name.text = currentItem.name
        holder.date.text = currentItem.date

        if (currentItem.imageUrl.isNotEmpty()) {
            Picasso.get().load(currentItem.imageUrl).into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.imagetest)
        }

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_PROMO, currentItem)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return promoList.size
    }

    inner class PromoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.titleTextView)
        val date: TextView = itemView.findViewById(R.id.dateTextView)
        val image: ImageView = itemView.findViewById(R.id.imageView)
    }
}

