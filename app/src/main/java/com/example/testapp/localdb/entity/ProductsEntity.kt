package com.example.testapp.localdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "products_table",
        foreignKeys = [ForeignKey(
                entity = CategoryEntity::class,
                parentColumns = ["mapid"],
                childColumns = ["mapid"],
                onDelete = ForeignKey.CASCADE
        )])
data class ProductsEntity (
        @PrimaryKey(autoGenerate = true) val id: Long? = null,
        val mapid: Long?,
        val title: String?,
        val price: String?,
        val img_url: String?,
        val descreption: String?,
        )