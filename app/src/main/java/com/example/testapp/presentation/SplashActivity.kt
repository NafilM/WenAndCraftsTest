package com.example.testapp.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testapp.R
import com.example.testapp.api.ApiInterface
import com.example.testapp.localdb.AppDatabase
import com.example.testapp.localdb.dao.CategoryDao
import com.example.testapp.localdb.dao.ProductsDao
import com.example.testapp.localdb.entity.CategoryEntity
import com.example.testapp.localdb.entity.ProductsEntity
import com.example.testapp.model.Categories
import com.example.testapp.repository.CategoryRepository
import com.example.testapp.viewmodel.CategoryViewModel
import com.example.testapp.viewmodel.MyViewModelFactory

class SplashActivity : ComponentActivity() {

    lateinit var categoryViewModel: CategoryViewModel
    private val apiInterface = ApiInterface.getInstance()
    var categoryList = mutableListOf<Categories>()
    lateinit var categoryDao: CategoryDao
    lateinit var productsDao: ProductsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get viewmodel instance using MyViewModelFactory
        categoryViewModel =
            ViewModelProvider(this, MyViewModelFactory(CategoryRepository(apiInterface))).get(
                CategoryViewModel::class.java
            )

        val database = AppDatabase.getInstance(this)
        categoryDao = database.categoryDao()
        productsDao = database.productsDao()

        categoryViewModel.categoryFetch()

        categoryViewModel.categorySuccess.observe(this, Observer {
            categoryList = it.categories

            val databasethread = Thread {

                for (category in categoryList){
                    val isCategoryExist = categoryDao.getCategoryByTitle(category.title)
                    if (isCategoryExist == null){
                        val categoryEntity = CategoryEntity(
                            title = category.title
                        )
                        categoryDao.insertSingleCategory(categoryEntity)

                        if (category.products != null){
                            for (product in category.products){
                                val current_category = categoryDao.getCategoryByTitle(category.title)
                                val productsEntity = ProductsEntity(
                                    mapid = current_category?.mapid,
                                    title = product.title,
                                    price = product.price.toString(),
                                    img_url = product.imageUrl,
                                    descreption = product.description
                                )
                                productsDao.insertSingleProduct(productsEntity)
                            }
                        }

                    }
                }
            }
            databasethread.start()
            databasethread.join()

            //going to login activity after a delay of 2 seconds.
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        })

        categoryViewModel.categoryError.observe(this, Observer {
            Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        })


        setContent {
            HelloCompose()
        }
    }
}

@Composable
fun HelloCompose() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "MACHINE TEST",
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 24.sp
        )
    }
}