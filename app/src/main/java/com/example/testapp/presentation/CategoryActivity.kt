package com.example.testapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.ui.theme.NestedColumnTheme
import coil.compose.rememberImagePainter
import com.example.testapp.R
import com.example.testapp.localdb.AppDatabase
import com.example.testapp.localdb.dao.CategoryDao
import com.example.testapp.localdb.dao.ProductsDao
import com.example.testapp.localdb.entity.CategoryEntity
import com.example.testapp.localdb.entity.ProductsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    lateinit var categoryList : List<CategoryEntity>
    lateinit var categoryDao: CategoryDao
    lateinit var productsDao: ProductsDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(this)
        categoryDao = database.categoryDao()
        productsDao = database.productsDao()

        setContent {
            NestedColumnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val databaseThread = Thread {
                        categoryList = categoryDao.getAllCategories()
                    }
                    databaseThread.start()
                    databaseThread.join()

                    Column(modifier = Modifier.fillMaxSize()) {

                        // Custom App Bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(Color.Black)
                        ) {
                            Text(
                                text = "Categories",
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(16.dp),
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        // Main Content
                        MainScreen(categoryList, productsDao)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(categoryList: List<CategoryEntity>,productsDao : ProductsDao) {
    LazyColumn {
        items(categoryList) { categories ->
            var isExpanded by remember { mutableStateOf(false) }
            ListItem(categories = categories, isExpanded = isExpanded,productsDao = productsDao) {
                isExpanded = !isExpanded
            }
        }
    }
}

@Composable
fun ListItem(categories: CategoryEntity, isExpanded: Boolean, productsDao: ProductsDao, toggleExpansion: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { toggleExpansion() },
        shape = RoundedCornerShape(5.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = categories.title.toString(),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
            if (isExpanded) {
                var productList by remember { mutableStateOf<List<ProductsEntity>>(emptyList()) }

                LaunchedEffect(categories.mapid) {
                    val productResult = withContext(Dispatchers.IO) {
                        productsDao.getProductsWithMapid(categories.mapid)
                    }
                    productList = productResult
                }

                // Split the productList into two separate lists
                val splitIndex = productList.size / 2
                val firstRow = productList.take(splitIndex)
                val secondRow = productList.drop(splitIndex)

                // Create two LazyRows to display the products in two rows
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(-20.dp)
                ) {
                    items(firstRow) { product ->
                        ProductItem(product = product)
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(-20.dp)
                ) {
                    items(secondRow) { product ->
                        ProductItem(product = product)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductsEntity) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("title",product.title)
                intent.putExtra("price",product.price)
                intent.putExtra("desc",product.descreption)
                intent.putExtra("img_url",product.img_url)
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(8.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            val painter: Painter = rememberImagePainter(
                data = product.img_url,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.placeholder)
                }
            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Text(
                text = product.title.toString(),
                style = TextStyle(color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "$${product.price}",
                style = TextStyle(color = Color.DarkGray, fontSize = 12.sp),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}