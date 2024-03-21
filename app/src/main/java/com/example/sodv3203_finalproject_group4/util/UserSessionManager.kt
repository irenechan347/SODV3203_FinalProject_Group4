package com.example.sodv3203_finalproject_group4.util

object UserSessionManager {
    private var userId: Int? = null

    fun login(userId: Int) {
        this.userId = userId
    }

    fun logout() {
        userId = null
    }

    fun getCurrentUserId(): Int? = userId

    fun isUserLoggedIn(): Boolean = userId != null
}