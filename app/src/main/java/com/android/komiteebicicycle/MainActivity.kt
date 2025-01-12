package com.android.komiteebicicycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.komiteebicicycle.presentation.home.HomeScreen
import com.android.komiteebicicycle.presentation.home.Member
import com.android.komiteebicicycle.ui.theme.KomiteeBiciCycleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KomiteeBiciCycleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        members = listOf(
                            Member(1, "John Doe"),
                            Member(2, "Jane Smith"),
                            Member(3, "Alex Johnson")
                        ),
                        onAddMember = {

                        },
                        onNavigateToContributions = { /*TODO*/ },
                        onNavigateToDraw = { /*TODO*/ }) {
                    }
                }
            }
        }
    }
}