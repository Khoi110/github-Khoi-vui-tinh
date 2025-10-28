package com.example.bai1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.bai1.ui.navigation.NavGraph
import com.example.bai1.ui.theme.Bai1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bai1App()
        }
    }
}

@Composable
fun Bai1App() {
    Bai1Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}
