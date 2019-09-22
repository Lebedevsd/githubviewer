package com.lebedevsd.githubviewer.ui.searchrepos

import androidx.appcompat.widget.SearchView
import com.lebedevsd.githubviewer.base.epoxy.ViewStateEpoxyController
import com.lebedevsd.githubviewer.base.ui.LCE
import com.lebedevsd.githubviewer.itemRepo
import com.lebedevsd.githubviewer.itemSearch
import com.lebedevsd.githubviewer.loading
import javax.inject.Inject

class SearchReposController @Inject constructor() :
    ViewStateEpoxyController<SearchReposViewState>() {
    override fun buildModels(state: LCE<SearchReposViewState>) {
        state.content.itemSearch.apply {
            itemSearch {
                id(this.hashCode())
                itemSearch(this@apply)
                queryListener(
                    object : SearchView.OnQueryTextListener {
                        override fun onQueryTextChange(newText: String?): Boolean {
                            return true
                        }

                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if (!query.isNullOrEmpty()) {
                                state.content.itemSearch.onQueryTextListener?.invoke(query)
                            }
                            return true
                        }
                    }
                )
            }
        }
        state.content.reposState.repos.forEach {
            itemRepo {
                id(it.hashCode())
                itemRepo(it)
                clickListener { model, _, _, _ ->
                    state.content.onClick?.invoke(
                        NavigationData(
                            model.itemRepo().ownerUsername,
                            model.itemRepo().name
                        )
                    )
                }
            }
        }
        if (state.isLoading()) {
            loading {
                id("loading")
                matchParent(state.content.reposState.repos.isEmpty())
            }
        }
    }
}
