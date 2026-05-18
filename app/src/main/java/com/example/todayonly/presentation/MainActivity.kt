package com.example.todayonly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.todayonly.presentation.navigation.AppNavGraph
import com.example.todayonly.ui.theme.TodayOnlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodayOnlyTheme {
                AppNavGraph()
            }
        }
    }
}