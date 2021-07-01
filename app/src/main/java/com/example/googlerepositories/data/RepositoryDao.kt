package com.example.googlerepositories.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repositories WHERE id = :repositoryId")
    fun getRepository(repositoryId: Long): Repository

    @Query("SELECT * FROM repositories")
    fun getAllRepositoriesWithoutFlow(): List<Repository>

    @Query("SELECT * FROM repositories")
    fun getAllRepositories(): Flow<List<Repository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateRepository(repository: Repository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<Repository>)

    @Query("DELETE FROM repositories")
    suspend fun deleteAllRepositories()

    @Query("DELETE FROM repositories")
    fun deleteAllRepositoriesWithoutFlow()
}