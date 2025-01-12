package com.android.komiteebicicycle.home.data.repository

import com.android.komiteebicicycle.home.data.model.Member
import com.android.komiteebicicycle.core.data.room.dao.MemberDao
import com.android.komiteebicicycle.home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val memberDao: MemberDao
) : HomeRepository {

    override suspend fun getMembers(): Result<List<Member>>{
        return Result.success(memberDao.getAllMembers())
    }

   override suspend fun addMember(member: Member) {
       memberDao.insertMember(member)
   }

}