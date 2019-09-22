package com.lebedevsd.githubviewer.ui.searchrepos

import com.lebedevsd.githubviewer.base.epoxy.ViewStateEpoxyController
import com.lebedevsd.githubviewer.base.ui.LCE
import com.lebedevsd.githubviewer.itemRepo
import com.lebedevsd.githubviewer.loading
import javax.inject.Inject

class SearchReposController @Inject constructor() :
    ViewStateEpoxyController<SearchReposViewState>() {
    override fun buildModels(state: LCE<SearchReposViewState>) {
        state.content.repos.forEach {
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
                matchParent(state.content.repos.isEmpty())
            }
        }
    }
}
