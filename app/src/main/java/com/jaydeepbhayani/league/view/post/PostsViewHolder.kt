package com.jaydeepbhayani.league.view.post

import com.bumptech.glide.Glide
import com.jaydeepbhayani.league.R
import com.jaydeepbhayani.league.data.model.PostItemModel
import com.jaydeepbhayani.league.databinding.ItemListPostBinding
import com.jaydeepbhayani.league.util.BaseViewHolder
import com.jaydeepbhayani.league.util.animatePost

class PostsViewHolder(private val binding: ItemListPostBinding) :
    BaseViewHolder<PostItemModel>(binding.root) {

    override fun bind(item: PostItemModel) {
        with(binding) {
            tvValueUsername.text = item.name
            tvValueTitle.text = item.title
            tvValueDesc.text = item.body
            Glide.with(itemView.context)
                .load(item.image)
                .error(R.drawable.ic_baseline_image_24)
                .into(ivAvatar)
        }
    }

    override fun setAnimation(item: Int) {
        binding.root.animatePost(android.R.anim.slide_in_left)
    }
}

