package com.erdin.connectin.engineers

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.erdin.connectin.FormatDate
import com.erdin.connectin.R
import com.erdin.connectin.databinding.ItemRvEngineerBinding
import com.squareup.picasso.Picasso

class EngineersAdapter : RecyclerView.Adapter<EngineersAdapter.EngineerHolder>() {

    private val items = mutableListOf<EngineersModel>()

    fun addList(list: List<EngineersModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineerHolder {
        return EngineerHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_rv_engineer, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EngineerHolder, position: Int) {
        val item = items[position]
        holder.binding.tvEngineerName.text = item.nameEngineer
        holder.binding.tvEngineerJob.text = item.job

        Picasso.get()
            .load(item.photo)
            .into(holder.binding.ivEngineerPhoto)

        val mContext = holder.binding.cvHero.context

        val formatDate = FormatDate.dateFormat(item.dob)

        holder.binding.cvHero.setOnClickListener {
            val intent = Intent(mContext, EngineerDetailsActivity::class.java)
            intent.putExtra("id_eng", item.idEngineer)
            intent.putExtra("photo_eng", item.photo)
            intent.putExtra("name_eng", item.nameEngineer)
            intent.putExtra("job_eng", item.job)
            intent.putExtra("skill_eng", item.nameSkill)
            intent.putExtra("loc_eng", item.location)
            intent.putExtra("email_eng", item.email)
            intent.putExtra("dob_eng", formatDate)
            intent.putExtra("showcase_eng", item.showcase)
            intent.putExtra("total_project", item.totalProject)
            intent.putExtra("success_project", item.successProject)
            intent.putExtra("rate_project", item.rateSuccess)
            mContext.startActivity(intent)
        }
    }

    class EngineerHolder(val binding: ItemRvEngineerBinding) :RecyclerView.ViewHolder(binding.root)

}