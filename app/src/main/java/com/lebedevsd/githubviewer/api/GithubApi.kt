package com.lebedevsd.githubviewer.api

import com.lebedevsd.githubviewer.api.model.ReposResponse
import com.lebedevsd.githubviewer.api.model.User
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Provides recipes content from network API
 */
@Singleton
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
    fun searchRepos(request: String, page: Int): Single<ReposResponse> {
        return Single.create { emitter ->
            val response = service.searchRepos(request, page).execute()

            if (response.isSuccessful) {
                response.body()?.let { emitter.onSuccess(it) }
            } else {
                emitter.onError(RuntimeException(response.message()))
            }
        }
    }

    /**
     * Performs network call to get repo contributors
     */
    fun getContributors(
        userName: String, repoName: String
    ): Single<List<User>> {
        return Single.create { emitter ->
            val response = service.getContributors(userName, repoName).execute()

            if (response.isSuccessful) {
                response.body()?.let { emitter.onSuccess(it) }
            } else {
                emitter.onError(RuntimeException(response.message()))
            }
        }
    }
}
