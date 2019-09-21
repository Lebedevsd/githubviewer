package com.lebedevsd.githubviewer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebedevsd.githubviewer.R
import com.lebedevsd.githubviewer.base.recyclerview.OnLoadMoreScrollListener
import com.lebedevsd.githubviewer.base.ui.BaseFragment
import com.lebedevsd.githubviewer.databinding.MainFragmentBinding
import timber.log.Timber

class SearchReposFragment : BaseFragment<SearchReposViewState, SearchReposViewModel, MainFragmentBinding>() {
    override val viewModelClass: Class<SearchReposViewModel> = SearchReposViewModel::class.java
    override val layoutId: Int = R.layout.main_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")
//        viewModel.navigateToDetail.observe(this, Observer { sha ->
//            Timber.d("Item with sha: %s was clicked", sha)
//            findNavController().navigate(CommitListFragmentDirections.actionCommitListFragmentToCommitDetailFragment(sha))
//        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            addOnScrollListener(object : OnLoadMoreScrollListener(resources.getInteger(R.integer.load_threshold)) {
                override fun onLoadMore() {
                    controller.currentData?.let {
                        viewModel.nextPage(it.content.page + 1)
                    }
                }
            })
            adapter = controller.adapter
        }

        savedInstanceState?.let { state ->
            val position = state.getInt("position")
            controller.adapter.addModelBuildListener {
                Timber.d("Restoring last recyclerview position: %d", position)
                binding.recyclerview.layoutManager?.scrollToPosition(position)
            }
        }

        return view
    }
}