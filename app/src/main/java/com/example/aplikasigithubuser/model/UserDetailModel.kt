package com.example.aplikasigithubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class UserDetailModel(
    val login: String? = null,
    val id: Long? = null,
    val avatar_url: String? = null,
    val followers_url: String? = null,
    val following_url: String? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val public_repos: Int? = null,
    val followers: Int? = null,
    val following: Int? = null
) : Serializable

