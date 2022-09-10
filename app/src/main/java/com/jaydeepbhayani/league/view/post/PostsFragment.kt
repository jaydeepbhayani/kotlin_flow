package com.jaydeepbhayani.league.view.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaydeepbhayani.league.R
import com.jaydeepbhayani.league.data.model.PostItemModel
import com.jaydeepbhayani.league.databinding.FragmentPostsBinding
import com.jaydeepbhayani.league.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment() {
    private val viewModel by viewModels<PostsViewModel>()

    private lateinit var postsListAdapter: PostsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPostsBinding.inflate(inflater, container, false).withBinding {
        setupMixedTransitions()
        setupState()
        setupListeners()
    }

    private fun FragmentPostsBinding.setupListeners() {}

    private fun FragmentPostsBinding.setupState() {
        tb.title = context?.stringResource(R.string.posts)
        setupAdapter()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postItem.collect {
                    setupPosts(it)
                }
            }
        }
    }

    private fun FragmentPostsBinding.setupPosts(postItem: UiState<List<PostItemModel>>) {
        val posts = postItem.getOrDefault(emptyList())
        when (postItem) {
            is UiState.Loading -> {
                loader.show()
            }
            is UiState.Success -> {
                loader.hide()
                rvPosts.show()
                postsListAdapter.setData(posts)
            }
        }
    }

    private fun FragmentPostsBinding.setupAdapter() {
        postsListAdapter = PostsListAdapter()
        rvPosts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvPosts.adapter = postsListAdapter
        rvPosts.edgeEffectFactory = BounceEdgeEffectFactory()
        rvPosts.itemAnimator = VerticalListItemAnimator()
        rvPosts.scheduleLayoutAnimation()
    }

    companion object {
        val TAG = PostsFragment::class.simpleName
    }
}