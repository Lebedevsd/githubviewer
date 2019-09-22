package com.lebedevsd.githubviewer.repository

import com.lebedevsd.githubviewer.api.GithubApi
import com.lebedevsd.githubviewer.api.model.User
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Repository of contributors
 */
class ContributorsRepository @Inject constructor(
    private val remoteApi: GithubApi
) {
    /**
     * @Returns list of all contributors for the repo
     */
    fun getContributors(userName: String, repoName: String): Flowable<List<User>> {
        return remoteApi.getContributors(userName, repoName)
            .toFlowable()
    }
}