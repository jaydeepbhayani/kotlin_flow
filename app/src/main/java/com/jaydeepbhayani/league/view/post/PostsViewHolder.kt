package com.jaydeepbhayani.league.view.post

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jaydeepbhayani.league.R
import com.jaydeepbhayani.league.data.model.PostItemModel
import com.jaydeepbhayani.league.data.model.PostsResponse
import com.jaydeepbhayani.league.util.BaseViewHolder

class PostsViewHolder(itemView: View) : BaseViewHolder(itemView) {
    var ivAvatar: ImageView
    var tvUsername: TextView
    var tvTitle: TextView
    var tvBody: TextView

    init {
        ivAvatar = itemView.findViewById(R.id.iv_avatar)
        tvUsername = itemView.findViewById(R.id.tv_value_username)
        tvTitle = itemView.findViewById(R.id.tv_value_title)
        tvBody = itemView.findViewById(R.id.tv_value_desc)
    }

    override fun bind(item: Any) {
        val data = item as PostItemModel
        tvUsername.text = data.name
        tvTitle.text = data.title
        tvBody.text = data.body

        Glide.with(itemView.context)
            .load(data.image)
            .error(R.drawable.ic_baseline_image_24)
            .into(ivAvatar)
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int, item: PostsResponse)
        fun onItemLongClick(position: Int, itemView: View, item: PostsResponse)
    }
}

var onItemClick: PostsViewHolder.OnItemClickListener? = null

