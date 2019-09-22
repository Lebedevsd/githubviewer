package com.lebedevsd.githubviewer.interactor

import com.lebedevsd.githubviewer.api.model.User
import com.lebedevsd.githubviewer.repository.ContributorsRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * UseCase that provides Contributors for selected repo
 */
class GetContributorsInteractor
@Inject constructor(private val repository: ContributorsRepository) {

    /**
     * @returns list of Contributors
     */
    operator fun invoke(username: String, repo: String) = getContributors(username, repo)

    private fun getContributors(username: String, repo: String): Flowable<List<User>> =
        repository.getContributors(username, repo)
}