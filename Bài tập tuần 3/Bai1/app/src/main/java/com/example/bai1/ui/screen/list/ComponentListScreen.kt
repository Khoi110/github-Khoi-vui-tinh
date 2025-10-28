package com.example.bai1.ui.screen.list

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bai1.ui.theme.Bai1Theme

@Composable
fun ComponentListScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tiêu đề màn hình
            Text(
                text = "UI Components List",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF0091EA),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Nhóm Display
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Display",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                ComponentCard(
                    "Text",
                    onClick = { navController.navigate("textDetail") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
                )
                ComponentCard(
                    "Image",
                    onClick = { navController.navigate("imageDetail") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
                )
            }

            // Nhóm Input
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Input",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                ComponentCard(
                    "TextField",
                    onClick = { navController.navigate("textFieldDetail") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                )

                ComponentCard(
                    "PasswordField",
                    onClick = { navController.navigate("passwordFieldDetail") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                )
            }

            // Nhóm Layout
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Layout",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                ComponentCard(
                    "Column",
                    onClick = { navController.navigate("columnDetail") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFD8EFFF))
                )

                ComponentCard(
                    "Row",
                    onClick = { navController.navigate("rowDetail") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFD8EFFF))
                )
            }
            ComponentCard(
                "Animation Demo",
                onClick = { navController.navigate("animationDetail") },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCDD2))
            )
            // Mục Tự tìm hiểu (to be continued chắc vậy)
            ComponentCard(
                "to be continued .....😁",
                onClick = { /* có thể mở web hoặc để trống */ },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD6D6))
            )
        }
    }
}

@Composable
fun ComponentCard(
    title: String,
    onClick: () -> Unit,
    colors: androidx.compose.material3.CardColors = CardDefaults.cardColors()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = colors
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComponentListScreen() {
    Bai1Theme {
        ComponentListScreen(rememberNavController())
    }
}
