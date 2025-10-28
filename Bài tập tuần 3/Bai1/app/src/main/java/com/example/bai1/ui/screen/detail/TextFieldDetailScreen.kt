package com.example.bai1.ui.screen.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
// THÊM CÁC IMPORT NÀY
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
// --------------------
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bai1.ui.theme.Bai1Theme

@Composable
fun TextFieldDetailScreen(navController: NavHostController) {
    // --- BƯỚC 1: TẠO BIẾN TRẠNG THÁI ---
    // 'remember' giúp trạng thái không bị reset mỗi khi recompose
    // 'mutableStateOf' tạo ra một trạng thái có thể thay đổi
    var textValue by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("← Back")
                }
                Text(
                    text = "TextField",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007AFF)
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            // --- BƯỚC 2: KẾT NỐI TRẠNG THÁI VỚI TEXTFIELD ---
            OutlinedTextField(
                value = textValue, // 1. Hiển thị giá trị từ biến trạng thái
                onValueChange = { newValue ->
                    textValue = newValue // 2. Cập nhật biến trạng thái khi người dùng nhập
                },
                label = { Text("Nhập nội dung...") }, // Đổi label cho thân thiện hơn
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- BƯỚC 3 (Tùy chọn): HIỂN THỊ TEXT ĐÃ NHẬP ---
            Text(
                text = textValue, // Hiển thị giá trị từ biến trạng thái ở đây
                fontSize = 16.sp,
                color = Color.Red
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextFieldDetailScreen() {
    Bai1Theme {
        TextFieldDetailScreen(rememberNavController())
    }
}
