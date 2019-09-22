package com.lebedevsd.githubviewer.ui.repocontributors

data class RepoContributorsViewState(
    val users: List<ItemUser> = emptyList()
)

data class ItemUser(
    val full_name: String,
    val avatar: String
)