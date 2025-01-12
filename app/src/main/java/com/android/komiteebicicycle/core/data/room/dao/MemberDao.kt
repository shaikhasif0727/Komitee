package com.android.komiteebicicycle.core.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.komiteebicicycle.home.data.model.Member

@Dao
interface MemberDao {
    @Insert
    suspend fun insertMember(member: Member)

    @Query("SELECT * FROM Member")
    suspend fun getAllMembers(): List<Member>
}