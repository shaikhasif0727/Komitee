package com.android.komiteebicicycle.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.komiteebicicycle.contribution.data.model.Contribution
import com.android.komiteebicicycle.core.data.room.dao.ContributionDao
import com.android.komiteebicicycle.home.data.model.Member
import com.android.komiteebicicycle.core.data.room.dao.MemberDao

@Database(entities = [Member::class, Contribution::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
    abstract fun contributionDao(): ContributionDao
}