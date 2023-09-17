package com.example.testapp.localdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class CategoryEntity (
    @PrimaryKey(autoGenerate = true) val mapid: Long? = null,
    val title: String?,
        )