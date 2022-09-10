package com.jaydeepbhayani.league.view.post

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaydeepbhayani.league.data.model.PostItemModel
import com.jaydeepbhayani.league.databinding.ItemListPostBinding
import com.jaydeepbhayani.league.util.BaseViewHolder

class PostsListAdapter : RecyclerView.Adapter<BaseViewHolder<PostItemModel>>() {

    private var dataList: MutableList<PostItemModel> = ArrayList()

    // Allows to remember the last item shown on screen
    private var lastPosition = -1

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: List<PostItemModel>) {
        this.dataList = dataList as MutableList<PostItemModel>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PostItemModel> {
        return PostsViewHolder(
            ItemListPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<PostItemModel>, position: Int) {
        val data = dataList[position]
        holder.bind(data)
        if (holder.bindingAdapterPosition > lastPosition) {
            holder.setAnimation(android.R.anim.slide_in_left)
            lastPosition = holder.bindingAdapterPosition
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}