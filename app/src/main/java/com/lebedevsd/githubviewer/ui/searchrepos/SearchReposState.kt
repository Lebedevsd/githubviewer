package com.lebedevsd.githubviewer.ui.searchrepos

data class SearchReposViewState(
    val page: Int = 1,
    val reposState: ReposState = ReposState(),
    val onClick: ((navigationData: NavigationData) -> Unit)? = null,
    val itemSearch: ItemSearch = ItemSearch()
)

data class ReposState(
    val repos: List<ItemRepo> = emptyList(),
    val hasNext: Boolean = false
)

data class ItemRepo(
    val full_name: String,
    val name: String,
    val ownerUsername: String,
    val ownerAvatar: String,
    val language: String,
    val forks_count: String
)

data class ItemSearch(
    val query: String = "",
    val onQueryTextListener: ((newQuery: String) -> Unit)? = null
)

data class NavigationData(
    val ownerName: String,
    val repoName: String
)