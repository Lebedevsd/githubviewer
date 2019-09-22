package com.lebedevsd.githubviewer.repository

import com.lebedevsd.githubviewer.api.GithubApi
import com.lebedevsd.githubviewer.api.model.ReposResponse
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Repository of repository search
 */
class SearchReposRepository @Inject constructor(
    private val remoteApi: GithubApi
) {
    /**
     * @Returns list of Repos for the particular [query]
     */
    fun searchRepos(query: String = "", page: Int = 1): Flowable<ReposResponse> {
        return remoteApi.searchRepos(query, page)
            .toFlowable()
    }
}