package com.example.bai1.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
fun ImageDetailScreen(navController: NavHostController) {
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
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // Căn giữa các item theo chiều dọc
                horizontalArrangement = Arrangement.SpaceBetween // Đẩy các item ra hai bên
            ){
                Button(
                onClick = { navController.popBackStack() },

            ) {
                Text("← Back")
            }
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Images",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF007AFF)
                )

            }



            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.avat),
                contentDescription = "Dong Phong Nha",
                modifier = Modifier.size(280.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "https://share.google/enWpdfhYRhSNB8qt2",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.jetpackcomposelogo),
                " JETPACK COMPOSE ICON",
                modifier = Modifier.size(280.dp)
            )
            Text(
                text = "Ảnh icon của jetpack compose",
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImageDetailScreen() {
    Bai1Theme {
        ImageDetailScreen(rememberNavController())
    }
}
