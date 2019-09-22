package com.lebedevsd.githubviewer.repository

import com.lebedevsd.githubviewer.api.GithubApi
import com.lebedevsd.githubviewer.api.model.Repo
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
    fun searchRepos(query: String = ""): Flowable<List<Repo>> {
        return remoteApi.searchRepos(query)
            .toFlowable()
            .map {
                it.items
            }
    }
}