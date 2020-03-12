package com.erdin.connectin.projects

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.erdin.connectin.R
import com.erdin.connectin.databinding.ItemRvProjectBinding
import java.text.SimpleDateFormat
import java.util.*


class ProjectsAdapter : RecyclerView.Adapter<ProjectsAdapter.ProjectHolder>() {

    private val items = mutableListOf<ProjectsModel>()

    fun addList(list: List<ProjectsModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        return ProjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_rv_project, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val item = items[position]
        val bind = holder.binding

        val myFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
        val myDate: Date = myFormat.parse(item.created)

        bind.tvProjectName.text = item.projectName
        bind.tvProjectDate.text = myDate.toString()
        val mContext = bind.cvProjects.context
        bind.cvProjects.setOnClickListener {
            val intent = Intent(mContext, ProjectDetailsActivity::class.java)
            intent.putExtra("project_items", item)
            mContext.startActivity(intent)
        }


    }

    class ProjectHolder(val binding: ItemRvProjectBinding) : RecyclerView.ViewHolder(binding.root)
}