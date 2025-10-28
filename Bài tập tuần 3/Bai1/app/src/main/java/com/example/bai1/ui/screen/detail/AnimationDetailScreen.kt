package com.example.bai1.ui.screen.detail

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationDetailScreen(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    val size by animateDpAsState(
        targetValue = if (expanded) 200.dp else 100.dp,
        animationSpec = tween(durationMillis = 500)
    )

    val color by animateColorAsState(
        targetValue = if (expanded) Color(0xFF4CAF50) else Color(0xFF1976D2),
        animationSpec = tween(600)
    )

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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("← Back")
                }

                Text(
                    text = "Animation Demo",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF007AFF)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Animated Box
            Box(
                modifier = Modifier
                    .size(size)
                    .background(color, shape = MaterialTheme.shapes.medium)
                    .clickable { expanded = !expanded },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (expanded) "Expanded!" else "Tap Me",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(visible = expanded) {
                Text(
                    text = "Đây là ví dụ về animation trong Jetpack Compose.\n" +
                            "Box thay đổi kích thước và màu khi bạn nhấn vào.",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimationDetailScreen() {
    Bai1Theme {
        AnimationDetailScreen(rememberNavController())
    }
}
