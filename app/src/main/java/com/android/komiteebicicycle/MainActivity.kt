package com.android.komiteebicicycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.android.komiteebicicycle.contribution.presentation.ContributionScreenRoot
import com.android.komiteebicicycle.core.naviagtion.Destination
import com.android.komiteebicicycle.home.presentation.HomeScreenRoot
import com.android.komiteebicicycle.ui.theme.KomiteeBiciCycleTheme
import com.android.komiteebicicycle.core.naviagtion.NavigationAction
import com.android.komiteebicicycle.core.naviagtion.Navigator
import com.android.komiteebicicycle.core.naviagtion.ObserverAsEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KomiteeBiciCycleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()

                    ObserverAsEvent(flow = navigator.navigationActions) { action ->
                        when (action) {
                            is NavigationAction.Navigate -> {
                                navController.navigate(action.destination) {
                                    action.navOption(this)
                                }
                            }

                            NavigationAction.NavigateUp -> navController.navigateUp()
                        }
                    }

                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = navigator.startDestination
                    ) {

                        navigation<Destination.HomeGraph>(
                            startDestination = Destination.HomeScreen
                        ) {
                            composable<Destination.HomeScreen> {
                                HomeScreenRoot()
                            }

                            composable<Destination.ContributionScreen> {
                                ContributionScreenRoot()
                            }
                        }

                    }
                }
            }
        }
    }
}