package com.example.tuan6

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// ----------------------------
// DATA MODEL (ĐÃ SỬA LỖI ✅)
// ----------------------------
data class Product(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    // Sửa kiểu dữ liệu từ String thành Double
    @SerializedName("price")
    val price: Double,

    // Ánh xạ "des" từ JSON vào thuộc tính "description"
    @SerializedName("des")
    val description: String,

    // Ánh xạ "imgURL" từ JSON vào thuộc tính "image"
    @SerializedName("imgURL")
    val image: String
)

// ----------------------------
// API SERVICE
// ----------------------------
interface ProductApi {
    @GET("product")
    suspend fun getProduct(): Product
}

// Hàm tạo đối tượng Retrofit để gọi API (ĐÃ SỬA LỖI ✅)
fun createApi(): ProductApi {
    return Retrofit.Builder()
        // URL cơ sở phải kết thúc bằng dấu /
        .baseUrl("https://mock.apidog.com/m1/890655-872447-default/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductApi::class.java)
}

// ----------------------------
// UI COMPOSABLE (Màn hình chính)
// ----------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BTTuan6() {
    val api = remember { createApi() }
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Tự động load dữ liệu khi màn hình khởi tạo
    LaunchedEffect(Unit) {
        try {
            product = api.getProduct()
        } catch (e: Exception) {
            error = "Lỗi khi tải dữ liệu: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Product detail",
                        color = Color(0xFF00BCD4),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF00BCD4)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFFFF5252)
                    )
                }

                error != null -> {
                    Text(
                        text = error!!,
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                product != null -> {
                    ProductDetail(product!!)
                }
            }
        }
    }
}

// ----------------------------
// PRODUCT DETAIL UI
// ----------------------------
@Composable
fun ProductDetail(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        // Product Image với background màu cam
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(Color(0xFFFF5252))
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(280.dp),
                contentScale = ContentScale.Fit
            )
        }

        // Product Information
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Product Name
            Text(
                text = product.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price (ĐÃ SỬA LỖI ✅)
            Text(
                // Hiển thị giá kiểu Double thành String
                text = "Giá: ${product.price}$",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5252)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = product.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                lineHeight = 20.sp,
                textAlign = TextAlign.Justify
            )
        }
    }
}