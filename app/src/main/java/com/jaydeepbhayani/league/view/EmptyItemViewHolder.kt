package com.jaydeepbhayani.league.view

import android.view.View
import android.widget.TextView
import com.jaydeepbhayani.league.R
import com.jaydeepbhayani.league.util.BaseViewHolder
import com.jaydeepbhayani.league.util.textStyle

class EmptyItemViewHolder(itemView: View) : BaseViewHolder(itemView) {
    //var itemContainer: View
    //var animationView: LottieAnimationView
    var tvPlaceholder: TextView

    init {
        //itemContainer = itemView.findViewById(R.id.itemContainer)
        //animationView = itemView.findViewById(R.id.animationView)
        tvPlaceholder = itemView.findViewById(R.id.tvPlaceholder)
    }

    override fun bind(item: Any) {
        //animationView.setAnimation(FILE_ANIMATION_SEARCH_RED)
        tvPlaceholder.text = itemView.context.getString(R.string.no_posts_found)
            .textStyle(itemView.context, colorInt = R.color.colorSecondary)
    }


}