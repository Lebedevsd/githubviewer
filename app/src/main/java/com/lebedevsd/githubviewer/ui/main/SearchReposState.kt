package com.lebedevsd.githubviewer.ui.main

data class SearchReposViewState(
    val query: String = "",
    val page: Int = 0,
    val repos: List<ItemRepo> = emptyList(),
    val onClick: ((hashcode: Int) -> Unit)? = null
)

data class ItemRepo(
    val full_name: String,
    val name: String,
    val ownerUsername: String,
    val ownerAvatar: String,
    val language: String,
    val forks_count: String
)