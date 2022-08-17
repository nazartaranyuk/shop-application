package com.nazartaraniuk.shopapplication

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nazartaraniuk.shopapplication.data.db.FavoritesDatabase
import com.nazartaraniuk.shopapplication.data.db.MIGRATION_1_2_TEST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoritesDatabaseMigrationsTest {

    private var database: SupportSQLiteDatabase? = null

    @JvmField
    @Rule
    val migrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        FavoritesDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migration1to2() {
        database = migrationTestHelper.createDatabase("test-db", 1)
            .apply {
                execSQL(
                    """
                    INSERT INTO favorites VALUES (
                    'Electronics',
                    'Description',
                    1,
                    'some image url',
                    0.0,
                    0,
                    0.0,
                    'title',
                    'new field'
                    )
                """.trimIndent()
                )
                close()
            }
        database = migrationTestHelper.runMigrationsAndValidate(
            "test-db",
            2,
            true,
            MIGRATION_1_2_TEST
        )

        val favoritesDatabase = Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            FavoritesDatabase::class.java,
            "test-db"
        )
            .allowMainThreadQueries()
            .build()

        runBlocking(Dispatchers.Main) {
            val favorite = favoritesDatabase.getFavoritesDao().getFavorites().first().first()
            assertEquals("Electronics", favorite.category)
            assertEquals("Description", favorite.description)
        }
    }

}