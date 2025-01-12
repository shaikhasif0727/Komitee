package com.android.komiteebicicycle.core.di

import android.content.Context
import androidx.room.Room
import com.android.komiteebicicycle.core.room.AppDatabase
import com.android.komiteebicicycle.core.room.dao.BiciDao
import com.android.komiteebicicycle.core.room.dao.ContributionDao
import com.android.komiteebicicycle.core.room.dao.MemberDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bici_system_db"
        ).build()
    }

    @Provides
    fun provideMemberDao(database: AppDatabase): MemberDao {
        return database.memberDao()
    }

    @Provides
    fun provideContributionDao(database: AppDatabase): ContributionDao {
        return database.contributionDao()
    }

    @Provides
    fun provideBiciDao(database: AppDatabase): BiciDao {
        return database.BiciDao()
    }

}