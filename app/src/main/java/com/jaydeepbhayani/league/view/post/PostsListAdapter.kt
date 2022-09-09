package com.jaydeepbhayani.league.view.post

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.jaydeepbhayani.league.R
import com.jaydeepbhayani.league.data.model.PostItemModel
import com.jaydeepbhayani.league.util.BaseViewHolder
import com.jaydeepbhayani.league.view.common.EmptyItemViewHolder

class PostsListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private var dataList: MutableList<PostItemModel> = ArrayList()

    // Allows to remember the last item shown on screen
    private var lastPosition = -1

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: List<PostItemModel>) {
        if (dataList.isEmpty()) {
            this.dataList.clear()
            this.dataList.add(
                PostItemModel(
                    title = null, body = null, image = null, name = null,
                )
            )
        } else {
            this.dataList = dataList as MutableList<PostItemModel>
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        /*val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_notes, parent, false)
        return BaseViewHolder(itemView)*/
        lateinit var holder: BaseViewHolder
        when (viewType) {
            TYPE_POSTS_VIEW -> holder =
                PostsViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list_post, parent, false)
                )
            TYPE_EMPTY_VIEW -> holder =
                EmptyItemViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.placeholder_item, parent, false)
                )
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
        setAnimation(holder.itemView, position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList[0].title.isNullOrEmpty()) R.layout.placeholder_item
        else R.layout.item_list_post
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setOnItemClickListener(onItemClickListener: PostsViewHolder.OnItemClickListener) {
        onItemClick = onItemClickListener
    }

    /**
     * Here is the key method to apply the animation
     */
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(
                viewToAnimate.context,
                android.R.anim.slide_in_left
            )
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    companion object {
        const val TYPE_POSTS_VIEW = R.layout.item_list_post
        const val TYPE_EMPTY_VIEW = R.layout.placeholder_item
    }
}