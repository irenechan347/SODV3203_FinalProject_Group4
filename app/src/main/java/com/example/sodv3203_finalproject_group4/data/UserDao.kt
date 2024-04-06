package com.example.sodv3203_finalproject_group4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sodv3203_finalproject_group4.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from users WHERE userId = :id")
    fun getUserById(id: Int): Flow<User>

    @Query("SELECT * from users WHERE email = :email")
    fun getUserByEmail(email: String): Flow<User>

    @Query("SELECT * from users ORDER BY userId ASC")
    fun getAllUsers(): Flow<List<User>>
}