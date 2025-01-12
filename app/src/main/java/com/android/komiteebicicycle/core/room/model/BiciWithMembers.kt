package com.android.komiteebicicycle.core.room.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.android.komiteebicicycle.core.room.dao.BiciMemberCrossRef
import com.android.komiteebicicycle.member.data.model.Member
import com.android.komiteebicicycle.overview.data.model.Bici

data class BiciWithMembers(
    @Embedded val bici: Bici,
    @Relation(
        parentColumn = "biciId",
        entityColumn = "memberId",
        associateBy = Junction(BiciMemberCrossRef::class)
    )
    val members: List<Member>
)