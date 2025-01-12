package com.android.komiteebicicycle.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.komiteebicicycle.member.data.model.Member

@Dao
interface MemberDao {
    @Insert
    suspend fun insertMember(member: Member)

    @Query("SELECT * FROM Member")
    suspend fun getAllMembers(): List<Member>
}