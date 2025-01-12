package com.android.komiteebicicycle.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.komiteebicicycle.contribution.data.model.Contribution

@Dao
interface ContributionDao {

    @Insert
    suspend fun insertContribution(contribution: Contribution)

    @Query("SELECT * FROM Contribution")
    suspend fun getAllContributions() : List<Contribution>

    @Query("SELECT * FROM Contribution where memberId = :memberId")
    suspend fun getContributionForMember(memberId: Int): List<Contribution>

}