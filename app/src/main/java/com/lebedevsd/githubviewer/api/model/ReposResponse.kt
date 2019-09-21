package com.lebedevsd.githubviewer.api.model

data class ReposResponse(
    val incomplete_results: Boolean,
    val items: List<Repo>,
    val total_count: Int
)