package com.android.komiteebicicycle.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.komiteebicicycle.contribution.data.model.Contribution
import com.android.komiteebicicycle.core.room.dao.BiciDao
import com.android.komiteebicicycle.core.room.dao.BiciMemberCrossRef
import com.android.komiteebicicycle.core.room.dao.ContributionDao
import com.android.komiteebicicycle.member.data.model.Member
import com.android.komiteebicicycle.core.room.dao.MemberDao
import com.android.komiteebicicycle.overview.data.model.Bici

@Database(entities = [Member::class, Contribution::class,Bici::class, BiciMemberCrossRef::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
    abstract fun contributionDao(): ContributionDao
    abstract fun BiciDao(): BiciDao
}