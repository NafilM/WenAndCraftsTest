package com.example.testapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import coil.compose.rememberImagePainter
import com.example.testapp.R

class ProductDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val title = intent.getStringExtra("title")
        val price = intent.getStringExtra("price")
        val descreption = intent.getStringExtra("desc")
        val image_url = intent.getStringExtra("img_url")

        setContent {
            ProductDetailScreen(title,price,descreption,image_url)
        }
    }
}

@Composable
fun ProductDetailScreen(title: String?,price : String?, descreption: String?,image_url : String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight() // Fill the entire screen height
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(image_url),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "\u20B9${price}",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = descreption.toString(),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 15.sp,
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        Color.Black,
                        MaterialTheme.shapes.medium
                    )
            ) {
                Text(
                    text = "Add to Cart",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}