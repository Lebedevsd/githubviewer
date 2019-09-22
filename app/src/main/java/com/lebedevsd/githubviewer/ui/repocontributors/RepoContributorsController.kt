package com.lebedevsd.githubviewer.ui.repocontributors

import com.lebedevsd.githubviewer.base.epoxy.ViewStateEpoxyController
import com.lebedevsd.githubviewer.base.ui.LCE
import com.lebedevsd.githubviewer.itemUser
import com.lebedevsd.githubviewer.loading
import javax.inject.Inject

class RepoContributorsController @Inject constructor() :
    ViewStateEpoxyController<RepoContributorsViewState>() {
    override fun buildModels(state: LCE<RepoContributorsViewState>) {
        state.content.users.forEach {
            itemUser {
                id(it.hashCode())
                itemUser(it)
            }
        }
        if (state.isLoading()) {
            loading {
                id("loading")
                matchParent(state.content.users.isEmpty())
            }
        }
    }
}
