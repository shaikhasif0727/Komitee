package com.android.komiteebicicycle.member.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Member(
    @PrimaryKey(autoGenerate = true) val memberId: Int = 0,
    val name: String
)