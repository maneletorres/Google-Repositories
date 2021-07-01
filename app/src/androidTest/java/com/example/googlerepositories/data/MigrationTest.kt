package com.example.googlerepositories.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.random.Random

// TODO: to implement!!!
@RunWith(AndroidJUnit4::class)
class MigrationTest {

    companion object {
        const val TEST_DB_NAME = "test-db"

        private val REPOSITORY: Repository = Repository(
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

    // Helper for creating Room databases and migrations
    @Rule
    var helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        RepositoryDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    // Helper for creating SQLite database in version 1
    private lateinit var mSupportSQLiteDatabase: SupportSQLiteDatabase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        // To test migrations from version 1 of the database, we need to create the database
        // with version 1 using SQLite API
        mSupportSQLiteDatabase = helper.createDatabase(TEST_DB_NAME, 1)

        // We're creating the table for every test, to ensure that the table is in the correct state
        // helper.createTable(mSupportSQLiteDatabase)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        // Clear the database after every test
        // helper.clearDatabase(mSupportSQLiteDatabase)
    }

    @Test
    @Throws(IOException::class)
    fun migrationFrom1To3_containsCorrectData() {
        // Create the database with the initial version 1 schema and insert a user
        mSupportSQLiteDatabase.apply {
            execSQL("INSERT INTO repositories VALUES(100, 'Name', 'Full name')")
            close()
        }

        // Re-open the database with version 3 and provide MIGRATION_1_2 and
        // MIGRATION_2_3 as the migration process.
        /*helper.runMigrationsAndValidate(
            TEST_DB_NAME, 3, true,
            RepositoryDatabase.MIGRATION_1_2, RepositoryDatabase.MIGRATION_2_3
        )*/

        // Get the latest, migrated, version of the database
        // Check that the correct data is in the database
        val dbRepository: Repository = getMigratedRoomDatabase().repositoryDao().getRepository(100)
        assertEquals(dbRepository.id, 1)
        assertEquals(dbRepository.name, REPOSITORY.name)
        // The date was missing in version 2, so it should be null in version 3
        // assertEquals(dbRepository.getDate(), null)
    }

    @Test
    @Throws(IOException::class)
    fun migrationFrom2To3_containsCorrectData() {
        // Create the database in version 2
        val db = helper.createDatabase(TEST_DB_NAME, 2)
        // Insert some data
        insertRepository(REPOSITORY.id, REPOSITORY.name, db)
        //Prepare for the next version
        db.close()

        // Re-open the database with version 3 and provide MIGRATION_1_2 and
        // MIGRATION_2_3 as the migration process.
        /*helper.runMigrationsAndValidate(
            TEST_DB_NAME, 3, true,
            RepositoryDatabase.MIGRATION_1_2, RepositoryDatabase.MIGRATION_2_3
        )*/

        // MigrationTestHelper automatically verifies the schema changes, but not the data validity
        // Validate that the data was migrated properly.
        val dbRepository: Repository = getMigratedRoomDatabase().repositoryDao().getRepository(100)
        assertEquals(dbRepository.id, REPOSITORY.id)
        assertEquals(dbRepository.name, REPOSITORY.name)
        // The date was missing in version 2, so it should be null in version 3
        // assertEquals(dbRepository.getDate(), null)
    }

    @Test
    @Throws(IOException::class)
    fun startInVersion3_containsCorrectData() {
        // Create the database with version 3
        val db = helper.createDatabase(TEST_DB_NAME, 3)
        // Insert some data
        insertRepository(REPOSITORY, db)
        db.close()

        // open the db with Room.
        val usersDatabase: RepositoryDatabase = getMigratedRoomDatabase()

        // verify that the data is correct
        val dbUser: Repository = usersDatabase.repositoryDao().getRepository(100)
        assertEquals(dbUser.id, REPOSITORY.id)
        assertEquals(dbUser.name, REPOSITORY.name)
        assertEquals(dbUser.fullName, REPOSITORY.fullName)
    }

    private fun getMigratedRoomDatabase(): RepositoryDatabase {
        val database: RepositoryDatabase = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RepositoryDatabase::class.java, TEST_DB_NAME
        )
            //.addMigrations(RepositoryDatabase.MIGRATION_1_2, RepositoryDatabase.MIGRATION_2_3)
            .build()
        // close the database and release any stream resources when the test finishes
        helper.closeWhenFinished(database)
        return database
    }

    private fun insertRepository(id: Long, name: String, db: SupportSQLiteDatabase) {
        val values = ContentValues()
        values.put("id", id)
        values.put("name", name)
        db.insert("users", CONFLICT_REPLACE, values)
    }

    private fun insertRepository(repository: Repository, db: SupportSQLiteDatabase) {
        val values = ContentValues()
        values.put("id", repository.id)
        values.put("name", repository.name)
        db.insert("repositories", CONFLICT_REPLACE, values)
    }
}