package com.lebedevsd.githubviewer.ui.repocontributors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebedevsd.githubviewer.R
import com.lebedevsd.githubviewer.base.ui.BaseFragment
import com.lebedevsd.githubviewer.databinding.RepoContributorsFragmentBinding
import timber.log.Timber

class RepoContributorsFragment :
    BaseFragment<RepoContributorsViewState, RepoContributorsViewModel, RepoContributorsFragmentBinding>() {
    override val viewModelClass: Class<RepoContributorsViewModel> = RepoContributorsViewModel::class.java
    override val layoutId: Int = R.layout.repo_contributors_fragment

    private val args: RepoContributorsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadContributors(args.ownerName, args.repoName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
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