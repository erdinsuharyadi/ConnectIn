package com.erdin.connectin.offers

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.erdin.connectin.R
import com.erdin.connectin.databinding.ItemRvOfferBinding
import com.squareup.picasso.Picasso
import java.io.Serializable

class OffersAdapter: RecyclerView.Adapter<OffersAdapter.OffersHolder>() {

    private val items = mutableListOf<OffersModel>()

    fun addList(list: List<OffersModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersHolder {
        return OffersHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_rv_offer, parent, false))
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: OffersHolder, position: Int) {
        val items = items[position]
        holder.binding.tvProfileName.text = items.nameEngineer
        holder.binding.tvProjectName.text = items.projectName
        holder.binding.tvProjectDate.text = items.created

        Picasso.get()
            .load(items.photo)
            .into(holder.binding.ivProfile)

        val mContext = holder.binding.cvOffer.context

        holder.binding.cvOffer.setOnClickListener {
            val intent = Intent(mContext, OfferDetailsActivity::class.java)
            intent.putExtra("offer_items", items)
            mContext.startActivity(intent)
        }
    }


    class OffersHolder(val binding: ItemRvOfferBinding) : RecyclerView.ViewHolder(binding.root)
}