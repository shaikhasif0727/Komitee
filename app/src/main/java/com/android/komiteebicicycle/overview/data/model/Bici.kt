package com.android.komiteebicicycle.overview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bici(
    @PrimaryKey(autoGenerate = true) val biciId: Int = 0,
    val title: String,
    val totalAmount: Double,
    val startDate: String,
    val endDate: String
)