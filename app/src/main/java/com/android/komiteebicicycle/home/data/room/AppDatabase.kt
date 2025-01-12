package com.android.komiteebicicycle.home.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.komiteebicicycle.home.data.model.Member
import com.android.komiteebicicycle.home.data.room.dao.MemberDao

@Database(entities = [Member::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun memberDao(): MemberDao
}