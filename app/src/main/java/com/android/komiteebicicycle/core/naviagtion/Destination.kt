package com.android.komiteebicicycle.core.naviagtion

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object OverviewGraph : Destination

    @Serializable
    data object OverviewScreen : Destination

    @Serializable
    data object CreateBiciScreen : Destination

    @Serializable
    data object AddMemberScreen : Destination

    @Serializable
    data class BiciDetailsScreen(val biciId:Int) : Destination

    @Serializable
    data object ContributionScreen : Destination
}