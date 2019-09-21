package com.lebedevsd.githubviewer.api.model

data class User(
    val avatar_url: String,
    val gravatar_id: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val received_events_url: String,
    val type: String,
    val url: String
)