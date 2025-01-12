package com.android.komiteebicicycle.core.di

import com.android.komiteebicicycle.core.naviagtion.DefaultNavigator
import com.android.komiteebicicycle.core.naviagtion.Destination
import com.android.komiteebicicycle.core.naviagtion.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Singleton
    @Provides
    fun provideNavigator(): Navigator {
        return DefaultNavigator(Destination.HomeGraph)
    }

}