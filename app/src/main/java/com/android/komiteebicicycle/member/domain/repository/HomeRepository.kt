package com.android.komiteebicicycle.member.domain.repository

import com.android.komiteebicicycle.member.data.model.Member

interface HomeRepository {

    suspend fun getMembers(): Result<List<Member>>

    suspend fun addMember(member: Member)

}