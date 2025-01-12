package com.android.komiteebicicycle.core.naviagtion

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationAction {

    data class Navigate(
        val destination: Destination,
        val navOption: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction

    data object NavigateUp: NavigationAction
}