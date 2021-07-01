package com.example.googlerepositories.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.googlerepositories.data.LocalRepositoryDataSourceTest.Companion.REPOSITORY
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var repositoryDao: RepositoryDao
    private lateinit var db: RepositoryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RepositoryDatabase::class.java
        ).build()
        repositoryDao = db.repositoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        repositoryDao.insertOrUpdateRepository(REPOSITORY)
        val repository = repositoryDao.getRepository(100)
        assertThat(repository, equalTo(REPOSITORY))
    }
}