package com.example.googlerepositories.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class LocalRepositoryDataSourceTest {

    companion object {
        val REPOSITORY: Repository = Repository(
            id = 100,
            name = "",
            fullName = "",
            owner = Owner(0, "", ""),
            htmlUrl = "",
            description = "",
            fork = Random.nextBoolean(),
            language = listOf("Java", "Kotlin", "Swift", "Objective-C").random(),
        )
    }

    private lateinit var mDatabase: RepositoryDatabase
    private lateinit var mDataSource: RepositoryDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        // Using an in-memory database because the information stored here disappears when the
        // process is killed:
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RepositoryDatabase::class.java
        ).build()
        mDataSource = mDatabase.repositoryDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    fun insertAndGetRepository() {
        // When inserting a new repository in the data source:
        mDataSource.insertOrUpdateRepository(REPOSITORY)

        // The repository can be retrieved:
        val dbRepository: Repository = mDataSource.getAllRepositoriesWithoutFlow().first()
        assertEquals(dbRepository.id, REPOSITORY.id)
        assertEquals(dbRepository.name, REPOSITORY.name)
        assertEquals(dbRepository.fullName, REPOSITORY.fullName)
    }

    @Test
    fun updateAndGetUser() {
        // Given that we have a user in the data source
        mDataSource.insertOrUpdateRepository(REPOSITORY)

        // When we are updating the name of the user
        val updatedUser = Repository(
            REPOSITORY.id,
            "John",
            "Doe",
            Owner(0, "", ""),
            "",
            "",
            Random.nextBoolean(),
            ""
        )
        mDataSource.insertOrUpdateRepository(updatedUser)

        // The retrieved repository has the updated username:
        val dbRepository: Repository = mDataSource.getRepository(100)
        assertEquals(dbRepository.id, REPOSITORY.id)
        assertEquals(dbRepository.name, "John")
    }

    @Test
    fun deleteAndGetUser() {
        // Given that we have a repository in the data source
        mDataSource.insertOrUpdateRepository(REPOSITORY)

        // When we are deleting all repositories
        mDataSource.deleteAllRepositoriesWithoutFlow()

        // The repository is no longer in the data source:
        val dbRepository: Repository = mDataSource.getRepository(100)
        assertNull(dbRepository)
    }
}