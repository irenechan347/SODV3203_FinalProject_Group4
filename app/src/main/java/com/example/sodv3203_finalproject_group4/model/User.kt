package com.example.sodv3203_finalproject_group4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val displayName: String,
    val name: String,
    val email: String,
    val phoneNo: String,
    val password: String
)