package com.example.testapp.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapp.localdb.dao.CategoryDao
import com.example.testapp.localdb.dao.ProductsDao
import com.example.testapp.localdb.entity.CategoryEntity
import com.example.testapp.localdb.entity.ProductsEntity

@Database(entities = [CategoryEntity::class,ProductsEntity::class], version = 5)
abstract class AppDatabase: RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun productsDao(): ProductsDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AppDatabase::class.java,
                    "webandcrafts_test_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }

}