package com.android.komiteebicicycle.contribution.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contribution(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val memberId: Int,
    val amount: Double,
    val date: String
)