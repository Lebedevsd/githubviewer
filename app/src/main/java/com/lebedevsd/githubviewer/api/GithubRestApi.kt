package com.lebedevsd.githubviewer.api

import com.lebedevsd.githubviewer.api.model.ReposResponse
import com.lebedevsd.githubviewer.api.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Provides Github entities from network API
 */
interface GithubRestApi {

    /**
     * Performs network call to search for reposState
     */
    @GET("search/repositories")
    fun searchRepos(@Query("q") request: String, @Query("page") page: Int): Call<ReposResponse>

    /**
     * Performs network call to get all contributors of the Repo
     */
    @GET("repos/{owner}/{repo}/contributors")
    fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<User>>

    companion object {
        /**
         * EndPoint to receive recipes
         */
        //TODO note that token can expire
        val ENDPOINT = "https://api.github.com/"
    }
}
