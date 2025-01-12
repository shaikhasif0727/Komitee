package com.android.komiteebicicycle.core.room.dao

import androidx.room.Entity

@Entity(primaryKeys = ["biciId","memberId"])
data class BiciMemberCrossRef(
    val biciId: Int,
    val memberId: Int
)
