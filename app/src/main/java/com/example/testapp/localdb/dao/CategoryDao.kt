package com.example.testapp.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.localdb.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert
    fun insertSingleCategory(category: CategoryEntity)

    @Query("SELECT * FROM category_table WHERE title = :title")
    fun getCategoryByTitle(title: String?): CategoryEntity?

    @Query("SELECT * FROM category_table")
    fun getAllCategories(): List<CategoryEntity>
}