package com.android.komiteebicicycle.home.domain.repository

import com.android.komiteebicicycle.home.data.model.Member

interface HomeRepository {

    suspend fun getMembers(): Result<List<Member>>

    suspend fun addMember(member: Member)

}