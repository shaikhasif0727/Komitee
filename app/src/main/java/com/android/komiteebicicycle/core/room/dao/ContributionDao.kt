package com.android.komiteebicicycle.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.android.komiteebicicycle.contribution.data.model.Contribution

@Dao
interface ContributionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContribution(contribution: Contribution)

    @Update
    suspend fun updateContribution(contribution: Contribution)

    @Query("SELECT * FROM Contribution WHERE biciId = :biciId AND month = :month")
    suspend fun getContributionsByBiciAndMonth(biciId: Int, month: String): List<Contribution>
}