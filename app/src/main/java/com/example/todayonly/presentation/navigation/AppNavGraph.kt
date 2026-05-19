package com.example.todayonly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todayonly.presentation.screens.ExpiredTasksScreen
import com.example.todayonly.presentation.screens.TodayTasksScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "tasks"
    ) {
        composable("tasks") {
            TodayTasksScreen(
                onOpenExpiredTasks = { navController.navigate("expired") }
            )
        }
        composable("expired") {
            ExpiredTasksScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}