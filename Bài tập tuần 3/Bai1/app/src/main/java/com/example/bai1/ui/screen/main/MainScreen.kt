package com.example.bai1.ui.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bai1.R
import com.example.bai1.ui.theme.Bai1Theme

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Nguyễn Đăng Khôi", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "040205001384", fontSize = 14.sp, color = Color.Gray)
            }
        }

//        Spacer(modifier = Modifier.height(-40.dp)) // Tạo khoảng trống phía trên
        // Logo + Text (phần trên)
        Column(

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo Jetpack Compose
            Image(
                painter = painterResource(id = R.drawable.jetpackcomposelogo),
                contentDescription = "Jetpack Compose Logo",
                modifier = Modifier
                    .size(160.dp) // chỉnh chiều cao logo ở đây
                    .offset(y = (-10).dp) // dịch logo lên cao hơn một chút
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tiêu đề
            Text(
                text = "Jetpack Compose",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Mô tả
            Text(
                text = "Modern toolkit for building native Android UI",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

        }

        // Nút bấm
        Button(
            onClick = {
                // Khi nhấn sẽ chuyển sang màn hình danh sách component
                navController.navigate("list")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "I’m ready")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Bai1Theme {
        MainScreen(rememberNavController())
    }
}
