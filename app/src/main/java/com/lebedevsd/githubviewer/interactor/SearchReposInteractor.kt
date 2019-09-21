package com.lebedevsd.githubviewer.interactor

import com.lebedevsd.githubviewer.api.model.Repo
import com.lebedevsd.githubviewer.repository.SearchReposRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * UseCase that provides search for repos
 */
class SearchReposInteractor
@Inject constructor(private val repository: SearchReposRepository) {

    /**
     * @returns sortedBy [Repo.forks_count] list of Repos
     */
    operator fun invoke(query: String) = getProducts(query)

    private fun getProducts(query: String): Flowable<List<Repo>> =
        repository.searchRepos(query)
            .map {
                it.sortedBy { repo -> repo.forks_count }
            }

}