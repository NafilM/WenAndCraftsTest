package com.example.testapp.localdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.localdb.entity.CategoryEntity
import com.example.testapp.localdb.entity.ProductsEntity

@Dao
interface ProductsDao {

    @Insert
    fun insertSingleProduct(product: ProductsEntity)

    @Query("SELECT * FROM products_table WHERE mapid =:mapid")
    fun getProductsWithMapid(mapid : Long?): MutableList<ProductsEntity>

}