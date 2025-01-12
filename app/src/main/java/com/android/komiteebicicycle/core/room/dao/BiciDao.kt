package com.android.komiteebicicycle.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.android.komiteebicicycle.core.room.model.BiciWithMembers
import com.android.komiteebicicycle.overview.data.model.Bici

@Dao
interface BiciDao {
    @Insert
    suspend fun insertBici(bici: Bici): Long

    @Insert
    suspend fun insertBiciMemberCrossRef(crossRef: BiciMemberCrossRef)

    @Transaction
    @Query("SELECT * FROM Bici")
    suspend fun getAllBiciWithMembers(): List<BiciWithMembers>
}