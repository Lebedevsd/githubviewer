package com.lebedevsd.githubviewer.api

import com.lebedevsd.githubviewer.api.model.ReposResponse
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject


/**
 * Provides recipes content from network API
 */
class GithubApi @Inject constructor(
    moshi: Moshi,
    okHttpClient: OkHttpClient
) {
    private val service: GithubRestApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(GithubRestApi.ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        service = retrofit.create(GithubRestApi::class.java)
    }

    /**
     * Performs network call to search for Repos
     */
    fun searchRepos(request: String): Single<ReposResponse> {
        return Single.create { emitter ->
            val response = service.searchRepos(request).execute()

            if (response.isSuccessful) {
                response.body()?.let { emitter.onSuccess(it) }
            } else {
                emitter.onError(RuntimeException(response.message()))
            }
        }
    }

    /**
     * Performs network call to get repo collaborators
     */
    fun getCollaborators(
        userName: String, repoName: String
    ) {

    }

    /**
     * Performs network call to get user repos
     */
    fun getUserRepos(
        username: String
    ) {
    }
}
