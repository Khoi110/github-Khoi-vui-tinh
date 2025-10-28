package com.example.bai1.ui.screen.detail

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bai1.ui.theme.Bai1Theme

@Composable
fun TextDetailScreen(navController: NavHostController) {
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
                verticalAlignment = Alignment.CenterVertically, // Căn giữa các item theo chiều dọc
                horizontalArrangement = Arrangement.SpaceBetween // Đẩy các item ra hai bên
            ) {
                Button(
                    onClick = { navController.popBackStack() }
                ) {
                    Text("← Back")
                }

                Text(
                    text = "Text Detail",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF007AFF)
                )


            }

            Spacer(modifier = Modifier.weight(1f)) // Dùng Spacer với weight để đẩy nội dung xuống giữa

            // Spacer(modifier = Modifier.height(16.dp)) // Spacer này không cần

            Text(
                text = buildAnnotatedString { // 'text' đã bị đổi thành 'text'
                    withStyle(SpanStyle(fontSize = 28.sp)) {
                        append("The ")
                    }
                    withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                        append("quick ")
                    }
                    withStyle(SpanStyle(color = Color(0xFF8B4513), fontWeight = FontWeight.Bold)) {
                        append("Brown\n")
                    }
                    append("fox ")
                    withStyle(SpanStyle(letterSpacing = 5.sp)) {
                        append("jumps ")
                    }
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("over\n")
                    }
                    append("the ")
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("lazy dog.")
                    }
                },
                // --- THAY ĐỔI 2 & 3: CĂN GIỮA VÀ TĂNG KÍCH THƯỚC CHỮ ---
                textAlign = TextAlign.Center, // Căn giữa nội dung text
                fontSize = 30.sp, // Đặt kích thước chữ cơ bản to hơn
                modifier = Modifier.fillMaxWidth() // Cần thiết để textAlign hoạt động
            )

            Spacer(modifier = Modifier.weight(1f))


        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextDetailScreen() {
    Bai1Theme {
        TextDetailScreen(rememberNavController())
    }
}
