package com.lebedevsd.githubviewer.creator

import com.lebedevsd.githubviewer.api.model.Repo
import com.lebedevsd.githubviewer.api.model.ReposResponse
import com.lebedevsd.githubviewer.api.model.User

fun testUser(
    avatar_url: String = "",
    gravatar_id: String = "",
    id: Int = 0,
    login: String = "",
    node_id: String = "",
    received_events_url: String = "",
    type: String = "",
    url: String = ""
) =
    User(
        avatar_url,
        gravatar_id,
        id,
        login,
        node_id,
        received_events_url,
        type,
        url
    )

fun testReposResponse(
    incomplete_results: Boolean = false,
    items: List<Repo> = emptyList(),
    total_count: Int = 0
) =
    ReposResponse(
        incomplete_results,
        items,
        total_count
    )

fun testRepo(
    created_at: String = "",
    default_branch: String = "",
    description: String = "",
    fork: Boolean = false,
    forks_count: Int = 0,
    full_name: String = "",
    homepage: String = "",
    html_url: String = "",
    id: Int = 0,
    language: String? = "",
    master_branch: String = "",
    name: String = "",
    node_id: String = "",
    open_issues_count: Int = 0,
    owner: User = testUser(),
    `private`: Boolean = false,
    pushed_at: String = "",
    score: Double = 0.0,
    size: Int = 0,
    stargazers_count: Int = 0,
    updated_at: String = "",
    url: String = "",
    watchers_count: Int = 0

) = Repo(
    created_at,
    default_branch,
    description,
    fork,
    forks_count,
    full_name,
    homepage,
    html_url,
    id,
    language,
    master_branch,
    name,
    node_id,
    open_issues_count,
    owner,
    `private`,
    pushed_at,
    score,
    size,
    stargazers_count,
    updated_at,
    url,
    watchers_count
)