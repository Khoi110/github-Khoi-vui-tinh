package com.example.bai1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bai1.ui.screen.main.MainScreen
import com.example.bai1.ui.screen.list.ComponentListScreen
import com.example.bai1.ui.screen.detail.TextDetailScreen
import com.example.bai1.ui.screen.detail.ImageDetailScreen
import com.example.bai1.ui.screen.detail.TextFieldDetailScreen
import com.example.bai1.ui.screen.detail.PasswordFieldDetailScreen
import com.example.bai1.ui.screen.detail.RowDetailScreen
import com.example.bai1.ui.screen.detail.ColumnDetailScreen
import com.example.bai1.ui.screen.detail.AnimationDetailScreen
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("list") { ComponentListScreen(navController) }
        composable("textDetail") { TextDetailScreen(navController) }
        composable("imageDetail") { ImageDetailScreen(navController) }
        composable("textFieldDetail") { TextFieldDetailScreen(navController) }
        composable("passwordFieldDetail") { PasswordFieldDetailScreen(navController) }
        composable("rowDetail") { RowDetailScreen(navController) }
        composable("columnDetail") { ColumnDetailScreen(navController) }
        composable("animationDetail") { AnimationDetailScreen(navController) }
    }
}
