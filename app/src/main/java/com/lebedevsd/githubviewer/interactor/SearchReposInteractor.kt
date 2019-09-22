package com.lebedevsd.githubviewer.interactor

import com.lebedevsd.githubviewer.api.model.ReposResponse
import com.lebedevsd.githubviewer.repository.SearchReposRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * UseCase that provides search for reposState
 */
class SearchReposInteractor
@Inject constructor(private val repository: SearchReposRepository) {

    /**
     * @returns list of Repos
     */
    operator fun invoke(query: String, page: Int = 1) = getRepos(query, page)

    private fun getRepos(query: String, page: Int): Flowable<ReposResponse> =
        repository.searchRepos(query, page)
}