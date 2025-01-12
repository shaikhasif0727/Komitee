package com.android.komiteebicicycle.core.di

import com.android.komiteebicicycle.contribution.data.repository.ContributionRepositoryImpl
import com.android.komiteebicicycle.contribution.domain.repository.ContributionRepository
import com.android.komiteebicicycle.home.data.repository.HomeRepositoryImpl
import com.android.komiteebicicycle.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl) : HomeRepository

    @Binds
    abstract fun bindContributionRepository(contributionRepositoryImpl: ContributionRepositoryImpl) : ContributionRepository

}